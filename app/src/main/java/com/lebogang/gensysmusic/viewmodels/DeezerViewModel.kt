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
import com.lebogang.gensysmusic.network.DeezerRepo
import com.lebogang.gensysmusic.network.models.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeezerViewModel(private val deezerRepo: DeezerRepo) : ViewModel() {
    val liveDataAudioChart:MutableLiveData<List<TrackDeezer>> = MutableLiveData()
    val liveDataAlbumChart:MutableLiveData<List<AlbumDeezer>> = MutableLiveData()
    val liveDataArtistChart:MutableLiveData<List<ArtistDeezer>> = MutableLiveData()

    fun getChartAudio() = viewModelScope.launch{
        deezerRepo.getChatAudio(getAudioCallback())
    }

    fun getChartAlbum() = viewModelScope.launch {
        deezerRepo.getChartAlbum(getAlbumCallback())
    }

    fun getChartArtist() = viewModelScope.launch {
        deezerRepo.getChartArtist(getArtistCallback())
    }

    fun getTrackSearchResults(query:String) = viewModelScope.launch{
        deezerRepo.getSearchAudioResults(query,getAudioCallback())
    }

    fun getAlbumSearchResults(query:String) = viewModelScope.launch{
        deezerRepo.getSearchAlbumResults(query,getAlbumCallback())
    }

    fun getArtistSearchResults(query:String) = viewModelScope.launch{
        deezerRepo.getSearchArtistResults(query, getArtistCallback())
    }

    fun getAlbumTracks(id:Int) = viewModelScope.launch {
        deezerRepo.getAlbumTracks(id, getAudioCallback())
    }

    fun getArtistTracks(id:Int) = viewModelScope.launch {
        deezerRepo.getArtistTracks(id, getAudioCallback())
    }

    private fun getAudioCallback():Callback<TrackDataModel>{
        return object:Callback<TrackDataModel>{
            override fun onResponse(call: Call<TrackDataModel>, response: Response<TrackDataModel>) {
                response.body()?.let {
                    liveDataAudioChart.postValue(it.data)
                }
            }
            override fun onFailure(call: Call<TrackDataModel>, t: Throwable) {
                //not finished
            }
        }
    }

    private fun getAlbumCallback():Callback<AlbumDataModel>{
        return object:Callback<AlbumDataModel>{
            override fun onResponse(call: Call<AlbumDataModel>, response: Response<AlbumDataModel>) {
                response.body()?.let {
                    liveDataAlbumChart.postValue(it.data)
                }
            }
            override fun onFailure(call: Call<AlbumDataModel>, t: Throwable) {
                //not finished
            }
        }
    }

    private fun getArtistCallback():Callback<ArtistDataModel>{
        return object:Callback<ArtistDataModel>{
            override fun onResponse(call: Call<ArtistDataModel>, response: Response<ArtistDataModel>) {
                response.body()?.let {
                    liveDataArtistChart.postValue(it.data)
                }
            }
            override fun onFailure(call: Call<ArtistDataModel>, t: Throwable) {
                //not finished
            }
        }
    }


}
