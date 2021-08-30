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
import com.lebogang.gensysmusic.database.local.models.Artist
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtistAccess {

    @Query("SELECT * FROM Artists ORDER BY title ASC")
    fun getArtists():Flow<MutableList<Artist>>

    @Query("SELECT * FROM Artists LIMIT :limit ")
    fun getArtists(limit:Int):Flow<MutableList<Artist>>

    @Query("SELECT * FROM Artists WHERE title IN(:nameArray) LIMIT :limit")
    fun getArtists(nameArray:Array<String>,limit: Int = 15):Flow<MutableList<Artist>>

    @Query("SELECT * FROM Artists WHERE isFavourite = :isFavourite")
    fun getFavouriteArtists(isFavourite:Boolean = true):Flow<MutableList<Artist>>

    @Query("SELECT COUNT(*) FROM Artists")
    fun getArtistsCount():Flow<Int>

    @Query("SELECT * FROM Artists WHERE title = :title")
    fun getArtists(title:String):Flow<MutableList<Artist>>

    @Query("SELECT * FROM Artists WHERE id = :id")
    fun getArtists(id:Long):Flow<Artist>

    @Query("SELECT * FROM Artists WHERE title = :title LIMIT :limit")
    fun getArtistsPreview(title:String,limit: Int):Flow<MutableList<Artist>>

    @Delete
    fun deleteArtist(artist: Artist)

    @Delete
    fun deleteArtist(artist:List<Artist>)

    @Query("DELETE FROM Artists")
    fun reset()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addArtist(artist: Artist)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addArtist(artist: List<Artist>)
    
}
