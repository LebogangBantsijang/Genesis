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
import com.lebogang.gensysmusic.utils.models.MusicAbstract
import kotlinx.parcelize.Parcelize

@Parcelize
class Music(
    @Expose
    val id:String,
    @Expose
    @SerializedName(value = "name")
    val title:String,
    @Expose
    val explicit:Boolean,
    @Expose
    @SerializedName(value = "preview_url")
    val preview:String,
    @Expose
    val artists:List<Artist>):MusicAbstract(){

    override fun getItemId(): Any = id

    override fun getItemTitle(): String = title

    override fun getItemAlbum(): String = "Explicit"

    override fun getItemArtist(): String {
        var names = ""
        artists.forEachIndexed { index, artist ->
            if (index == (artists.size -1)){
                names += artist.title
                return@forEachIndexed
            }
            names += artist.title + ", "
        }
        return names
    }

    override fun getItemDuration(): Long  = 0

    override fun getItemContent(): String = preview

    override fun getItemArt(): String  = artists.first().title

    override fun getItemDate(): Long = 0

    override fun getIsItemFavourite(): Boolean = false

}
