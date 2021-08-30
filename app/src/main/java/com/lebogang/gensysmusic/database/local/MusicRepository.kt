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
import com.lebogang.gensysmusic.database.local.models.Music

class MusicRepository(private val musicAccess: MusicAccess){

    fun getMusic() = musicAccess.getMusic()

    fun getMusic(idArray:List<Long>) = musicAccess.getMusic(idArray)

    fun getAlbumMusic(albumId:Long) = musicAccess.getAlbumMusic(albumId)

    fun getArtistMusic(artistId:Long) = musicAccess.getArtistMusic(artistId)

    fun getFavouriteMusic() = musicAccess.getFavouriteMusic()

    fun searchItems(query:String) = musicAccess.searchMusic(query)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getMusicIdList() = musicAccess.getMusicIdList()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getMusicCount():Int = musicAccess.getMusicCount()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteMusic(music: Music){
        musicAccess.deleteMusic(music)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteMusic(music:List<Music>){
        musicAccess.deleteMusic(music)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun reset(){
        musicAccess.reset()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addMusic(music: Music){
        musicAccess.addMusic(music)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addMusic(music: List<Music>){
        musicAccess.addMusic(music)
    }

}
