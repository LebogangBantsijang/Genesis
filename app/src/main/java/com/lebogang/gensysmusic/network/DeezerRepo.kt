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

package com.lebogang.gensysmusic.network

import com.lebogang.gensysmusic.network.dao.DeezerService
import com.lebogang.gensysmusic.network.models.*
import retrofit2.Callback

class DeezerRepo (private val deezerService: DeezerService) {

    fun getChatAudio(callback: Callback<TrackDataModel>){
        deezerService.getChartAudio().enqueue(callback)
    }

    fun getChartAlbum(callback: Callback<AlbumDataModel>){
        deezerService.getChartAlbums().enqueue(callback)
    }

    fun getChartArtist(callback: Callback<ArtistDataModel>){
        deezerService.getChartArtist().enqueue(callback)
    }

    fun getSearchAudioResults(query:String, callback: Callback<TrackDataModel>){
        deezerService.searchAudioItems(query).enqueue(callback)
    }

    fun getSearchAlbumResults(query:String, callback: Callback<AlbumDataModel>){
        deezerService.searchAlbumItems(query).enqueue(callback)
    }

    fun getSearchArtistResults(query:String, callback: Callback<ArtistDataModel>){
        deezerService.searchArtistItems(query).enqueue(callback)
    }

    fun getAlbumTracks(id:Int, callback:Callback<TrackDataModel>){
        deezerService.getAlbumTracks(id).enqueue(callback)
    }

    fun getArtistTracks(id:Int, callback: Callback<TrackDataModel>){
        deezerService.getArtistTracts(id).enqueue(callback)
    }
}
