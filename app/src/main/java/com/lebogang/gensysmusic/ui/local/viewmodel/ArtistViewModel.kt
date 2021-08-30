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

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.lebogang.gensysmusic.GensysApp
import com.lebogang.gensysmusic.database.local.ArtistRepository
import com.lebogang.gensysmusic.database.local.FavouriteRepository
import com.lebogang.gensysmusic.database.local.models.Artist
import com.lebogang.gensysmusic.database.local.models.Favourite
import com.lebogang.gensysmusic.database.local.scan.LocalContent
import com.lebogang.gensysmusic.database.local.scan.MediaStoreObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArtistViewModel(private val artistRepository: ArtistRepository,
                      private val favouriteRepository: FavouriteRepository):ViewModel() {
    lateinit var localContent: LocalContent

    private val mediaStoreObserver = object : MediaStoreObserver(){
        override fun onContentChanged() {
            localContent.onUpdate()
        }
    }

    fun getArtistsPreview() = artistRepository.getArtistsPreview().asLiveData()

    fun getArtists() = artistRepository.getArtists().asLiveData()

    fun getFavourites() = artistRepository.getFavouriteArtists().asLiveData()

    fun getArtistsPreview(title:String) = artistRepository.getArtistsPreview(title).asLiveData()

    fun getArtist(id: Long):LiveData<Artist> = artistRepository.getArtists(id).asLiveData()

    fun getArtists(nameArray: Array<String>) = artistRepository.getArtists(nameArray).asLiveData()

    fun getArtists(title:String) = artistRepository.getArtists(title).asLiveData()

    fun addArtist(artist: Artist) = viewModelScope.launch(Dispatchers.IO) {
        if (artist.isFavourite)
            favouriteRepository.addMediaId(Favourite(artist.id))
        else
            favouriteRepository.removeMediaId(Favourite(artist.id))
        artistRepository.addArtist(artist)
    }

    fun registerObserver(application: GensysApp){
        localContent = application.localContent
        application.contentResolver.registerContentObserver(LocalContent.ResolverConstants.ARTIST_URI
            ,true,mediaStoreObserver)
    }

    fun unregisterObserver(application: GensysApp){
        application.contentResolver.unregisterContentObserver(mediaStoreObserver)
    }
}
