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

package com.lebogang.gensysmusic.ui.dialogs

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lebogang.gensysmusic.databinding.DialogDeezerPreviewBinding
import com.lebogang.gensysmusic.servicehelpers.OnStateChangedListener
import com.lebogang.gensysmusic.servicehelpers.PlaybackState
import com.lebogang.gensysmusic.servicehelpers.RepeatSate
import com.lebogang.gensysmusic.network.models.AlbumDeezer
import com.lebogang.gensysmusic.network.models.TrackDeezer
import com.lebogang.gensysmusic.service.MusicService
import com.lebogang.gensysmusic.ui.helpers.CommonActivity
import com.lebogang.gensysmusic.ui.helpers.ThemeHelper
import com.lebogang.gensysmusic.utils.Keys
import com.lebogang.gensysmusic.utils.GlideManager

class DialogDeezerPreview : BottomSheetDialogFragment(), OnStateChangedListener{
    private val viewBinding: DialogDeezerPreviewBinding by lazy { DialogDeezerPreviewBinding.inflate(layoutInflater) }
    private lateinit var audio:TrackDeezer
    private var album:AlbumDeezer? = null
    @ColorInt private var explicitColor:Int = 0
    private val musicService: MusicService? by lazy {(requireActivity() as CommonActivity).getMusicService()}
    private var countDownTimer:CountDownTimer? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        audio = requireArguments().getParcelable(Keys.DEEZER_SONG_KEY)!!
        album = audio.album
        explicitColor = if (audio.hasExplicitLyrics) ThemeHelper.PRIMARY_COLOR else
            ThemeHelper.SECONDARY_TEXTCOLOR_NO_DISABLE
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateViews()
    }

    private fun populateViews(){
        viewBinding.titleView.text = audio.title
        viewBinding.subtitleView.text = audio.artist.title
        viewBinding.explicitView.setTextColor(explicitColor)
        if(album != null){
            GlideManager(this).loadOnline(album!!.coverMedium, viewBinding.imageView)
        }else{
            val art = requireArguments().getString(Keys.ALBUM_ART_URL_KEY)!!
            GlideManager(this).loadOnline(art, viewBinding.imageView)
        }
    }

    /**
     * Start playing audio
     * */
    override fun onResume() {
        super.onResume()
        musicService?.playOnline(audio.link, this)
    }

    /**
     * Stop playing audio
     * */
    override fun onPause() {
        super.onPause()
        musicService?.stopOnline()
        countDownTimer?.cancel()
    }

    /**
     * Update counter
     * */
    private fun getCountDownTimer():CountDownTimer{
        return object :CountDownTimer(musicService?.getOnlineDuration()!!.toLong(), 1000){
            override fun onTick(millisUntilFinished: Long) {
                val time = (millisUntilFinished/1000).toString()
                viewBinding.timerView.text = time
                viewBinding.progressBar.progress = millisUntilFinished.toInt()
            }

            override fun onFinish() {
                viewBinding.timerView.text = "-/-"
                viewBinding.progressBar.progress = 0
            }

        }
    }

    /**
     * change views when playback state changes
     * */
    override fun onPlaybackChanged(playbackState: PlaybackState) {
        if (playbackState == PlaybackState.PLAYING){
            musicService?.let{
                viewBinding.progressBar.isIndeterminate = false
                viewBinding.progressBar.max = it.getOnlineDuration()
                countDownTimer = getCountDownTimer()
                countDownTimer?.start()
            }
        }else{
            viewBinding.progressBar.isIndeterminate = true
        }
    }

    override fun onRepeatModeChange(repeatSate: RepeatSate) {
        //not needed
    }
}
