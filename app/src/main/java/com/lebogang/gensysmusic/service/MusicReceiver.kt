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

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter

const val PLAY_ACTION = "Play"
const val PAUSE_ACTION = "Pause"
const val SKIP_NEXT_ACTION = "Next"
const val SKIP_PREV_ACTION = "Previous"

class MusicReceiver(private val musicService:MusicService) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when(intent.action){
            SKIP_PREV_ACTION -> musicService.skipToPrevious()
            PAUSE_ACTION-> musicService.pause()
            PLAY_ACTION-> musicService.play()
            SKIP_NEXT_ACTION-> musicService.skipToNext()
        }
    }

}
