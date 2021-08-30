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

package com.lebogang.gensysmusic.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PlaylistDeezer(
        @Expose
        val id:Int,
        @Expose
        val title:String,
        @Expose
        @SerializedName(value = "nb_tracks")
        val numberOfTracks:Int,
        @Expose
        @SerializedName(value = "public")
        val isPlaylistPublic:Boolean,
        @Expose
        val position:Int,
        @Expose
        @SerializedName(value = "picture_small")
        val coverSmall:String,
        @Expose
        @SerializedName(value = "picture_medium")
        val coverMedium:String
)
