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

package com.lebogang.gensysmusic.online.deezer.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.lebogang.gensysmusic.utils.models.MusicAbstract
import kotlinx.parcelize.Parcelize

@Parcelize
class Music (
    @Expose
    val id:Int,
    @Expose
    val title:String,
    @Expose
    val duration:Int,
    @Expose
    @SerializedName(value = "explicit_lyrics")
    val hasExplicitLyrics:Boolean,
    @Expose
    var artist: Artist?,
    @Expose
    var album: Album?,
    @Expose
    @SerializedName(value = "preview")
    val link:String):MusicAbstract(){

    class Members(@Expose val data:List<Music>)

    override fun getItemId(): Any = id

    override fun getItemTitle(): String = title

    override fun getItemAlbum(): String = album?.title ?: "Explicit"

    override fun getItemArtist(): String = artist?.title ?: "Unknown"

    override fun getItemDuration(): Long = duration.toLong()

    override fun getItemContent(): String = link

    override fun getItemArt(): String = album?.coverMedium ?: artist?.coverMedium ?: ""

    override fun getItemDate(): Long = 0

    override fun getIsItemFavourite(): Boolean = false

}
