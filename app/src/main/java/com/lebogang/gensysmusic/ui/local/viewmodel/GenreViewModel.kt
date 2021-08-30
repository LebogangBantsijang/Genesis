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
import com.lebogang.gensysmusic.GensysApp
import com.lebogang.gensysmusic.database.local.GenreRepository
import com.lebogang.gensysmusic.database.local.scan.LocalContent
import com.lebogang.gensysmusic.database.local.scan.MediaStoreObserver

class GenreViewModel(private val genreRepository: GenreRepository) : ViewModel(){
    lateinit var localContent: LocalContent

    private val mediaStoreObserver = object : MediaStoreObserver(){
        override fun onContentChanged() {
            localContent.onUpdate()
        }
    }

    fun getGenrePreview() = genreRepository.getGenresPreview().asLiveData()

    fun getGenres() = genreRepository.getGenres().asLiveData()

    fun getGenreMusicIds(genreId:Long):LiveData<MutableList<Long>> =
        genreRepository.getGenreMusicIds(genreId).asLiveData()

    fun registerObserver(application: GensysApp){
        localContent = application.localContent
        application.contentResolver.registerContentObserver(LocalContent.ResolverConstants.GENRE_URI
            ,true,mediaStoreObserver)
    }

    fun unregisterObserver(application: GensysApp){
        application.contentResolver.unregisterContentObserver(mediaStoreObserver)
    }

}
