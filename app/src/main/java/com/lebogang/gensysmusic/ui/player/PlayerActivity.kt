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

package com.lebogang.gensysmusic.ui.player

import android.content.Intent
import android.os.Bundle
import com.lebogang.gensysmusic.GensysApp
import com.lebogang.gensysmusic.ui.utils.ImageLoader
import com.lebogang.gensysmusic.ui.utils.ModelFactory
import com.lebogang.gensysmusic.ui.local.viewmodel.MusicViewModel

class PlayerActivity : PlayerHelper() {
    private val adapter = ViewpagerAdapter()
    private val app: GensysApp by lazy { application as GensysApp }
    private val musicViewModel: MusicViewModel by lazy{ ModelFactory(app).getMusicViewModel()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar()
        initViewPager()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    private fun initToolbar(){
        setSupportActionBar(bind.toolbar)
        bind.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initViewPager(){
        musicViewModel.getMusic().observe(this,{adapter.setData(it)})
        adapter.context = this
        adapter.imageLoader = ImageLoader(this)
        bind.viewpager.adapter = adapter
        bind.viewpager.setPageTransformer { page, _ ->
            page.scaleX = 0.85f
            page.scaleY = 0.85f
        }
    }

}
