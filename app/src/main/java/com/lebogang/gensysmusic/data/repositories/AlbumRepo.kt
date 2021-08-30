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

package com.lebogang.gensysmusic.data.repositories

import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import com.lebogang.gensysmusic.data.models.Album
import com.lebogang.gensysmusic.data.repositories.local.LocalAlbum
import com.lebogang.gensysmusic.viewmodels.utils.OnContentChanged
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class AlbumRepo(val context: Context) {
    private val localAlbums = LocalAlbum(context)
    private val contentObserver = getContentObserver()
    private var onContentChanged: OnContentChanged? = null

    fun registerObserver(onContentChanged: OnContentChanged){
        localAlbums.contentResolver.registerContentObserver(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI
                , true, contentObserver)
        this.onContentChanged = onContentChanged
    }

    fun unregisterObserver(){
        localAlbums.contentResolver.unregisterContentObserver(contentObserver)
    }

    suspend fun getAlbums():MutableList<Album> = coroutineScope {
        val deffer = async(Dispatchers.IO) {
            localAlbums.getAlbums()
        }
        deffer.await()
    }

    fun getAlbums(albumName:String):Album?{
        return localAlbums.getAlbums(albumName)
    }

    private fun getContentObserver(): ContentObserver {
        return object : ContentObserver(Handler(Looper.getMainLooper())){
            override fun deliverSelfNotifications(): Boolean {
                return true
            }

            override fun onChange(selfChange: Boolean) {
                //super.onChange(selfChange)
                onContentChanged?.onMediaChanged()
            }

            override fun onChange(selfChange: Boolean, uri: Uri?) {
                //super.onChange(selfChange, uri)
                onContentChanged?.onMediaChanged()
            }

            override fun onChange(selfChange: Boolean, uri: Uri?, flags: Int) {
                //super.onChange(selfChange, uri, flags)
                onContentChanged?.onMediaChanged()
            }

            override fun onChange(selfChange: Boolean, uris: MutableCollection<Uri>, flags: Int) {
                //super.onChange(selfChange, uris, flags)
                onContentChanged?.onMediaChanged()
            }
        }
    }

}
