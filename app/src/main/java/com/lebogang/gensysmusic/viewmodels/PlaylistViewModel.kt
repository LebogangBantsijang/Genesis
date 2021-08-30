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

package com.lebogang.gensysmusic.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.asLiveData
import com.lebogang.gensysmusic.room.PlaylistRepo
import com.lebogang.gensysmusic.room.models.Playlist
import kotlinx.coroutines.launch

class PlaylistViewModel(private val playlistRepo: PlaylistRepo): ViewModel() {
    val liveData: LiveData<List<Playlist>> = playlistRepo.getPlaylists().asLiveData()
    val liveDataAudioIds:MutableLiveData<List<Long>> = MutableLiveData()

    fun getPlaylists(id:Long):Playlist{
        return playlistRepo.getPlaylist(id)
    }

    fun getPlaylistAudio(playlistId: Long) = viewModelScope.launch{
        liveDataAudioIds.postValue(playlistRepo.getPlaylistAudioIds(playlistId))
    }

    fun insertPlaylist(playlist: Playlist) = viewModelScope.launch {
        playlistRepo.insertPlaylist(playlist)
    }

    fun insertPlaylistAudio(playlistId: Long, audioId:Long) = viewModelScope.launch{
        playlistRepo.insertAudioPlaylist(playlistId, audioId)
    }

    fun deletePlaylist(playlist: Playlist) = viewModelScope.launch {
        playlistRepo.deletePlaylist(playlist)
    }

    fun clearData() = viewModelScope.launch {
        playlistRepo.clearData()
    }

    fun clearAudioData(playlistId: Long) = viewModelScope.launch {
        playlistRepo.clearAudioData(playlistId)
        liveDataAudioIds.postValue(playlistRepo.getPlaylistAudioIds(playlistId))
    }

    fun deletePlaylistAudio(playlistId:Long, audioId:Long) = viewModelScope.launch {
        playlistRepo.deletePlaylistAudio(playlistId,audioId)
        liveDataAudioIds.postValue(playlistRepo.getPlaylistAudioIds(playlistId))
    }

}
