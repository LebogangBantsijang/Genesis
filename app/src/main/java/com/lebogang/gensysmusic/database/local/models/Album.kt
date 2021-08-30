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

package com.lebogang.gensysmusic.database.local.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.lebogang.gensysmusic.utils.models.AlbumAbstract
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Albums")
data class Album (
    @PrimaryKey
    val id:Long,
    val title:String,
    val artist:String,
    var trackCount:Int,
    var duration:String,
    var size:String,
    val date:Long,
    var isFavourite:Boolean,
    val contentUri:String,
    val artUri:String):AlbumAbstract(){

    @Ignore
    override fun getItemId(): Any = id

    @Ignore
    override fun getItemTitle(): String = title

    @Ignore
    override fun getItemArtist(): String = artist

    @Ignore
    override fun getItemArt(): String = artUri

    @Ignore
    override fun getItemDuration(): String = duration

    @Ignore
    override fun getItemSize(): String = size

    @Ignore
    override fun getIsItemFavourite(): Boolean = isFavourite

    @Ignore
    override fun getItemContent(): String = contentUri

    @Ignore
    override fun getIsItemExplicit(): Boolean = false

}
