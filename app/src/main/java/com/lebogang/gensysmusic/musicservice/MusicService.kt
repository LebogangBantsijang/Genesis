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

package com.lebogang.gensysmusic.musicservice

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.AudioManager
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import com.lebogang.gensysmusic.musicservice.EffectManager.*
import com.lebogang.gensysmusic.utils.Keys
import com.lebogang.gensysmusic.utils.models.MusicAbstract

class MusicService:Service(){
    private val binder = MusicBinder()
    private var queue = mutableListOf<MusicAbstract>()
    private lateinit var focusManager: FocusManager
    private val listenerMap = HashMap<String,OnStateChanged>()
    private lateinit var effectManager: EffectManager
    private lateinit var music: MusicAbstract
    private lateinit var notifications: Notifications
    private lateinit var broadcastReceiver: MusicBroadcastReceiver
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var repeatState:RepeatState

    override fun onCreate() {
        super.onCreate()
        focusManager = FocusManager(this,getFocusListener())
        effectManager = EffectManager(23)//get sessionId from media player
        notifications = Notifications(this)
        broadcastReceiver = MusicBroadcastReceiver(this)
        registerReceiver(broadcastReceiver,MusicBroadcastReceiver.getIntentFilters())
        sharedPreferences = getSharedPreferences(Keys.PREFERENCE_NAME, Context.MODE_PRIVATE)
        repeatState = OnStateChanged.getRepeatState(sharedPreferences.getInt(Keys.REPEAT_STATE,0))
    }

    override fun onDestroy() {
        super.onDestroy()
        RepeatState.NONE.ordinal
        stop()
        unregisterReceiver(broadcastReceiver)
        sharedPreferences.edit().putInt(Keys.REPEAT_STATE,repeatState.ordinal).apply()
    }

    override fun onBind(intent: Intent?): IBinder = binder

    inner class MusicBinder:Binder(){
        fun getMusicService():MusicService = this@MusicService
    }

    private fun getFocusListener() = AudioManager.OnAudioFocusChangeListener {
        if (it == AudioManager.AUDIOFOCUS_LOSS || it == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT)
            pause()
    }

    fun addPlaybackListener(className:String,listener:OnStateChanged){
        listenerMap[className] = listener
    }

    fun addToQueue(music: MusicAbstract,index:Int){
        if (index > -1 && index < queue.size)
            queue.add(index,music)
        else
            queue.add(music)
    }

    fun setQueue(queue:MutableList<MusicAbstract>){
        this.queue = queue
    }

    fun play(queue: MutableList<MusicAbstract>, music: MusicAbstract){
        this.queue = queue
        play(music)
    }

    fun play(music:MusicAbstract){
        listenerMap.forEach { it.value.onPlayback(PlaybackState.PLAYING) }
        focusManager.requestFocus()
        this.music = music
        //play music 1st
        startForeground(notifications.notificationId
            ,notifications.getNotification(music,PlaybackState.PLAYING))
    }

    fun play(){
        listenerMap.forEach { it.value.onPlayback(PlaybackState.PLAYING) }
        focusManager.requestFocus()
        //play music 1st
        startForeground(notifications.notificationId
            ,notifications.getNotification(music,PlaybackState.PLAYING))
    }

    fun pause(){
        listenerMap.forEach { it.value.onPlayback(PlaybackState.PAUSED) }
        focusManager.abandonFocus()
        //play music 1st
        startForeground(notifications.notificationId
            ,notifications.getNotification(music,PlaybackState.PAUSED))
    }

    fun stop(){
        listenerMap.forEach { it.value.onPlayback(PlaybackState.STOPPED) }
        focusManager.abandonFocus()
        //stop music 1st
        stopForeground(true)
        stopSelf()
    }

    fun playToggle(){
        //finish later
    }

    fun skipTo(position:Int){
        listenerMap.forEach { it.value.onPlayback(PlaybackState.SKIP_TO) }
        focusManager.abandonFocus()
        val music = queue[position]
        play(music)
    }

    fun skipNext(){
        listenerMap.forEach { it.value.onPlayback(PlaybackState.SKIP_NEXT) }
        focusManager.abandonFocus()
        //not sure if its gonna work but hey.
        val index = queue.indexOf(music) + 1
        if (index < queue.size)
            play(queue[index])
        else
            play(queue[0])
    }

    fun skipPrevious(){
        listenerMap.forEach { it.value.onPlayback(PlaybackState.SKIP_PREVIOUS) }
        focusManager.abandonFocus()
        val index = queue.indexOf(music) - 1
        if (index >= 0)
            play(queue[index])
        else
            play(queue[(queue.size - 1)])
    }

    fun getMusic() = music

    fun getRepeatState() = repeatState

    fun setRepeatState(state: RepeatState){
        repeatState = state
        listenerMap.forEach{it.value.onRepeat(repeatState)}
    }

    fun getCurrentPosition() = queue.size

    @RequiresApi(Build.VERSION_CODES.P)
    fun setEffectGain(input:Float, type: InputType) = effectManager.setInput(input,type)

    @RequiresApi(Build.VERSION_CODES.P)
    fun getEffectGain(type: InputType) = effectManager.getGain(type)

    @RequiresApi(Build.VERSION_CODES.P)
    fun enableEffect(state :Boolean) = effectManager.setEnabledDynamicProcessing(state)

    @RequiresApi(Build.VERSION_CODES.P)
    fun isEffectEnabled() = effectManager.isDynamicProcessingEnabled()

}
