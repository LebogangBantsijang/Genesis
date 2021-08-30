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

package com.lebogang.gensysmusic.ui.helpers

import android.os.Bundle
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatDelegate
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.settings.ThemeSettings

abstract class ThemeHelper:CommonActivity() {
    val themeSettings: ThemeSettings by lazy{
        ThemeSettings(this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        delegate.localNightMode = themeSettings.getThemeMode()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getColors()
    }

    private fun getColors(){
        PRIMARY_COLOR = getColorById(R.color.primaryColor)
        SECONDARY_TEXTCOLOR_NO_DISABLE = getColorById(R.color.secondaryTextColorLightNoDisable)
        when(themeSettings.getThemeMode()){
            AppCompatDelegate.MODE_NIGHT_NO->{
                PRIMARY_TEXTCOLOR = getColorById(R.color.primaryTextColorLight)
                SECONDARY_TEXTCOLOR = getColorById(R.color.secondaryTextColorLight)
            }
            else ->{
                PRIMARY_TEXTCOLOR = getColorById(R.color.primaryTextColorDark)
                SECONDARY_TEXTCOLOR = getColorById(R.color.secondaryTextColorDark)
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun getColorById(id:Int):Int{
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            resources.getColor(id, theme)
        } else {
            resources.getColor(id)
        }
    }

    companion object ThemeColors{
        @ColorInt var PRIMARY_COLOR:Int = 0
        @ColorInt var PRIMARY_TEXTCOLOR:Int = 0
        @ColorInt var SECONDARY_TEXTCOLOR:Int = 0
        @ColorInt var SECONDARY_TEXTCOLOR_NO_DISABLE:Int = 0
    }


}
