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

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.lebogang.gensysmusic.utils.models.AlbumAbstract
import kotlinx.parcelize.Parcelize

@Parcelize
class Album(
    @Expose
    val id:String,
    @Expose
    @SerializedName(value = "name")
    val title:String,
    @Expose
    @SerializedName(value = "release_date")
    val date:String,
    @Expose
    val images:List<Cover>,
    @Expose
    val artists:List<Artist>,
    @Expose
    @SerializedName(value = "total_tracks")
    val numberOfTracks:Int):AlbumAbstract(){

        @Parcelize
        class Cover(@Expose val url:String):Parcelable

    override fun getItemId(): Any = id

    override fun getItemTitle(): String = title

    override fun getItemArtist(): String{
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

    override fun getItemArt(): String = images.first().url

    override fun getItemDuration(): String = ""

    override fun getItemSize(): String = ""

    override fun getIsItemFavourite(): Boolean = false

    override fun getItemContent(): String = ""

    override fun getIsItemExplicit(): Boolean = false

}
