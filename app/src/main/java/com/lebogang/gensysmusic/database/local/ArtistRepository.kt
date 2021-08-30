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
import com.lebogang.gensysmusic.database.local.models.Artist
import kotlinx.coroutines.flow.Flow

class ArtistRepository(private val artistAccess: ArtistAccess) {

    fun getArtists() = artistAccess.getArtists()

    fun getArtistsPreview() =artistAccess.getArtists(6)

    fun getArtists(nameArray:Array<String>) = artistAccess.getArtists(nameArray)

    fun getFavouriteArtists() = artistAccess.getFavouriteArtists(true)

    fun getArtistsCount(): Flow<Int> = artistAccess.getArtistsCount()

    fun getArtists(title:String) = artistAccess.getArtists(title)

    fun getArtists(id:Long) = artistAccess.getArtists(id)

    fun getArtistsPreview(title:String) = artistAccess.getArtistsPreview(title,6)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteArtist(artist: Artist){
        artistAccess.deleteArtist(artist)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteArtist(artist:List<Artist>){
        artistAccess.deleteArtist(artist)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun reset(){
        artistAccess.reset()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addArtist(artist: Artist){
        artistAccess.addArtist(artist)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addArtist(artist: List<Artist>){
        artistAccess.addArtist(artist)
    }
}
