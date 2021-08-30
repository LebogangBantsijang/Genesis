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
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.lebogang.gensysmusic.GensysApp
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.databinding.ActivityDeezerMusicBinding
import com.lebogang.gensysmusic.ui.utils.ImageLoader
import com.lebogang.gensysmusic.ui.utils.ItemClickInterface
import com.lebogang.gensysmusic.ui.utils.Type
import com.lebogang.gensysmusic.ui.charts.deezer.adapters.MusicAdapter
import com.lebogang.gensysmusic.ui.utils.ModelFactory

class DeezerMusicActivity : AppCompatActivity() {
    private val bind: ActivityDeezerMusicBinding by lazy{ ActivityDeezerMusicBinding.inflate(layoutInflater) }
    private val deezerViewModel by lazy { ModelFactory(application as GensysApp).getDeezerViewModel() }
    private val adapter = MusicAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.music)
        setContentView(bind.root)
        initToolbar()
        initRecyclerView()
    }

    private fun initToolbar(){
        setSupportActionBar(bind.toolbar)
        bind.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initRecyclerView(){
        adapter.imageLoader = ImageLoader(this)
        adapter.itemClickInterface = getItemClickInterface()
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

    override fun onResume() {
        super.onResume()
        deezerViewModel.getMusic()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.application_refresh, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.refresh -> {
                bind.progressBar.visibility = View.VISIBLE
                deezerViewModel.getMusic()
                true
            }
            else-> false
        }
    }
}
