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

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.database.local.models.Music
import com.lebogang.gensysmusic.databinding.ActivityInformationBinding
import com.lebogang.gensysmusic.ui.utils.ImageLoader
import com.lebogang.gensysmusic.ui.utils.Type
import com.lebogang.gensysmusic.utils.Keys
import java.util.*

class InformationActivity : AppCompatActivity() {
    private val bind:ActivityInformationBinding by lazy{ActivityInformationBinding.inflate(layoutInflater)}
    private var music:Music? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.info)
        setContentView(bind.root)
        music = intent.extras?.getParcelable(Keys.MUSIC_KEY)!!
        initToolbar()
        initViews()
    }

    private fun initToolbar(){
        setSupportActionBar(bind.toolbar)
        bind.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initViews(){
        music?.let {
            bind.titleTextView.text = it.title
            bind.artistTextView.text = it.artist
            bind.albumTextView.text = it.album
            bind.durationTextView.text = it.time
            bind.sizeTextView.text = it.size
            bind.favouriteTextView.text = it.isFavourite.toString()
            bind.yearTextView.text = if (it.date <= 0) getString(R.string.unknown) else Date(it.date).toString()
            ImageLoader(this).loadImage(it.artUri, Type.MUSIC,bind.imageView)
        }
    }

}
