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

package com.lebogang.gensysmusic.online.spotify

import com.lebogang.gensysmusic.online.spotify.models.AlbumElements
import com.lebogang.gensysmusic.online.spotify.models.MusicElements
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface SpotifyAccess {

    @GET("v1/browse/new-releases")
    fun getAlbums(@Header("Authorization") authentication:String):Call<AlbumElements>

    @GET("v1/albums/{id}/tracks")
    fun getAlbumMusic(@Header("Authorization") authentication:String,
                      @Path("id")id:String):Call<MusicElements>
}
