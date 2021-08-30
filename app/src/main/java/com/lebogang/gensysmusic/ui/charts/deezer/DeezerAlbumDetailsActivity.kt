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
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.databinding.ActivityDeezerAlbumDetailsBinding
import com.lebogang.gensysmusic.online.deezer.models.Album
import com.lebogang.gensysmusic.ui.utils.ImageLoader
import com.lebogang.gensysmusic.ui.utils.ItemClickInterface
import com.lebogang.gensysmusic.ui.utils.Type
import com.lebogang.gensysmusic.ui.charts.deezer.adapters.MusicAdapter
import com.lebogang.gensysmusic.ui.utils.ModelFactory
import com.lebogang.gensysmusic.utils.Keys

class DeezerAlbumDetailsActivity : AppCompatActivity() {
    private val bind: ActivityDeezerAlbumDetailsBinding by lazy{
        ActivityDeezerAlbumDetailsBinding.inflate(layoutInflater) }
    private val deezerViewModel by lazy { ModelFactory(application as GensysApp).getDeezerViewModel() }
    private var album: Album? = null
    private val imageLoader: ImageLoader by lazy{ ImageLoader(this) }
    private val adapter = MusicAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = ""
        setContentView(bind.root)
        album = intent.extras?.getParcelable(Keys.ALBUM_KEY)
        initToolbar()
        initRecyclerView()
        initViews()
    }

    private fun initToolbar(){
        setSupportActionBar(bind.toolbar)
        bind.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initViews(){
        album?.let {
            bind.titleView.text = it.title
            bind.subtitleView.text = it.artist.title
            if (it.hasExplicitLyrics)
                bind.explicitView.setTextColor(getColor(R.color.primaryColor))
            imageLoader.loadImage(it.coverMedium, Type.ALBUM,bind.imageView)
            adapter.artUrl = it.coverSmall
            deezerViewModel.getAlbumMusic(it.id)
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
