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

package com.lebogang.gensysmusic.database.local

import androidx.annotation.WorkerThread
import com.lebogang.gensysmusic.database.local.models.Album

class AlbumRepository (private val albumAccess: AlbumAccess){

    fun getAlbums() = albumAccess.getAlbums()

    fun getAlbumsPreview() = albumAccess.getAlbums(6)

    fun getAlbums(nameArray:Array<String>) = albumAccess.getAlbums(nameArray)

    fun getFavouriteAlbums() = albumAccess.getFavouriteAlbums(true)

    fun getAlbums(id:Long) = albumAccess.getAlbums(id)

    fun getAlbums(title:String) = albumAccess.getAlbums(title)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAlbum(album: Album){
        albumAccess.deleteAlbum(album)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAlbum(album:List<Album>){
        albumAccess.deleteAlbum(album)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun reset(){
        albumAccess.reset()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addAlbum(album: Album){
        albumAccess.addAlbum(album)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addAlbum(album: List<Album>){
        albumAccess.addAlbum(album)
    }

}
