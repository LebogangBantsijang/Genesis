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

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.lebogang.gensysmusic.GensysApp
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.database.local.models.Album
import com.lebogang.gensysmusic.databinding.ActivityAlbumsBinding
import com.lebogang.gensysmusic.ui.utils.ImageLoader
import com.lebogang.gensysmusic.ui.utils.ItemClickInterface
import com.lebogang.gensysmusic.ui.utils.ItemOptionsInterface
import com.lebogang.gensysmusic.ui.utils.Type
import com.lebogang.gensysmusic.ui.local.adapters.AlbumAdapter
import com.lebogang.gensysmusic.ui.local.viewmodel.AlbumViewModel
import com.lebogang.gensysmusic.ui.utils.ModelFactory
import com.lebogang.gensysmusic.utils.Keys

class AlbumsActivity : AppCompatActivity() {
    private val app:GensysApp by lazy { application as GensysApp }
    private val bind:ActivityAlbumsBinding by lazy{ActivityAlbumsBinding.inflate(layoutInflater)}
    private val adapter = AlbumAdapter()
    private val albumsViewModel: AlbumViewModel by lazy{ ModelFactory(app).getAlbumViewModel()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.albums)
        setContentView(bind.root)
        initToolbar()
        initRecyclerView()
        albumsViewModel.registerObserver(app)
    }

    private fun initToolbar(){
        setSupportActionBar(bind.toolbar)
        bind.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initRecyclerView(){
        adapter.imageLoader = ImageLoader(this)
        adapter.itemOptionsInterface = getFavouriteInterface()
        adapter.itemClickInterface = getItemClick()
        albumsViewModel.getAlbums().observe(this,{
            adapter.setData(it)
        })
        bind.recyclerView.layoutManager = LinearLayoutManager(this)
        bind.recyclerView.adapter = adapter
    }

    private fun getFavouriteInterface() = object : ItemOptionsInterface {
        override fun onOptionsClick(item: Any) {
            val album = item as Album
            album.isFavourite = !album.isFavourite
            albumsViewModel.addAlbum(album)
        }
    }

    private fun getItemClick() = object : ItemClickInterface {
        override fun onItemClick(view: View, item: Any?, type: Type) {
            val album = item as Album
            val intent = Intent(this@AlbumsActivity,AlbumDetailsActivity::class.java)
            intent.putExtra(Keys.ALBUM_KEY,album.id)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.application_search_refresh, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.refresh->{
                app.localContent.onUpdate()
                true
            }
            else->false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        albumsViewModel.unregisterObserver(app)
    }

}
