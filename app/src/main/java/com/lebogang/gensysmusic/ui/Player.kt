/*
 * Copyright (c) 2021. - Lebogang Bantsijang
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.lebogang.gensysmusic.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.core.view.GestureDetectorCompat
import androidx.navigation.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.databinding.ActivityMainBinding
import com.lebogang.gensysmusic.service.MusicQueue
import com.lebogang.gensysmusic.service.MusicService
import com.lebogang.gensysmusic.servicehelpers.OnStateChangedListener
import com.lebogang.gensysmusic.servicehelpers.PlaybackState
import com.lebogang.gensysmusic.servicehelpers.RepeatSate
import com.lebogang.gensysmusic.settings.PlayerBackgroundType
import com.lebogang.gensysmusic.settings.PlayerSettings
import com.lebogang.gensysmusic.ui.helpers.BottomSheetHelper
import com.lebogang.gensysmusic.ui.helpers.SeekBarHelper
import com.lebogang.gensysmusic.utils.SeekBarThreader
import com.lebogang.gensysmusic.utils.GlideManager

@SuppressLint("ClickableViewAccessibility")
class Player(private val activity: MainActivity, private val viewBinding: ActivityMainBinding): BottomSheetHelper()
        , OnStateChangedListener{
    private val playerSettings = PlayerSettings(activity)
    var musicService: MusicService? = null
    lateinit var seekBarThreader:SeekBarThreader
    private val bottomSheet = BottomSheetBehavior.from(viewBinding.player.bottomSheet).apply {
        state = BottomSheetBehavior.STATE_HIDDEN
        addBottomSheetCallback(this@Player)
    }
    private val gestureDetector = GestureDetectorCompat(activity, object : GestureDetector.SimpleOnGestureListener(){
        override fun onDoubleTap(e: MotionEvent?): Boolean {
            musicService?.togglePlayPause()
            return true
        }

    })

    init {
        initOnClicks()
        MusicQueue.currentAudio.observe(activity, {
            //launcher
            activity.showLauncher()
            viewBinding.launcherView.titleView.text = it.title
            viewBinding.launcherView.subtitleView.text = it.artist
            viewBinding.launcherView.progressBar.max = it.duration.toInt()
            GlideManager(activity).loadAudioArt(it.getArtUri(), viewBinding.launcherView.imageView)
            //player
            viewBinding.player.titleView.text = it.title
            viewBinding.player.subtitleView.text = it.artist
            viewBinding.player.thirdTitle.text = it.album
            viewBinding.player.seekBar.max = it.duration.toInt()
            viewBinding.player.durationView.text = it.durationFormatted
            GlideManager(activity).loadAudioArtCircularCrop(it.getArtUri(), viewBinding.player.imageView)
            changeBackground()
        })
        if (playerSettings.getBackgroundType() == PlayerBackgroundType.GIF)
            loadGif()
    }

    private fun initOnClicks(){
        viewBinding.launcherView.root.setOnClickListener { showSheet() }
        viewBinding.player.volumeView.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                val controller = activity.findNavController(R.id.navFragmentContainer)
                controller.navigate(R.id.audioProcessingDialog)
            }else{
                Snackbar.make(viewBinding.root,"Not Supported", Snackbar.LENGTH_LONG).show()
            }
        }
        viewBinding.launcherView.queueView.setOnClickListener {
            val controller = activity.findNavController(R.id.navFragmentContainer)
            controller.navigate(R.id.queueDialog) }
        viewBinding.launcherView.playPauseView.setOnClickListener { musicService?.togglePlayPause() }
        viewBinding.player.seekBar.setOnSeekBarChangeListener(object : SeekBarHelper(){
            override fun progressChanged(progress: Int) {
                musicService?.seekTo(progress)
            }
        })
        viewBinding.player.previousView.setOnClickListener { musicService?.skipToPrevious() }
        viewBinding.player.playPauseView.setOnClickListener { musicService?.togglePlayPause() }
        viewBinding.player.nextView.setOnClickListener { musicService?.skipToNext() }
        viewBinding.player.queueView.setOnClickListener {
            val controller = activity.findNavController(R.id.navFragmentContainer)
            controller.navigate(R.id.queueDialog) }
        viewBinding.player.shareView.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "audio/*"
            }
            MusicQueue.currentAudio.value?.let {
                intent.putExtra(Intent.EXTRA_STREAM, it.getUri())
                activity.startActivity(Intent.createChooser(intent,"Share from Genesis"))
            }
        }
        viewBinding.player.repeatView.setOnClickListener { musicService?.changeRepeatMode() }
        viewBinding.player.backView.setOnClickListener { hideSheet() }
        viewBinding.player.gifView.setOnClickListener {
            val controller = activity.findNavController(R.id.navFragmentContainer)
            controller.navigate(R.id.playerBackgroundDialog)
        }
        // gestures
        viewBinding.player.artView.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
        }
    }

    fun changeBackground(){
        val uri = MusicQueue.currentAudio.value?.getArtUri()
        uri?.let {
            when(playerSettings.getBackgroundType()){
                PlayerBackgroundType.NONE -> viewBinding.player.artView.setImageBitmap(null)
                PlayerBackgroundType.ADAPTIVE_BLURRY ->
                    GlideManager(activity).loadBlurred(activity,it,viewBinding.player.artView)
                PlayerBackgroundType.ADAPTIVE_IMAGE ->
                    GlideManager(activity).loadAudioArt(it, viewBinding.player.artView)
                PlayerBackgroundType.GIF -> {
                    //do nothing
                }
            }
        }
    }

    fun loadGif(){
        GlideManager(activity).loadGif(activity,viewBinding.player.artView)
    }

    fun isSheetShowing():Boolean{
        if (bottomSheet.state == BottomSheetBehavior.STATE_EXPANDED){
            hideSheet()
            return true
        }
        return false
    }

    private fun hideSheet(){
        bottomSheet.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun showSheet(){
        bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onStateChanged(newState: Int) {
        if (newState == BottomSheetBehavior.STATE_HALF_EXPANDED)
            bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED
        else if (newState == BottomSheetBehavior.STATE_EXPANDED)
            viewBinding.player.artView.requestFocus()
        else
            viewBinding.player.artView.clearFocus()
    }

    override fun onPlaybackChanged(playbackState: PlaybackState) {
        if (playbackState == PlaybackState.PLAYING){
            viewBinding.launcherView.playPauseView.setImageResource(R.drawable.ic_pause)
            viewBinding.player.playPauseView.setImageResource(R.drawable.ic_pause)
        }
        else{
            viewBinding.launcherView.playPauseView.setImageResource(R.drawable.ic_play)
            viewBinding.player.playPauseView.setImageResource(R.drawable.ic_play)
        }
        seekBarThreader.onPlaybackStateChanged(playbackState)
    }

    fun getStateListener(): OnStateChangedListener {
        return this
    }

    override fun onRepeatModeChange(repeatSate: RepeatSate) {
        when(repeatSate){
            RepeatSate.REPEAT_NONE -> viewBinding.player.repeatView.setImageResource(R.drawable.ic_repeat_none)
            RepeatSate.REPEAT_ALL -> viewBinding.player.repeatView.setImageResource(R.drawable.ic_repeat)
            RepeatSate.REPEAT_ONE -> viewBinding.player.repeatView.setImageResource(R.drawable.ic_repeat_one)
            RepeatSate.SHUFFLE_ALL -> viewBinding.player.repeatView.setImageResource(R.drawable.ic_shuffle)
        }
    }

}
