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
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.lebogang.gensysmusic.GensysApp
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.database.local.models.Artist
import com.lebogang.gensysmusic.databinding.ActivityArtistsBinding
import com.lebogang.gensysmusic.ui.utils.ImageLoader
import com.lebogang.gensysmusic.ui.utils.ItemClickInterface
import com.lebogang.gensysmusic.ui.utils.ItemOptionsInterface
import com.lebogang.gensysmusic.ui.utils.Type
import com.lebogang.gensysmusic.ui.local.adapters.ArtistAdapter
import com.lebogang.gensysmusic.ui.local.viewmodel.ArtistViewModel
import com.lebogang.gensysmusic.ui.utils.ModelFactory
import com.lebogang.gensysmusic.utils.Keys

class ArtistsActivity : AppCompatActivity() {
    private val app:GensysApp by lazy { application as GensysApp }
    private val bind:ActivityArtistsBinding by lazy { ActivityArtistsBinding.inflate(layoutInflater) }
    private val artistViewModel:ArtistViewModel by lazy { ModelFactory(app).getArtistViewModel() }
    private val adapter = ArtistAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.artists)
        setContentView(bind.root)
        initToolbar()
        initRecyclerView()
        artistViewModel.registerObserver(app)
    }

    private fun initToolbar(){
        setSupportActionBar(bind.toolbar)
        bind.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initRecyclerView(){
        artistViewModel.getArtists().observe(this,{
            adapter.setData(it)
        })
        adapter.imageLoader = ImageLoader(this)
        adapter.itemOptionsInterface = getFavouriteInterface()
        adapter.itemClickInterface = getItemClick()
        bind.recyclerView.layoutManager = LinearLayoutManager(this)
        bind.recyclerView.adapter = adapter
    }

    private fun getFavouriteInterface() = object : ItemOptionsInterface {
        override fun onOptionsClick(item: Any) {
            val artist = item as Artist
            artist.isFavourite = !artist.isFavourite
            artistViewModel.addArtist(artist)
        }
    }

    private fun getItemClick() = object : ItemClickInterface {
        override fun onItemClick(view: View, item: Any?, type: Type) {
            val artist = item as Artist
            val intent = Intent(this@ArtistsActivity,ArtistDetailsActivity::class.java)
            intent.putExtra(Keys.ARTIST_KEY,artist.id)
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
        artistViewModel.unregisterObserver(app)
    }

}
