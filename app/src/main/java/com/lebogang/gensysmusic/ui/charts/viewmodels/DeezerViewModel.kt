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

package com.lebogang.gensysmusic.ui.charts.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lebogang.gensysmusic.online.deezer.DeezerRepository
import com.lebogang.gensysmusic.online.deezer.models.Album
import com.lebogang.gensysmusic.online.deezer.models.Artist
import com.lebogang.gensysmusic.online.deezer.models.Music
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeezerViewModel(private val deezerRepository: DeezerRepository): ViewModel() {
    val musicLiveData = MutableLiveData<List<Music>>()
    val albumLiveData = MutableLiveData<List<Album>>()
    val artistLiveData = MutableLiveData<List<Artist>>()

    fun getMusic() = viewModelScope.launch {
        deezerRepository.getMusic(getMusicCallbacks())
    }

    fun getAlbums() = viewModelScope.launch {
        deezerRepository.getAlbums(getAlbumCallbacks())
    }

    fun getArtists() = viewModelScope.launch {
        deezerRepository.getArtists(getArtistCallbacks())
    }

    fun getAlbumMusic(id:Int) = viewModelScope.launch {
        deezerRepository.getAlbumMusic(id,getMusicCallbacks())
    }

    fun getArtistMusic(id:Int) = viewModelScope.launch {
        deezerRepository.getArtistMusic(id,getMusicCallbacks())
    }

    private fun getMusicCallbacks() = object : Callback<Music.Members> {
        override fun onResponse(call: Call<Music.Members>, response: Response<Music.Members>) {
            musicLiveData.postValue(response.body()?.data)
        }
        override fun onFailure(call: Call<Music.Members>, t: Throwable) {
            //failed
        }
    }

    private fun getAlbumCallbacks() = object : Callback<Album.Members> {
        override fun onResponse(call: Call<Album.Members>, response: Response<Album.Members>) {
            albumLiveData.postValue(response.body()?.data)
        }
        override fun onFailure(call: Call<Album.Members>, t: Throwable) {
            //failed
        }
    }

    private fun getArtistCallbacks() = object : Callback<Artist.Members> {
        override fun onResponse(call: Call<Artist.Members>, response: Response<Artist.Members>) {
            artistLiveData.postValue(response.body()?.data)
        }
        override fun onFailure(call: Call<Artist.Members>, t: Throwable) {
            //failed
        }
    }
}
