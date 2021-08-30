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

import com.lebogang.gensysmusic.utils.models.ArtistAbstract
import kotlinx.parcelize.Parcelize

@Parcelize
data class Artist(
    val id:Long,
    val title:String,
    val albumCount:Int,
    val trackCount:Int,
    val pictureMedium:String,
    val pictureLarge:String,
    val link:String,
    val albumIds:List<Long>):ArtistAbstract() {

    override fun getItemId(): Any = id

    override fun getItemTitle(): String = title

    override fun getAudioCount(): String = "Songs: $trackCount"

    override fun getItemSize(): String = ""

    override fun getIsItemFavourite(): Boolean = false

    override fun getItemContent(): String = link

    override fun getItemArt(): String = pictureMedium

    override fun getItemDuration(): String = ""

}
