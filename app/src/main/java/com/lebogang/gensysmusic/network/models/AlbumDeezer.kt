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

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlbumDeezer(
        @Expose
        val id:Int,
        @Expose
        val title:String,
        @Expose
        val artist:ArtistDeezer,
        @Expose
        val type:String,
        @Expose
        val position:Int,
        @Expose
        @SerializedName(value = "explicit_lyrics")
        val hasExplicitLyrics:Boolean,
        @Expose
        @SerializedName(value = "cover_medium")
        val coverMedium:String,
        @Expose
        @SerializedName(value = "cover_small")
        val coverSmall:String,
        @Expose
        @SerializedName(value = "tracklist")
        val trackListUrl:String
): Parcelable
