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

package com.lebogang.gensysmusic.ui.local.viewmodel

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.lebogang.gensysmusic.GensysApp
import com.lebogang.gensysmusic.database.local.FavouriteRepository
import com.lebogang.gensysmusic.database.local.MusicRepository
import com.lebogang.gensysmusic.database.local.models.Favourite
import com.lebogang.gensysmusic.database.local.models.Music
import com.lebogang.gensysmusic.database.local.scan.LocalContent
import com.lebogang.gensysmusic.database.local.scan.MediaStoreObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MusicViewModel(private val musicRepository: MusicRepository,
                     private val favouriteRepository: FavouriteRepository): ViewModel() {
    lateinit var localContent: LocalContent
    val searchLiveData = MutableLiveData<MutableList<Music>>()


    private val mediaStoreObserver = object : MediaStoreObserver(){
        override fun onContentChanged() {
            localContent.onUpdate()
        }
    }

    fun getFavourites() = musicRepository.getFavouriteMusic().asLiveData()

    fun getMusic() = musicRepository.getMusic().asLiveData()

    fun getAlbumMusic(albumId:Long) = musicRepository.getAlbumMusic(albumId).asLiveData()

    fun getArtistMusic(artistId:Long) = musicRepository.getArtistMusic(artistId).asLiveData()

    fun getMusic(idArray: List<Long>) = musicRepository.getMusic(idArray).asLiveData()

    fun addMusic(music: Music) = viewModelScope.launch(Dispatchers.IO) {
        if (music.isFavourite)
            favouriteRepository.addMediaId(Favourite(music.id))
        else
            favouriteRepository.removeMediaId(Favourite(music.id))
        musicRepository.addMusic(music)
    }

    fun searchItems(query:String) = viewModelScope.launch(Dispatchers.IO) {
        val queryFormatted = "%$query%"
        val list = musicRepository.searchItems(queryFormatted)
        searchLiveData.postValue(list)
    }

    @SuppressLint("InlinedApi")
    fun update(context:Context, music:Music) = viewModelScope.launch(Dispatchers.IO) {
        val uri = Uri.parse(music.contentUri)
        val values = ContentValues().apply {
            put(MediaStore.Audio.Media.TITLE,music.title)
            put(MediaStore.Audio.Media.ALBUM,music.album)
            put(MediaStore.Audio.Media.ARTIST,music.artist)
        }
        val results = context.contentResolver.update(uri,values, (MediaStore.Audio.Media._ID + " =?"),
            arrayOf(music.id.toString()))
        if (results>0)
            musicRepository.addMusic(music)
    }

    fun registerObserver(application: GensysApp){
        localContent = application.localContent
        application.contentResolver.registerContentObserver(LocalContent.ResolverConstants.MUSIC_URI
            ,true,mediaStoreObserver)
    }

    fun unregisterObserver(application: GensysApp){
        application.contentResolver.unregisterContentObserver(mediaStoreObserver)
    }

}
