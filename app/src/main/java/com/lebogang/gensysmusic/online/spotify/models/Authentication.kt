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

package com.lebogang.gensysmusic.online.spotify.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Authentication(
    @Expose
    @SerializedName(value = "access_token")
    val accessToken:String,
    @Expose
    @SerializedName(value = "token_type")
    val tokenType:String,
    @Expose
    @SerializedName(value = "expires_in")
    val expiresIn:Long)
