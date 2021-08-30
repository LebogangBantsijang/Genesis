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
import com.lebogang.gensysmusic.database.local.models.Genre

class GenreRepository(private val genreAccess: GenreAccess) {

    fun getGenres() = genreAccess.getGenres()

    fun getGenresPreview() = genreAccess.getGenres(6)

    fun getGenreMusicIds(genreId:Long) = genreAccess.getGenreMusic(genreId)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addGenre(genre: Genre){
        genreAccess.addGenre(genre)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addGenre(genre: List<Genre>){
        genreAccess.addGenre(genre)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addGenreMusic(genreMembers: Genre.Members){
        genreAccess.addGenreMusic(genreMembers)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addGenreMusic(genreMembers: List<Genre.Members>){
        genreAccess.addGenreMusic(genreMembers)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteGenre(genre: Genre){
        genreAccess.deleteGenre(genre)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteGenre(genre: List<Genre>){
        genreAccess.deleteGenre(genre)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteGenreMusic(genreMembers: Genre.Members){
        genreAccess.deleteGenreMusic(genreMembers)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteGenreMusic(genreMembers: List<Genre.Members>){
        genreAccess.deleteGenreMusic(genreMembers)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun reset(){
        genreAccess.resetGenres()
        genreAccess.resetGenreMusic()
    }

}
