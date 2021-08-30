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
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.lebogang.gensysmusic.GensysApp
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.database.local.models.Genre
import com.lebogang.gensysmusic.databinding.ActivityGenresBinding
import com.lebogang.gensysmusic.ui.utils.ItemClickInterface
import com.lebogang.gensysmusic.ui.utils.Type
import com.lebogang.gensysmusic.ui.local.adapters.GenreAdapter
import com.lebogang.gensysmusic.ui.local.viewmodel.GenreViewModel
import com.lebogang.gensysmusic.ui.utils.ModelFactory
import com.lebogang.gensysmusic.utils.Keys

class GenresActivity : AppCompatActivity() {
    private val app:GensysApp by lazy { application as GensysApp }
    private val bind:ActivityGenresBinding by lazy {ActivityGenresBinding.inflate(layoutInflater)}
    private val adapter = GenreAdapter()
    private val genreViewModel:GenreViewModel by lazy { ModelFactory(app).getGenreViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.genres)
        setContentView(bind.root)
        initToolbar()
        initRecyclerView()
        genreViewModel.registerObserver(app)
    }

    private fun initToolbar(){
        setSupportActionBar(bind.toolbar)
        bind.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initRecyclerView(){
        adapter.itemClickInterface = getItemClickInterface()
        genreViewModel.getGenres().observe(this,{
            adapter.setData(it)
        })
        bind.recyclerView.layoutManager = StaggeredGridLayoutManager(2,
            StaggeredGridLayoutManager.VERTICAL)
        bind.recyclerView.adapter = adapter
    }

    private fun getItemClickInterface() = object : ItemClickInterface {
        override fun onItemClick(view: View, item: Any?, type: Type) {
            val genre = item as Genre
            val intent = Intent(this@GenresActivity,GenreDetailsActivity::class.java)
            intent.putExtra(Keys.GENRE_KEY,genre)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.application_refresh, menu)
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
        genreViewModel.unregisterObserver(app)
    }

}
