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

package com.lebogang.gensysmusic.settings

import android.content.Context
import android.content.SharedPreferences
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.servicehelpers.RepeatSate

class PlayerSettings(private val context: Context){
    private val preferences: SharedPreferences = context.applicationContext
        .getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    private val repeatKey = "Repeat"
    private val playerBackgroundKey = "PlayerBackground"

    fun setRepeatMode(repeatSate: RepeatSate){
        preferences.edit().putString(repeatKey, repeatSate.toString()).apply()
    }

    fun getRepeatMode(): RepeatSate {
        return when(preferences.getString(repeatKey, RepeatSate.REPEAT_NONE.toString())){
            RepeatSate.REPEAT_ALL.toString() -> RepeatSate.REPEAT_ALL
            RepeatSate.REPEAT_ONE.toString() -> RepeatSate.REPEAT_ONE
            RepeatSate.SHUFFLE_ALL.toString() -> RepeatSate.SHUFFLE_ALL
            else -> RepeatSate.REPEAT_NONE
        }
    }

    fun setBackgroundType(playerBackgroundType: PlayerBackgroundType){
        preferences.edit().putString(playerBackgroundKey,playerBackgroundType.toString()).apply()
    }

    fun getBackgroundType():PlayerBackgroundType{
        return when(preferences.getString(playerBackgroundKey,PlayerBackgroundType.ADAPTIVE_BLURRY.toString())){
            PlayerBackgroundType.NONE.toString() -> PlayerBackgroundType.NONE
            PlayerBackgroundType.ADAPTIVE_IMAGE.toString() -> PlayerBackgroundType.ADAPTIVE_IMAGE
            PlayerBackgroundType.GIF.toString() -> PlayerBackgroundType.GIF
            else -> PlayerBackgroundType.ADAPTIVE_BLURRY
        }
    }
}
