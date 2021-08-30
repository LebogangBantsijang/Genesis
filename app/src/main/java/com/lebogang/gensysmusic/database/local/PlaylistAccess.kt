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

import androidx.room.*
import com.lebogang.gensysmusic.database.local.models.Playlist
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistAccess {

    @Query("SELECT * FROM Playlist")
    fun getPlaylist():Flow<MutableList<Playlist>>

    @Query("SELECT * FROM Playlist LIMIT :limit")
    fun getPlaylistPreview(limit:Int):Flow<MutableList<Playlist>>

    @Query("SELECT * FROM Playlist_Members WHERE playlistId = :playlistId")
    fun getPlaylistMusicIds(playlistId:Long):Flow<MutableList<Playlist.Members>>

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = Playlist::class)
    fun addPlaylist(playlist: Playlist)

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = Playlist.Members::class)
    fun addPlaylistMusicId(playlistMembers: Playlist.Members)

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = Playlist.Members::class)
    fun addPlaylistMusicId(playlistMembers: List<Playlist.Members>)

    @Delete(entity = Playlist::class)
    fun deletePlaylist(playlist: Playlist)

    @Query("DELETE FROM Playlist_Members WHERE playlistId = :playlistId AND musicId = :musicId")
    fun deletePlaylistMusicId(playlistId: Long,musicId:Long)

    @Query("DELETE FROM Playlist")
    fun reset()

    @Query("DELETE FROM Playlist_Members")
    fun resetMembers()

}
