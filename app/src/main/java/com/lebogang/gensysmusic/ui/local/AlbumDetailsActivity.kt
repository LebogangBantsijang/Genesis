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
import androidx.recyclerview.widget.LinearLayoutManager
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.GensysApp
import com.lebogang.gensysmusic.database.local.models.Music
import com.lebogang.gensysmusic.databinding.ActivityAlbumDetailsBinding
import com.lebogang.gensysmusic.ui.local.adapters.MusicAdapter
import com.lebogang.gensysmusic.ui.local.dialogs.MusicOptionsDialog
import com.lebogang.gensysmusic.ui.local.viewmodel.AlbumViewModel
import com.lebogang.gensysmusic.ui.local.viewmodel.MusicViewModel
import com.lebogang.gensysmusic.ui.utils.*
import com.lebogang.gensysmusic.utils.Keys

class AlbumDetailsActivity : AppCompatActivity() {
    private val bind:ActivityAlbumDetailsBinding by lazy{ActivityAlbumDetailsBinding.inflate(layoutInflater)}
    private val app: GensysApp by lazy { application as GensysApp }
    private val musicViewModel:MusicViewModel by lazy { ModelFactory(app).getMusicViewModel() }
    private val albumViewModel: AlbumViewModel by lazy{ ModelFactory(app).getAlbumViewModel()}
    private val imageLoader: ImageLoader by lazy { ImageLoader(this) }
    private val musicAdapter = MusicAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = ""
        setContentView(bind.root)
        val albumId = intent.extras?.getLong(Keys.ALBUM_KEY)!!
        initToolbar()
        initData(albumId)
        initMusic(albumId)
    }

    private fun initToolbar(){
        setSupportActionBar(bind.toolbar)
        bind.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initData(id:Long){
        albumViewModel.getAlbums(id).observe(this,{
            bind.albumTitleTextView.text = it.getItemTitle()
            bind.albumArtistTextView.text = it.getItemArtist()
            val date:String = if (it.date > 0) it.date.toString() else "Unknown"
            val subTitle = date + " - " + it.getItemDuration() + " - " + it.getItemSize()
            bind.albumSubtitleTextView.text = subTitle
            if (it.getIsItemFavourite())
                bind.imageButton.setImageResource(R.drawable.ic_melting_heart_filled_ios)
            else
                bind.imageButton.setImageResource(R.drawable.ic_melting_heart_ios)
            imageLoader.loadImage(it.getItemArt(), Type.ALBUM,bind.imageView)
        })
    }


    private fun initMusic(albumId: Long){
        musicAdapter.selectableBackground = Colors.getSelectableBackground(theme)
        musicAdapter.imageLoader = imageLoader
        musicAdapter.hideFavouriteButton = true
        musicAdapter.optionsClickInterface = getOptionClickInterface()
        musicViewModel.getAlbumMusic(albumId).observe(this,{list->
            musicAdapter.setData(list)
        })
        bind.recyclerView.layoutManager = LinearLayoutManager(this)
        bind.recyclerView.adapter = musicAdapter
    }

    private fun getOptionClickInterface(): ItemOptionsInterface = object : ItemOptionsInterface {
        override fun onOptionsClick(item: Any) {
            val musicOptionsDialog = MusicOptionsDialog()
            musicOptionsDialog.music = item as Music
            musicOptionsDialog.showExtraOption = false
            musicOptionsDialog.showNow(supportFragmentManager,null)
        }
    }

}
