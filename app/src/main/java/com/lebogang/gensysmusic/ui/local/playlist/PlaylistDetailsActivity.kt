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

package com.lebogang.gensysmusic.ui.local.playlist

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.lebogang.gensysmusic.GensysApp
import com.lebogang.gensysmusic.database.local.models.Music
import com.lebogang.gensysmusic.database.local.models.Playlist
import com.lebogang.gensysmusic.databinding.ActivityPlaylistDetailsBinding
import com.lebogang.gensysmusic.ui.utils.ImageLoader
import com.lebogang.gensysmusic.ui.utils.ItemOptionsInterface
import com.lebogang.gensysmusic.ui.local.playlist.adapters.MusicAdapter
import com.lebogang.gensysmusic.ui.utils.ModelFactory
import com.lebogang.gensysmusic.ui.local.viewmodel.MusicViewModel
import com.lebogang.gensysmusic.ui.local.viewmodel.PlaylistViewModel
import com.lebogang.gensysmusic.utils.Keys

class PlaylistDetailsActivity : AppCompatActivity() {
    private val bind: ActivityPlaylistDetailsBinding by lazy {
        ActivityPlaylistDetailsBinding.inflate(layoutInflater) }
    private val app: GensysApp by lazy { application as GensysApp }
    private val playlistViewModel: PlaylistViewModel by lazy{ ModelFactory(app).getPlaylistViewModel()}
    private val musicViewModel: MusicViewModel by lazy { ModelFactory(app).getMusicViewModel() }
    private var playlist: Playlist? = null
    private val adapter = MusicAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playlist = intent.getParcelableExtra(Keys.PLAYLIST_KEY)
        title = playlist?.title
        setContentView(bind.root)
        initToolbar()
        initData()
        initViews()
    }

    private fun initToolbar(){
        setSupportActionBar(bind.toolbar)
        bind.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initViews(){
        bind.addButton.setOnClickListener {
            playlist?.let {
                val intent = Intent(this, PlaylistMembersAddActivity::class.java)
                intent.putExtra(Keys.PLAYLIST_KEY,it)
                startActivity(intent)
            }
        }
    }

    private fun initData(){
        playlist?.let {playlist->
            playlistViewModel.getPlaylistMusicIds(playlist.id).observe(this,{
                val idArrayList = mutableListOf<Long>()
                it.forEach { item-> idArrayList.add(item.musicId) }
                initRecyclerView(idArrayList)
            })
        }
    }

    private fun initRecyclerView(idArray: List<Long>){
        val typedValue = TypedValue()
        theme.resolveAttribute(android.R.attr.selectableItemBackground, typedValue,true)
        adapter.selectableBackgroundId = typedValue.resourceId
        adapter.imageLoader = ImageLoader(this)
        adapter.favouriteClickInterface = getFavouriteInterface()
        adapter.optionsClickInterface = getOptionClickInterface()
        musicViewModel.getMusic(idArray).observe(this,{
            adapter.setData(it)
            bind.progressBar.visibility = View.GONE
        })
        bind.recyclerView.layoutManager = LinearLayoutManager(this)
        bind.recyclerView.adapter = adapter
    }

    private fun getFavouriteInterface() = object : ItemOptionsInterface {
        override fun onOptionsClick(item: Any) {
            val music = item as Music
            music.isFavourite = !music.isFavourite
            musicViewModel.addMusic(music)
        }
    }

    private fun getOptionClickInterface() = object : ItemOptionsInterface {
        override fun onOptionsClick(item: Any) {
            playlist?.let {
                val music = item as Music
                playlistViewModel.deletePlaylistMusic(it.id, music.id)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(Keys.PLAYLIST_KEY,playlist)
        super.onSaveInstanceState(outState)
    }

}
