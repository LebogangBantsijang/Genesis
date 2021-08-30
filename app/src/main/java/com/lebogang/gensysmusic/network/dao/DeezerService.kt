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

package com.lebogang.gensysmusic.network.dao

import com.lebogang.gensysmusic.network.models.AlbumDataModel
import com.lebogang.gensysmusic.network.models.ArtistDataModel
import com.lebogang.gensysmusic.network.models.TrackDataModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DeezerService {
    //https://api.deezer.com/

    @GET("chart/0/tracks?index=0&limit=100")
    fun getChartAudio():Call<TrackDataModel>

    @GET("chart/0/albums?index=0&limit=100")
    fun getChartAlbums():Call<AlbumDataModel>

    @GET("chart/0/artists?index=0&limit=100")
    fun getChartArtist():Call<ArtistDataModel>

    @GET("search/track?&index=0&limit=100")
    fun searchAudioItems(@Query("q") query:String):Call<TrackDataModel>

    @GET("search/album?&index=0&limit=100")
    fun searchAlbumItems(@Query("q") query:String):Call<AlbumDataModel>

    @GET("search/artist?&index=0&limit=100")
    fun searchArtistItems(@Query("q") query: String):Call<ArtistDataModel>

    @GET("album/{id}/tracks")
    fun getAlbumTracks(@Path("id")id:Int):Call<TrackDataModel>

    @GET("artist/{id}/top?limit=50")
    fun getArtistTracts(@Path("id")id:Int):Call<TrackDataModel>
}
