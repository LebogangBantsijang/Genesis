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
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lebogang.gensysmusic.GensysApp
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.database.local.models.Playlist
import com.lebogang.gensysmusic.databinding.ActivityPlaylistBinding
import com.lebogang.gensysmusic.ui.utils.ItemClickInterface
import com.lebogang.gensysmusic.ui.utils.ItemOptionsInterface
import com.lebogang.gensysmusic.ui.utils.Type
import com.lebogang.gensysmusic.ui.local.adapters.PlaylistAdapter
import com.lebogang.gensysmusic.ui.local.playlist.dialogs.PlaylistDialog
import com.lebogang.gensysmusic.ui.local.playlist.dialogs.PlaylistOptionsDialog
import com.lebogang.gensysmusic.ui.utils.ModelFactory
import com.lebogang.gensysmusic.ui.local.viewmodel.PlaylistViewModel
import com.lebogang.gensysmusic.utils.Keys

class PlaylistActivity : AppCompatActivity() {
    private val bind: ActivityPlaylistBinding by lazy { ActivityPlaylistBinding.inflate(layoutInflater) }
    private val app:GensysApp by lazy{application as GensysApp}
    private val playlistViewModel: PlaylistViewModel by lazy{ ModelFactory(app).getPlaylistViewModel()}
    private val adapter = PlaylistAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.playlists)
        setContentView(bind.root)
        initToolbar()
        initRecyclerView()
    }

    private fun initToolbar(){
        setSupportActionBar(bind.toolbar)
        bind.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initRecyclerView(){
        adapter.itemOptionsInterface = getItemsOptionsItemInterface()
        adapter.itemClickInterface = getItemClickInterface()
        playlistViewModel.getPlaylists().observe(this,{
            adapter.setData(it)
            bind.progressBar.visibility = View.GONE
        })
        bind.recyclerView.layoutManager = LinearLayoutManager(this)
        bind.recyclerView.adapter = adapter
        bind.newButton.setOnClickListener{PlaylistDialog().showNow(supportFragmentManager,null)}
    }

    private fun getItemClickInterface() = object : ItemClickInterface {
        override fun onItemClick(view: View, item: Any?, type: Type) {
            val playlist = item as Playlist
            val intent = Intent(this@PlaylistActivity, PlaylistDetailsActivity::class.java)
            intent.putExtra(Keys.PLAYLIST_KEY, playlist)
            startActivity(intent)
        }
    }

    private fun getItemsOptionsItemInterface() = object : ItemOptionsInterface {
        override fun onOptionsClick(item: Any) {
            val playlist = item as Playlist
            val playlistOptionsDialog = PlaylistOptionsDialog()
            playlistOptionsDialog.playlist = playlist
            playlistOptionsDialog.showNow(supportFragmentManager,null)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.application_playlist, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.addPlaylist -> {
                PlaylistDialog().showNow(supportFragmentManager,null)
                true
            }
            R.id.clearAll ->{
                areYouSure()
                true
            }
            else -> false
        }
    }

    private fun areYouSure(){
        MaterialAlertDialogBuilder(this)
            .setMessage("Remove all playlists?")
            .setNegativeButton("No",null)
            .setPositiveButton("Yes") { _, _ ->
                playlistViewModel.reset()
            }.create().show()
    }
}
