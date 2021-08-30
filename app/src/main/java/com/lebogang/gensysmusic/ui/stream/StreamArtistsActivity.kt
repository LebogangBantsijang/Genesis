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

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.databinding.ActivityStreamArtistsBinding
import com.lebogang.gensysmusic.ui.utils.ItemClickInterface
import com.lebogang.gensysmusic.ui.utils.Type
import com.lebogang.gensysmusic.ui.stream.adapters.ArtistAdapter

class StreamArtistsActivity : AppCompatActivity() {
    private val bind:ActivityStreamArtistsBinding by lazy{ActivityStreamArtistsBinding.inflate(layoutInflater)}
    private val adapter = ArtistAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.artists)
        setContentView(bind.root)
        initToolbar()
        initRecyclerView()
    }

    private fun initToolbar(){
        setSupportActionBar(bind.toolbar)
        bind.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initRecyclerView(){
        adapter.itemClickInterface = getItemClickInterface()
        bind.recyclerView.layoutManager = StaggeredGridLayoutManager(2,1)
        bind.recyclerView.adapter = adapter
    }

    private fun getItemClickInterface() = object : ItemClickInterface {
        override fun onItemClick(view: View, item: Any?, type: Type) {
            TODO("Not yet implemented")
        }
    }

}
