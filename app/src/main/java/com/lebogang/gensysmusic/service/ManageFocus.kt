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

import android.app.Notification
import android.content.Context
import android.media.AudioManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.lebogang.gensysmusic.data.models.Audio
import com.lebogang.gensysmusic.servicehelpers.PlaybackState

abstract class ManageFocus(context: Context, private val listener : AudioManager.OnAudioFocusChangeListener) {
    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    @RequiresApi(Build.VERSION_CODES.O)
    private var focusAttributes :android.media.AudioAttributes? = null
    @RequiresApi(Build.VERSION_CODES.O)
    private var focus :android.media.AudioFocusRequest? = null

    @RequiresApi(Build.VERSION_CODES.O)
    fun getFocus():android.media.AudioFocusRequest{
        if (focus == null)
            focus = android.media.AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                    .setOnAudioFocusChangeListener(listener)
                    .setAudioAttributes(getFocusAttributes())
                    .build()
        return focus!!
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getFocusAttributes(): android.media.AudioAttributes{
        if (focusAttributes == null)
            focusAttributes = android.media.AudioAttributes.Builder()
                    .setContentType(android.media.AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setLegacyStreamType(android.media.AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(android.media.AudioAttributes.USAGE_MEDIA)
                    .build()
        return focusAttributes!!
    }


    @Suppress("DEPRECATION")
    fun requestFocus(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            audioManager.requestAudioFocus(getFocus())
        else
            audioManager.requestAudioFocus(listener, AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN)
    }

    @Suppress("DEPRECATION")
    fun abandonFocus(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            audioManager.abandonAudioFocusRequest(getFocus())
        else
            audioManager.abandonAudioFocus(listener)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createAudioFocusRequest():android.media.AudioFocusRequest{
        return android.media.AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
            .setAcceptsDelayedFocusGain(false)
            .setOnAudioFocusChangeListener(listener)
            .setAudioAttributes(getFocusAttributes())
            .build()
    }

    abstract fun createNotification(audio: Audio, playbackState: PlaybackState): Notification
}
