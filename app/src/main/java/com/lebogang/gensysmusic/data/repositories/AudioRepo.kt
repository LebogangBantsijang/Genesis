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

import android.content.ContentValues
import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import com.lebogang.gensysmusic.data.models.Audio
import com.lebogang.gensysmusic.data.repositories.local.LocalAudio
import com.lebogang.gensysmusic.viewmodels.utils.OnContentChanged
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AudioRepo(val context: Context){
    private val localAudio = LocalAudio(context)
    private val contentObserver = getContentObserver()
    private var onContentChanged: OnContentChanged? = null
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun registerObserver(onContentChanged: OnContentChanged){
        localAudio.contentResolver.registerContentObserver(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                , true, contentObserver)
        this.onContentChanged = onContentChanged
    }

    fun unregisterObserver(){
        localAudio.contentResolver.unregisterContentObserver(contentObserver)
    }

    suspend fun getAudio():MutableList<Audio> = coroutineScope {
        val deffer = async(Dispatchers.IO) {
            localAudio.getAudio()
        }
        deffer.await()
    }

    fun getAudio(id:Long):Audio{
        return localAudio.getAudio(id)
    }

    fun isAudioAvailable(id:Long):Boolean{
        return localAudio.isAudioAvailable(id)
    }

    suspend fun getAlbumAudio(albumName:String):MutableList<Audio> = coroutineScope {
        val deffer = async {
            localAudio.getAlbumAudio(albumName)
        }
        deffer.await()
    }

    suspend fun getArtistAudio(artistName:String):MutableList<Audio> = coroutineScope {
        val deffer = async {
            localAudio.getArtistAudio(artistName)
        }
        deffer.await()
    }

    suspend fun getAudio(uri: Uri):MutableList<Audio> = coroutineScope {
        val deffer = async {
            localAudio.getAudio(uri)
        }
        deffer.await()
    }

    suspend fun getAudio(audioIdList:List<Long>?):MutableList<Audio> = coroutineScope {
        val deffer = async {
            localAudio.getAudio(audioIdList)
        }
        deffer.await()
    }

    fun updateAudio(audio: Audio, contentValues: ContentValues) = scope.launch {
        localAudio.updateAudio(audio, contentValues)
    }

    fun deleteAudio(audio: Audio) = scope.launch {
        localAudio.deleteAudio(audio)
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
