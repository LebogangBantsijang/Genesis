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
import com.lebogang.gensysmusic.database.local.models.Genre
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreAccess {

    @Query("SELECT * FROM Genres ORDER BY title ASC")
    fun getGenres(): Flow<MutableList<Genre>>

    @Query("SELECT * FROM Genres LIMIT :limit")
    fun getGenres(limit:Int):Flow<MutableList<Genre>>

    @Query("SELECT musicId FROM Genre_Members WHERE genreId IN(:genreId)")
    fun getGenreMusic(genreId:Long):Flow<MutableList<Long>>

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = Genre::class)
    fun addGenre(genre: Genre)

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = Genre::class)
    fun addGenre(genre: List<Genre>)

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = Genre.Members::class)
    fun addGenreMusic(genreMembers: Genre.Members)

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = Genre.Members::class)
    fun addGenreMusic(genreMembers: List<Genre.Members>)

    @Delete(entity = Genre::class)
    fun deleteGenre(genre: Genre)

    @Delete(entity = Genre::class)
    fun deleteGenre(genre: List<Genre>)

    @Delete(entity = Genre.Members::class)
    fun deleteGenreMusic(genreMembers: Genre.Members)

    @Delete(entity = Genre.Members::class)
    fun deleteGenreMusic(genreMembers: List<Genre.Members>)

    @Query("DELETE FROM Genres")
    fun resetGenres()

    @Query("DELETE FROM Genre_Members")
    fun resetGenreMusic()
}
