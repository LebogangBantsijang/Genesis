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

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.lebogang.gensysmusic.GensysApp
import com.lebogang.gensysmusic.database.local.models.Music
import com.lebogang.gensysmusic.databinding.ActivitySearchBinding
import com.lebogang.gensysmusic.ui.local.adapters.MusicAdapter
import com.lebogang.gensysmusic.ui.local.dialogs.MusicOptionsDialog
import com.lebogang.gensysmusic.ui.local.viewmodel.MusicViewModel
import com.lebogang.gensysmusic.ui.utils.Colors
import com.lebogang.gensysmusic.ui.utils.ImageLoader
import com.lebogang.gensysmusic.ui.utils.ItemOptionsInterface
import com.lebogang.gensysmusic.ui.utils.ModelFactory

class SearchActivity : AppCompatActivity() {
    private val bind:ActivitySearchBinding by lazy{ActivitySearchBinding.inflate(layoutInflater)}
    private val app: GensysApp by lazy { application as GensysApp }
    private val musicViewModel: MusicViewModel by lazy{ ModelFactory(app).getMusicViewModel()}
    private val adapter = MusicAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)
        initToolbar()
        initRecyclerView()
        initSearchView()
    }

    private fun initToolbar(){
        setSupportActionBar(bind.toolbar)
        bind.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initSearchView(){
        bind.searchEditText.doOnTextChanged { text, _, _, _ ->
            text?.let {
                musicViewModel.searchItems(it.toString())
            }
        }
    }

    private fun initRecyclerView(){
        adapter.selectableBackground = Colors.getSelectableBackground(theme)
        adapter.imageLoader = ImageLoader(this)
        adapter.hideFavouriteButton = true
        adapter.optionsClickInterface = getOptionClickInterface()
        musicViewModel.searchLiveData.observe(this,{adapter.setData(it)})
        bind.recyclerView.layoutManager = LinearLayoutManager(this)
        bind.recyclerView.adapter = adapter
    }

    private fun getOptionClickInterface() = object : ItemOptionsInterface {
        override fun onOptionsClick(item: Any) {
            val musicOptionsDialog = MusicOptionsDialog()
            musicOptionsDialog.music = item as Music
            musicOptionsDialog.showNow(supportFragmentManager,null)
        }
    }

}
