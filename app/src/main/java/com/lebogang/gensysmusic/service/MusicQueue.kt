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

package com.lebogang.gensysmusic.service

import androidx.lifecycle.MutableLiveData
import com.lebogang.gensysmusic.data.models.Audio

object MusicQueue {
    val currentAudio = MutableLiveData<Audio>()
    var audioQueue = mutableListOf<Audio>()

    fun setCurrentAudio(audio: Audio, queue:MutableList<Audio>){
        audioQueue = queue
        currentAudio.value = audio
    }

    fun setCurrentAudio(audio: Audio){
        currentAudio.value = audio
    }

    fun removeAudio(audio: Audio){
        currentAudio.value?.let {
            if (it.id != audio.id)
                audioQueue.remove(audio)
        }
    }

    fun clearQueue(){
        audioQueue.clear()
        audioQueue.add(currentAudio.value!!)
    }

    fun getRandomAudio(): Audio {
        val audio = audioQueue[(Math.random()*(audioQueue.size -1)).toInt()]
        currentAudio.value = audio
        return audio
    }

    fun getNext(): Audio {
        val index = audioQueue.indexOf(currentAudio.value) + 1
        val audio = if (index >= audioQueue.size) audioQueue[0] else audioQueue[index]
        currentAudio.value = audio
        return audio
    }

    fun getPrevious(): Audio {
        val index = audioQueue.indexOf(currentAudio.value) - 1
        val audio = if (index < 0) audioQueue[(audioQueue.size - 1)] else audioQueue[index]
        currentAudio.value = audio
        return audio
    }
}
