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

package com.lebogang.gensysmusic.online.stream.models

import com.lebogang.gensysmusic.utils.models.AlbumAbstract
import kotlinx.parcelize.Parcelize

@Parcelize
data class Album(
    val id:Long,
    val title:String,
    val artist:String,
    val album:String,
    val coverMedium:String,
    val coverLarge:String,
    val releaseDate:Long,
    val link:String,
    val explicit:Boolean,
    val trackCount:Int,
    val rating:Int,
    val artistIds:List<Long>,
    val genre:List<String>):AlbumAbstract(){

    override fun getItemId(): Any = id

    override fun getItemTitle(): String = title

    override fun getItemArtist(): String = artist

    override fun getItemArt(): String = coverMedium

    override fun getItemDuration(): String  = ""

    override fun getItemSize(): String = "Songs: $trackCount"

    override fun getIsItemFavourite(): Boolean = false

    override fun getItemContent(): String = link

    override fun getIsItemExplicit(): Boolean = explicit
}
