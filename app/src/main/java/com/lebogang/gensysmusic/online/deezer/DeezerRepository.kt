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

package com.lebogang.gensysmusic.online.deezer

import com.lebogang.gensysmusic.online.deezer.models.Album
import com.lebogang.gensysmusic.online.deezer.models.Artist
import com.lebogang.gensysmusic.online.deezer.models.Music
import retrofit2.Callback

class DeezerRepository(private val deezerAccess: DeezerAccess) {

    fun getMusic(callback: Callback<Music.Members>) {
        deezerAccess.getMusic().enqueue(callback)
    }

    fun getAlbums(callback: Callback<Album.Members>){
        deezerAccess.getAlbums().enqueue(callback)
    }

    fun getArtists(callback: Callback<Artist.Members>){
        deezerAccess.getArtists().enqueue(callback)
    }

    fun getAlbumMusic(id:Int,callback: Callback<Music.Members>) {
        deezerAccess.getAlbumMusic(id).enqueue(callback)
    }

    fun getArtistMusic(id:Int,callback: Callback<Music.Members>) {
        deezerAccess.getArtistMusic(id).enqueue(callback)
    }
}
