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

import com.lebogang.gensysmusic.online.spotify.models.Authentication
import retrofit2.Call
import retrofit2.http.*

interface SpotifyAuthenticationAccess {

    @FormUrlEncoded
    @POST("api/token")
    fun getAccessToken(@Header("Authorization") auth:String,
                       @Field("grant_type") requestBody: String, ): Call<Authentication>

}
