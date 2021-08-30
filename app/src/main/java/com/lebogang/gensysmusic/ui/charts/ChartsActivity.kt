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

package com.lebogang.gensysmusic.ui.charts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.databinding.ActivityChartsBinding
import com.lebogang.gensysmusic.ui.SettingsActivity
import com.lebogang.gensysmusic.ui.charts.deezer.DeezerAlbumActivity
import com.lebogang.gensysmusic.ui.charts.deezer.DeezerArtistActivity
import com.lebogang.gensysmusic.ui.charts.deezer.DeezerMusicActivity
import com.lebogang.gensysmusic.ui.charts.spotify.SpotifyActivity
import com.lebogang.gensysmusic.ui.local.HomeActivity
import com.lebogang.gensysmusic.ui.stream.StreamActivity

class ChartsActivity : AppCompatActivity() {
    private val bind:ActivityChartsBinding by lazy{ActivityChartsBinding.inflate(layoutInflater)}
    private val currentUser: FirebaseUser? by lazy { FirebaseAuth.getInstance().currentUser}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.app_name)
        setContentView(bind.root)
        setSupportActionBar(bind.toolbar)
        initViews()
        initBottomNavigation()
    }

    private fun initViews(){
        bind.deezerSongs.setOnClickListener {
            startActivity(Intent(this, DeezerMusicActivity::class.java)) }
        bind.deezerAlbums.setOnClickListener {
            startActivity(Intent(this, DeezerAlbumActivity::class.java)) }
        bind.deezerArtists.setOnClickListener {
            startActivity(Intent(this, DeezerArtistActivity::class.java)) }
        bind.spotifyNewReleases.setOnClickListener {
            startActivity(Intent(this, SpotifyActivity::class.java)) }
    }

    private fun initBottomNavigation(){
        bind.bottomNavigation.selectedItemId = R.id.charts
        bind.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.stream -> startActivity(Intent(this, StreamActivity::class.java))
                R.id.settings-> startActivity(Intent(this,SettingsActivity::class.java))
                R.id.local -> startActivity(Intent(this,HomeActivity::class.java))
            }
            false
        }
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

}
