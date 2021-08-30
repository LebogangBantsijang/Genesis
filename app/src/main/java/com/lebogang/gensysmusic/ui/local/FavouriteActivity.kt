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

package com.lebogang.gensysmusic.ui.local

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.databinding.ActivityFavouriteBinding
import com.lebogang.gensysmusic.ui.local.fragments.PagerAdapter

class FavouriteActivity : AppCompatActivity() {
    private val bind:ActivityFavouriteBinding by lazy {ActivityFavouriteBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.favourite)
        setContentView(bind.root)
        initToolbar()
        initViewPager()
    }

    private fun initToolbar(){
        setSupportActionBar(bind.toolbar)
        bind.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initViewPager(){
        bind.viewpager.adapter = PagerAdapter(this)
        TabLayoutMediator(bind.tabs,bind.viewpager) { tab, position ->
            when(position){
                0 ->{
                    tab.text = getString(R.string.music)
                    tab.icon = getDrawable(R.drawable.ic_music_ios)
                }
                1 ->{
                    tab.text = getString(R.string.albums)
                    tab.icon = getDrawable(R.drawable.ic_album_ios)
                }
                2 ->{
                    tab.text = getString(R.string.artists)
                    tab.icon = getDrawable(R.drawable.ic_artist_ios)
                }
            }
        }.attach()
    }
}
