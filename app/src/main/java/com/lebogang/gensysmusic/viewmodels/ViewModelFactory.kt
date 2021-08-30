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

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lebogang.gensysmusic.GensysApp
import com.lebogang.gensysmusic.ui.local.viewmodel.MusicViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val application:Application): ViewModelProvider.Factory {
    private val gensysApp = application as GensysApp

    //new
    fun getMusicViewModel(): MusicViewModel = create(MusicViewModel::class.java)

    //old
    fun getStatisticsViewModel():StatisticsViewModel{
        return create(StatisticsViewModel::class.java)
    }

    fun getPlaylistViewModel():PlaylistViewModel{
        return create(PlaylistViewModel::class.java)
    }

    fun getAudioViewModel():AudioViewModel{
        return create(AudioViewModel::class.java)
    }

    fun getArtistViewModel():ArtistViewModel{
        return create(ArtistViewModel::class.java)
    }

    fun getAlbumViewModel():AlbumViewModel{
        return create(AlbumViewModel::class.java)
    }

    fun getDeezerViewModel():DeezerViewModel{
        return create(DeezerViewModel::class.java)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(StatisticsViewModel::class.java) ->
                StatisticsViewModel(gensysApp.statisticsRepo) as T
            modelClass.isAssignableFrom(PlaylistViewModel::class.java) ->
                PlaylistViewModel(gensysApp.playlistRepo) as T
            modelClass.isAssignableFrom(AudioViewModel::class.java) ->
                AudioViewModel(gensysApp.audioRepo) as T
            modelClass.isAssignableFrom(ArtistViewModel::class.java) ->
                ArtistViewModel(gensysApp.artistRepo) as T
            modelClass.isAssignableFrom(AlbumViewModel::class.java) ->
                AlbumViewModel(gensysApp.albumRepo) as T
            modelClass.isAssignableFrom(DeezerViewModel::class.java) ->
                DeezerViewModel(gensysApp.deezerRepo) as T
            //new
            modelClass.isAssignableFrom(MusicViewModel::class.java) ->
                MusicViewModel(gensysApp.musicRepository,gensysApp.favouriteRepository) as T
            else -> throw IllegalArgumentException()
        }
    }
}
