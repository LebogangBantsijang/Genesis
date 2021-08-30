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
import com.lebogang.gensysmusic.database.local.models.Music
import kotlinx.coroutines.flow.Flow

@Dao
interface MusicAccess {

    @Query("SELECT * FROM Music ORDER BY title ASC" )
    fun getMusic():Flow<MutableList<Music>>

    @Query("SELECT * FROM Music WHERE id IN(:idArray)")
    fun getMusic(idArray:List<Long>):Flow<MutableList<Music>>

    @Query("SELECT * FROM Music WHERE id = :id")
    fun getMusic(id:Long):Music?

    @Query("SELECT * FROM Music WHERE albumId = :albumId ORDER BY title")
    fun getAlbumMusic(albumId:Long):Flow<MutableList<Music>>

    @Query("SELECT * FROM Music WHERE artistId = :artistId ORDER BY title")
    fun getArtistMusic(artistId:Long):Flow<MutableList<Music>>

    @Query("SELECT * FROM Music WHERE isFavourite = :isFavourite")
    fun getFavouriteMusic(isFavourite:Boolean = true):Flow<MutableList<Music>>

    @Query("SELECT * FROM Music WHERE title LIKE :query OR artist LIKE :query")
    fun searchMusic(query:String):MutableList<Music>

    @Query("SELECT id FROM Music")
    fun getMusicIdList():List<Long>

    @Query("SELECT COUNT(*) FROM Music")
    fun getMusicCount():Int

    @Delete
    fun deleteMusic(music: Music)

    @Delete
    fun deleteMusic(music:List<Music>)

    @Query("DELETE FROM Music")
    fun reset()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMusic(music: Music)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMusic(music: List<Music>)

}
