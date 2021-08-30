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

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.lebogang.gensysmusic.database.local.models.Album
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumAccess {

    @Query("SELECT * FROM Albums ORDER BY title ASC")
    fun getAlbums():Flow<MutableList<Album>>

    @Query("SELECT * FROM Albums ORDER BY date DESC LIMIT :limit")
    fun getAlbums(limit:Int = 5):Flow<MutableList<Album>>

    @Query("SELECT * FROM Albums WHERE artist = :artist")
    fun getArtistAlbums(artist:String):Flow<MutableList<Album>>

    @Query("SELECT * FROM Albums WHERE title IN(:nameArray) LIMIT :limit")
    fun getAlbums(nameArray:Array<String>,limit: Int = 15):Flow<MutableList<Album>>

    @Query("SELECT * FROM Albums WHERE isFavourite = :isFavourite")
    fun getFavouriteAlbums(isFavourite:Boolean = true):Flow<MutableList<Album>>

    @Query("SELECT COUNT(*) FROM Albums")
    fun getAlbumsCount():Flow<Int>

    @Query("SELECT * FROM Albums WHERE id =:id")
    fun getAlbums(id:Long):Flow<Album>

    @Query("SELECT * FROM Albums WHERE title =:title")
    fun getAlbums(title:String):Flow<MutableList<Album>>

    @Delete
    fun deleteAlbum(album: Album)

    @Delete
    fun deleteAlbum(album:List<Album>)

    @Query("DELETE FROM Albums")
    fun reset()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAlbum(album: Album)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAlbum(album: List<Album>)
    
}
