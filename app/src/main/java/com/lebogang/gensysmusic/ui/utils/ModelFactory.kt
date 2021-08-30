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

package com.lebogang.gensysmusic.ui.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lebogang.gensysmusic.GensysApp
import com.lebogang.gensysmusic.ui.charts.viewmodels.DeezerViewModel
import com.lebogang.gensysmusic.ui.charts.viewmodels.SpotifyViewModel
import com.lebogang.gensysmusic.ui.history.viewmodels.HistoryViewModel
import com.lebogang.gensysmusic.ui.local.viewmodel.*
import com.lebogang.gensysmusic.ui.user.viewmodels.UserViewModel

class ModelFactory(private val application: GensysApp) : ViewModelProvider.Factory {

    fun getArtistViewModel(): ArtistViewModel = create(ArtistViewModel::class.java)

    fun getAlbumViewModel(): AlbumViewModel = create(AlbumViewModel::class.java)

    fun getMusicViewModel(): MusicViewModel = create(MusicViewModel::class.java)

    fun getGenreViewModel(): GenreViewModel = create(GenreViewModel::class.java)

    fun getUserViewModel(): UserViewModel = create(UserViewModel::class.java)

    fun getPlaylistViewModel(): PlaylistViewModel = create(PlaylistViewModel::class.java)

    fun getDeezerViewModel(): DeezerViewModel = create(DeezerViewModel::class.java)

    fun getSpotifyViewModel(): SpotifyViewModel = create(SpotifyViewModel::class.java)

    fun getHistoryViewModel(): HistoryViewModel = create(HistoryViewModel::class.java)

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(HistoryViewModel::class.java)->
                HistoryViewModel(application.historyRepository) as T
            modelClass.isAssignableFrom(SpotifyViewModel::class.java)->
                SpotifyViewModel(application.spotifyRepository) as T
            modelClass.isAssignableFrom(DeezerViewModel::class.java)->
                DeezerViewModel(application.deezerRepository) as T
            modelClass.isAssignableFrom(PlaylistViewModel::class.java)->
                PlaylistViewModel(application.playlistRepository) as T
            modelClass.isAssignableFrom(UserViewModel::class.java)->
                UserViewModel(application.userRepository) as T
            modelClass.isAssignableFrom(GenreViewModel::class.java)->
                GenreViewModel(application.genreRepository) as T
            modelClass.isAssignableFrom(ArtistViewModel::class.java)->
                ArtistViewModel(application.artistRepository,application.favouriteRepository) as T
            modelClass.isAssignableFrom(AlbumViewModel::class.java)->
                AlbumViewModel(application.albumRepository,application.favouriteRepository) as T
            modelClass.isAssignableFrom(MusicViewModel::class.java)->
                MusicViewModel(application.musicRepository,application.favouriteRepository) as T
            else -> throw IllegalArgumentException()
        }
    }
}
