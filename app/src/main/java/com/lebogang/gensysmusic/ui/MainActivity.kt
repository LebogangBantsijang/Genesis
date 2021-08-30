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
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.data.models.Audio
import com.lebogang.gensysmusic.databinding.ActivityMainBinding
import com.lebogang.gensysmusic.servicehelpers.OnStateChangedListener
import com.lebogang.gensysmusic.service.ManageServiceConnection
import com.lebogang.gensysmusic.service.MusicQueue
import com.lebogang.gensysmusic.service.MusicService
import com.lebogang.gensysmusic.settings.PlayerBackgroundType
import com.lebogang.gensysmusic.ui.helpers.ThemeHelper
import com.lebogang.gensysmusic.utils.SeekBarThreader

class MainActivity : ThemeHelper() {
    private val viewBinding: ActivityMainBinding by lazy{ ActivityMainBinding.inflate(layoutInflater) }
    private var musicService: MusicService? = null
    private lateinit var appBarConfiguration:AppBarConfiguration
    private lateinit var player: Player

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ManageServiceConnection(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        player = Player(this, viewBinding)
        initNavigation()
        initAds()
    }

    @SuppressLint("RtlHardcoded")
    private fun initNavigation(){
        setSupportActionBar(viewBinding.toolbar)
        val navHost = supportFragmentManager.findFragmentById(R.id.navFragmentContainer) as NavHostFragment
        val controller = navHost.navController
        appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment), viewBinding.drawerLayout)
        setupActionBarWithNavController(controller,appBarConfiguration)
        viewBinding.navigationView.setupWithNavController(controller)
        controller.addOnDestinationChangedListener { _, _, _ ->
            showLauncher()
        }
    }

    fun showLauncher(){
        val navHost = supportFragmentManager.findFragmentById(R.id.navFragmentContainer) as NavHostFragment
        val controller = navHost.navController
        val destination = controller.currentDestination?.id
        /*if (destination == R.id.songsFragment || destination == R.id.viewAlbumFragment
                || destination == R.id.viewArtistFragment || destination == R.id.viewPlaylistFragment){
            if (MusicQueue.currentAudio.value != null && MusicQueue.audioQueue.isNotEmpty()){
                viewBinding.launcherView.getRoot().visibility = View.VISIBLE
            }
        }else
            viewBinding.launcherView.getRoot().visibility = View.GONE*/
    }

    override fun onSupportNavigateUp(): Boolean {
        val controller = findNavController(R.id.navFragmentContainer)
        return controller.navigateUp() or super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        val controller = findNavController(R.id.navFragmentContainer)
        if (!player.isSheetShowing() && !controller.navigateUp())
            moveTaskToBack(true)
    }

    override fun getStateChangedListener(): OnStateChangedListener {
        return player.getStateListener()
    }

    override fun changePlayerBackground(type:PlayerBackgroundType){
        if (type == PlayerBackgroundType.GIF)
            player.loadGif()
        else
            player.changeBackground()
    }

    override fun onServiceReady(musicService: MusicService) {
        this.musicService = musicService
        player.musicService = musicService
        player.seekBarThreader = SeekBarThreader(this, musicService)
    }

    override fun getMusicService(): MusicService? {
        return musicService
    }

    fun playAudio(audio: Audio, audioQueue:MutableList<Audio>?){
        audioQueue?.let { MusicQueue.audioQueue = it }
        musicService?.play(audio)
    }

    fun openMenu(){
        viewBinding.drawerLayout.open()
    }

    private fun initAds(){
        /*MobileAds.initialize(this)
        viewBinding.adView.loadAd(AdRequest.Builder().build())*/
    }
}
