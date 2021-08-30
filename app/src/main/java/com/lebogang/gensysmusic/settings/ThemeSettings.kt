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
import androidx.appcompat.app.AppCompatDelegate
import com.lebogang.gensysmusic.R

class ThemeSettings(context: Context) {
    private val preferences: SharedPreferences = context.applicationContext
            .getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    private val themeKey = "ThemeResource"
    private val itemDisplay = "ColumnCount"

    fun getThemeMode():Int{
        return preferences.getInt(themeKey, AppCompatDelegate.MODE_NIGHT_YES)
    }

    fun setThemeMode(mode:Int){
        preferences.edit().putInt(themeKey, mode).apply()
    }

    fun getColumnCount():Int{
        return preferences.getInt(itemDisplay, 2)
    }

    fun setColumnCount(count:Int){
        preferences.edit().putInt(itemDisplay, count).apply()
    }
}
