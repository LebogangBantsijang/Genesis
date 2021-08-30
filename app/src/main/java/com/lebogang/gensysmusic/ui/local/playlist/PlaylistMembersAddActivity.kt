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

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.lebogang.gensysmusic.GensysApp
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.database.local.models.Playlist
import com.lebogang.gensysmusic.databinding.ActivityPlaylistMembersAddBinding
import com.lebogang.gensysmusic.ui.utils.Colors
import com.lebogang.gensysmusic.ui.local.Sort
import com.lebogang.gensysmusic.ui.local.playlist.adapters.MusicAddAdapter
import com.lebogang.gensysmusic.ui.utils.ModelFactory
import com.lebogang.gensysmusic.ui.local.viewmodel.MusicViewModel
import com.lebogang.gensysmusic.ui.local.viewmodel.PlaylistViewModel
import com.lebogang.gensysmusic.utils.Keys

class PlaylistMembersAddActivity : AppCompatActivity() {
    private val bind: ActivityPlaylistMembersAddBinding by lazy{
        ActivityPlaylistMembersAddBinding.inflate(layoutInflater) }
    private val app: GensysApp by lazy { application as GensysApp }
    private val playlistViewModel: PlaylistViewModel by lazy{ ModelFactory(app).getPlaylistViewModel()}
    private val musicViewModel: MusicViewModel by lazy { ModelFactory(app).getMusicViewModel() }
    private val adapter = MusicAddAdapter()
    private var playlist:Playlist? = null
    private var itemsIdsToRemove = mutableListOf<Long>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playlist = intent.getParcelableExtra(Keys.PLAYLIST_KEY)
        title = getString(R.string.select_items)
        setContentView(bind.root)
        initToolbar()
        initPlaylist()
        initRecyclerView()
        initChipGroup()
    }

    private fun initToolbar(){
        setSupportActionBar(bind.toolbar)
        bind.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initChipGroup(){
        bind.chipGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.titleChip -> adapter.sort(Sort.TITLE)
                R.id.recentChip -> adapter.sort(Sort.NEW)
                R.id.durationChip -> adapter.sort(Sort.DURATION)
            }
        }
    }

    private fun initRecyclerView(){
        adapter.selectableBackground = Colors.getSelectableBackground(theme)
        musicViewModel.getMusic().observe(this,{
            for (index in (it.size - 1) downTo 0 step 1){
                val music = it[index]
                if (itemsIdsToRemove.contains(music.id))
                    it.remove(music)
            }
            adapter.setData(it)
        })
        bind.recyclerView.layoutManager = LinearLayoutManager(this)
        bind.recyclerView.adapter = adapter
    }

    private fun initPlaylist(){
        playlist?.let { item->
            playlistViewModel.getPlaylistMusicIds(item.id).observe(this,{
                it.forEach {item-> itemsIdsToRemove.add(item.musicId) }
                bind.progressBar.visibility = View.GONE
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.application_select_playlist_items,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.save->{
                if (adapter.selectedItems.isNotEmpty()){
                    playlist?.let {
                        val memberList = mutableListOf<Playlist.Members>()
                        for (x in adapter.selectedItems)
                            memberList.add(Playlist.Members(0,it.id,x))
                        playlistViewModel.addPlaylistMembers(memberList)
                        Toast.makeText(this, getString(R.string.items_added),Toast.LENGTH_SHORT).show()
                        onBackPressed()
                    }
                }
                true
            }
            else->false
        }
    }
}
