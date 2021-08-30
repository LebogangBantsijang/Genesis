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

package com.lebogang.gensysmusic.room

import androidx.annotation.WorkerThread
import com.lebogang.gensysmusic.room.dao.PlaylistAudioBridgeDao
import com.lebogang.gensysmusic.room.dao.PlaylistDao
import com.lebogang.gensysmusic.room.models.Playlist
import com.lebogang.gensysmusic.room.models.PlaylistBridgeTable
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

class PlaylistRepo(private val playlistDao: PlaylistDao,
                   private val playlistAudioBridgeDao: PlaylistAudioBridgeDao) {

    fun getPlaylists(): Flow<List<Playlist>> = playlistDao.getPlaylist()

    fun getPlaylist(id:Long): Playlist {
        return playlistDao.getPlaylist(id)
    }

    suspend fun getPlaylistAudioIds(playlistId:Long):List<Long> = coroutineScope {
        val deffered = async(Dispatchers.IO) {
            playlistAudioBridgeDao.getAudioIds(playlistId)
        }
        deffered.await()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertPlaylist(playlist: Playlist){
        playlistDao.insert(playlist)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAudioPlaylist(id:Long, audioId:Long){
        playlistAudioBridgeDao.insertAudio(PlaylistBridgeTable(0, id, audioId))
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deletePlaylist(playlist: Playlist){
        playlistAudioBridgeDao.delete(playlist.id)
        playlistDao.delete(playlist)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun clearData(){
        playlistDao.clearData()
        playlistAudioBridgeDao.clearData()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun clearAudioData(playlistId: Long){
        playlistAudioBridgeDao.delete(playlistId)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deletePlaylistAudio(playlistId:Long, audioId:Long){
        playlistAudioBridgeDao.delete(playlistId, audioId)
    }

}
