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
import com.lebogang.gensysmusic.online.spotify.SpotifyRepository
import com.lebogang.gensysmusic.online.spotify.models.Album
import com.lebogang.gensysmusic.online.spotify.models.AlbumElements
import com.lebogang.gensysmusic.online.spotify.models.Music
import com.lebogang.gensysmusic.online.spotify.models.MusicElements
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SpotifyViewModel(private val spotifyRepository: SpotifyRepository): ViewModel() {
    val albumLiveData = MutableLiveData<List<Album>>()
    val musicLiveData = MutableLiveData<List<Music>>()

    fun getAlbums(){
        spotifyRepository.getAlbums(getAlbumCallback())
    }

    fun getAlbumMusic(id:String){
        spotifyRepository.getAlbumMusic(id, getAlbumMusicCallback())
    }

    private fun getAlbumCallback() = object : Callback<AlbumElements> {
        override fun onResponse(call: Call<AlbumElements>, response: Response<AlbumElements>) {
            albumLiveData.postValue(response.body()?.albums?.items)
        }

        override fun onFailure(call: Call<AlbumElements>, t: Throwable) {
            //failed
        }
    }

    private fun getAlbumMusicCallback() = object : Callback<MusicElements> {
        override fun onResponse(call: Call<MusicElements>, response: Response<MusicElements>) {
            musicLiveData.postValue(response.body()?.items)
        }

        override fun onFailure(call: Call<MusicElements>, t: Throwable) {
            //failed
        }
    }
}
