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

package com.lebogang.gensysmusic.online.spotify

import com.lebogang.gensysmusic.online.spotify.models.Authentication
import com.lebogang.gensysmusic.online.spotify.models.AlbumElements
import com.lebogang.gensysmusic.online.spotify.models.MusicElements
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SpotifyRepository(private val spotifyAccess: SpotifyAccess,
                        private val spotifyAuthentication: SpotifyAuthentication) {

    fun getAlbums(callback: Callback<AlbumElements>){
        spotifyAuthentication.authenticate(getCallbacksAlbums(callback))
    }

    fun getAlbumMusic(id:String,callback: Callback<MusicElements>){
        spotifyAuthentication.authenticate(getCallbacksMusic(id,callback))
    }

    private fun getCallbacksAlbums(callback: Callback<AlbumElements>) = object :Callback<Authentication>{
        override fun onResponse(call: Call<Authentication>, response: Response<Authentication>) {
            if (response.isSuccessful){
                val authentication = response.body()!!
                spotifyAccess.getAlbums(("Bearer " + authentication.accessToken))
                    .enqueue(callback)
            }
        }
        override fun onFailure(call: Call<Authentication>, t: Throwable){
            //failed
        }
    }

    private fun getCallbacksMusic(id:String, callback: Callback<MusicElements>) = object :Callback<Authentication>{
        override fun onResponse(call: Call<Authentication>, response: Response<Authentication>) {
            if (response.isSuccessful){
                val authentication = response.body()!!
                spotifyAccess.getAlbumMusic(("Bearer " + authentication.accessToken), id)
                    .enqueue(callback)
            }
        }
        override fun onFailure(call: Call<Authentication>, t: Throwable) {
            //failed
        }
    }
}
