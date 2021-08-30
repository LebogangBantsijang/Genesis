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
import android.provider.MediaStore
import com.lebogang.gensysmusic.R

class ContentSettings(private val context: Context) {
    private val preferences:SharedPreferences = context.applicationContext
            .getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    private val sortKey = "SortOrder"
    private val durationKey = "AudioDurationFilter"
    val titleAsc = MediaStore.Audio.Media.TITLE + " ASC"
    val titleDesc = MediaStore.Audio.Media.TITLE + " DESC"
    val dateAsc = MediaStore.Audio.Media.DATE_MODIFIED + " ASC"
    val dateDesc = MediaStore.Audio.Media.DATE_MODIFIED + " DESC"

    fun setSortOrder(order:String){
        preferences.edit().putString(sortKey, order).apply()
    }

    fun getSortOrder():String{
        val order = preferences.getString(sortKey, titleAsc)
        return order!!
    }

    fun setDurationFilter(duration:Long){
        preferences.edit().putLong(durationKey, duration).apply()
    }

    fun getDurationFilter():Long{
        return preferences.getLong(durationKey, 0)
    }

}
