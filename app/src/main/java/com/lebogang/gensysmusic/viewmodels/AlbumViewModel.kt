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

package com.lebogang.gensysmusic.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lebogang.gensysmusic.data.models.Album
import com.lebogang.gensysmusic.data.models.Artist
import com.lebogang.gensysmusic.data.repositories.AlbumRepo
import com.lebogang.gensysmusic.viewmodels.utils.OnContentChanged
import kotlinx.coroutines.launch

class AlbumViewModel(private val albumRepo: AlbumRepo) :ViewModel(), OnContentChanged{

    val liveData :MutableLiveData<MutableList<Album>> = MutableLiveData()
    val liveDataArtist:MutableLiveData<MutableList<Artist>> = MutableLiveData()

    fun registerContentObserver(){
        albumRepo.registerObserver(this)
    }

    fun unregisterContentContentObserver(){
        albumRepo.unregisterObserver()
    }

    fun getAlbums() = viewModelScope.launch {
        liveData.postValue(albumRepo.getAlbums())
    }

    fun getAlbums(albumsName:String):Album? = albumRepo.getAlbums(albumsName)

    override fun onMediaChanged() {
        getAlbums()
    }

}
