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

import android.content.ContentValues
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lebogang.gensysmusic.data.models.Audio
import com.lebogang.gensysmusic.data.repositories.AudioRepo
import com.lebogang.gensysmusic.viewmodels.utils.AudioEnum
import com.lebogang.gensysmusic.viewmodels.utils.OnContentChanged
import kotlinx.coroutines.launch

class AudioViewModel(private val audioRepo: AudioRepo): ViewModel(), OnContentChanged {
    val liveData:MutableLiveData<MutableList<Audio>> = MutableLiveData()
    private lateinit var which: AudioEnum
    private var identifier:Any? = null

    fun registerContentObserver(){
        audioRepo.registerObserver(this)
    }

    fun unregisterContentContentObserver(){
        audioRepo.unregisterObserver()
    }

    fun updateAudio(audio: Audio, contentValues: ContentValues)
        = audioRepo.updateAudio(audio,contentValues)

    fun deleteAudio(audio: Audio) = audioRepo.deleteAudio(audio)

    fun getAudio() = viewModelScope.launch {
        liveData.postValue(audioRepo.getAudio())
    }

    fun getAudio(id:Long):Audio{
        return audioRepo.getAudio(id)
    }

    fun isAudioAvailable(id:Long):Boolean{
        return audioRepo.isAudioAvailable(id)
    }

    fun getAudio(uri: Uri) = viewModelScope.launch {
        liveData.postValue(audioRepo.getAudio(uri))
        which = AudioEnum.URI_AUDIO
        identifier = uri
    }

    fun getAlbumAudio(name:String) = viewModelScope.launch {
        liveData.postValue(audioRepo.getAlbumAudio(name))
        which = AudioEnum.ALBUM_AUDIO
        identifier = name
    }

    fun getArtistAudio(name:String) = viewModelScope.launch {
        liveData.postValue(audioRepo.getArtistAudio(name))
        which = AudioEnum.ARTIST_AUDIO
        identifier = name
    }

    fun getAudio(audioIdList:List<Long>) = viewModelScope.launch {
        liveData.postValue(audioRepo.getAudio(audioIdList))
        which = AudioEnum.LIST_AUDIO
        identifier = audioIdList
    }

    @Suppress("UNCHECKED_CAST")
    override fun onMediaChanged() {
        if (identifier!= null)
            when(which){
                AudioEnum.URI_AUDIO -> {
                    getAudio(identifier as Uri)
                }
                AudioEnum.ALBUM_AUDIO ->{
                    getAlbumAudio(identifier as String)
                }
                AudioEnum.ARTIST_AUDIO ->{
                    getArtistAudio(identifier as String)
                }
                AudioEnum.LIST_AUDIO ->{
                    getAudio(identifier as List<Long>)
                }
            }
        else
            getAudio()
    }

}
