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

package com.lebogang.gensysmusic.online.deezer

import com.lebogang.gensysmusic.online.deezer.models.Album
import com.lebogang.gensysmusic.online.deezer.models.Artist
import com.lebogang.gensysmusic.online.deezer.models.Music
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DeezerAccess {

    @GET("chart/0/tracks?index=0&limit=100")
    fun getMusic(): Call<Music.Members>

    @GET("chart/0/albums?index=0&limit=100")
    fun getAlbums():Call<Album.Members>

    @GET("chart/0/artists?index=0&limit=100")
    fun getArtists():Call<Artist.Members>

    @GET("album/{id}/tracks")
    fun getAlbumMusic(@Path("id")id:Int):Call<Music.Members>

    @GET("artist/{id}/top?limit=50")
    fun getArtistMusic(@Path("id")id:Int):Call<Music.Members>

}
