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

package com.lebogang.gensysmusic.ui.local.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.lebogang.gensysmusic.database.local.PlaylistRepository
import com.lebogang.gensysmusic.database.local.models.Playlist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaylistViewModel(private val playlistRepository: PlaylistRepository):ViewModel() {

    fun getFavourites() = playlistRepository.getPlaylistPreview().asLiveData()

    fun getPlaylists() = playlistRepository.getPlaylist().asLiveData()

    fun getPlaylistMusicIds(playlistId:Long) = playlistRepository.getPlaylistMusic(playlistId).asLiveData()

    fun addPlaylist(playlist: Playlist) = viewModelScope.launch(Dispatchers.IO) {
        playlistRepository.addPlaylist(playlist)
    }

    fun addPlaylistMembers(members: List<Playlist.Members>) = viewModelScope.launch(Dispatchers.IO) {
        playlistRepository.addPlaylistMusicId(members)
    }

    fun addPlaylistMembers(members: Playlist.Members) = viewModelScope.launch(Dispatchers.IO) {
        playlistRepository.addPlaylistMusicId(members)
    }

    fun reset() = viewModelScope.launch(Dispatchers.IO) {
        playlistRepository.reset()
    }

    fun delete(playlist: Playlist) = viewModelScope.launch(Dispatchers.IO) {
        playlistRepository.deletePlaylist(playlist)
    }

    fun deletePlaylistMusic(playlistId: Long,musicId:Long) = viewModelScope.launch(Dispatchers.IO) {
        playlistRepository.deletePlaylistMusicId(playlistId,musicId)
    }
}
