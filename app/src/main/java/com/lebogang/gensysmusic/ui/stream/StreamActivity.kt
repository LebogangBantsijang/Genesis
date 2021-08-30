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

package com.lebogang.gensysmusic.ui.stream

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.databinding.ActivityStreamBinding
import com.lebogang.gensysmusic.ui.SettingsActivity
import com.lebogang.gensysmusic.ui.charts.ChartsActivity
import com.lebogang.gensysmusic.ui.local.HomeActivity
import com.lebogang.gensysmusic.ui.stream.adapters.AlbumAdapter
import com.lebogang.gensysmusic.ui.utils.ImageLoader
import com.lebogang.gensysmusic.ui.utils.ItemClickInterface
import com.lebogang.gensysmusic.ui.utils.Type

class StreamActivity : AppCompatActivity() {
    private val bind:ActivityStreamBinding by lazy{ ActivityStreamBinding.inflate(layoutInflater)}
    private val imageLoader:ImageLoader by lazy { ImageLoader(this) }
    private val albumAdapter = AlbumAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.app_name)
        setContentView(bind.root)
        setSupportActionBar(bind.toolbar)
        initBottomNavigation()
        getData()
    }

    private fun getData(){
        initAlbumRecyclerView()
    }

    private fun initAlbumRecyclerView(){
        albumAdapter.imageLoader = imageLoader
        albumAdapter.itemClickInterface = getItemClickInterface()
        bind.recyclerViewAlbums.layoutManager = LinearLayoutManager(this
            ,LinearLayoutManager.HORIZONTAL, false)
        bind.recyclerViewAlbums.adapter = albumAdapter
    }

    private fun getItemClickInterface() = object:ItemClickInterface{
        override fun onItemClick(view: View, item: Any?, type: Type) {
            //finish later
        }
    }

    private fun initBottomNavigation(){
        bind.bottomNavigation.selectedItemId = R.id.stream
        bind.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.charts -> startActivity(Intent(this, ChartsActivity::class.java))
                R.id.settings-> startActivity(Intent(this, SettingsActivity::class.java))
                R.id.local -> startActivity(Intent(this, HomeActivity::class.java))
            }
            false
        }
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

}
