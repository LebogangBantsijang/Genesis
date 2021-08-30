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

package com.lebogang.gensysmusic.ui.charts.deezer

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.lebogang.gensysmusic.GensysApp
import com.lebogang.gensysmusic.databinding.ActivityDeezerArtistDetailsBinding
import com.lebogang.gensysmusic.online.deezer.models.Artist
import com.lebogang.gensysmusic.ui.utils.ImageLoader
import com.lebogang.gensysmusic.ui.utils.ItemClickInterface
import com.lebogang.gensysmusic.ui.utils.Type
import com.lebogang.gensysmusic.ui.charts.deezer.adapters.MusicAdapter
import com.lebogang.gensysmusic.ui.utils.ModelFactory
import com.lebogang.gensysmusic.utils.Keys

class DeezerArtistDetailsActivity : AppCompatActivity() {
    private val bind: ActivityDeezerArtistDetailsBinding by lazy{
        ActivityDeezerArtistDetailsBinding.inflate(layoutInflater) }
    private val deezerViewModel by lazy { ModelFactory(application as GensysApp).getDeezerViewModel() }
    private val adapter = MusicAdapter()
    private val imageLoader: ImageLoader by lazy{ ImageLoader(this) }
    private var artist: Artist? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = ""
        setContentView(bind.root)
        artist = intent.extras?.getParcelable(Keys.ARTIST_KEY)
        initToolbar()
        initRecyclerView()
        initViews()
    }

    private fun initToolbar(){
        setSupportActionBar(bind.toolbar)
        bind.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initViews(){
        artist?.let {
            bind.titleView.text = it.title
            imageLoader.loadImage(it.coverMedium, Type.ALBUM,bind.imageView)
            adapter.artUrl = it.coverSmall
            deezerViewModel.getArtistMusic(it.id)
        }
    }

    private fun initRecyclerView(){
        adapter.imageLoader = imageLoader
        adapter.itemClickInterface = getItemClickInterface()
        adapter.showMusicNumber = false
        deezerViewModel.musicLiveData.observe(this,{
            adapter.setData(it)
            bind.progressBar.visibility = View.GONE
        })
        bind.recyclerView.layoutManager = LinearLayoutManager(this)
        bind.recyclerView.adapter = adapter
    }

    private fun getItemClickInterface() = object: ItemClickInterface {
        override fun onItemClick(view: View, item: Any?, type: Type) {
            TODO("Not yet implemented")
        }
    }

}
