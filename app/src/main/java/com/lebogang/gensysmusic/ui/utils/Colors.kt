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

package com.lebogang.gensysmusic.ui.utils

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.util.TypedValue
import com.lebogang.gensysmusic.R

object Colors {

    fun getPrimaryColor(context: Context):Int = context.getColor(R.color.primaryColor)

    fun getWhiteColor(context: Context):Int = context.getColor(R.color.white)

    fun getSurfaceColor(theme: Resources.Theme):Int{
        val typedValue = TypedValue()
        theme.resolveAttribute(R.attr.colorSurface,typedValue, true)
        return typedValue.data
    }

    fun getColorButtonNormal(theme: Resources.Theme):Int{
        val typedValue = TypedValue()
        theme.resolveAttribute(R.attr.colorButtonNormal,typedValue, true)
        return typedValue.data
    }

    fun getSelectableBackground(theme: Resources.Theme):Int{
        val typedValue = TypedValue()
        theme.resolveAttribute(android.R.attr.selectableItemBackground, typedValue,true)
        return typedValue.resourceId
    }

    fun getControlNormalColor(theme: Resources.Theme, context: Context):Int{
        val typedValue = TypedValue()
        theme.resolveAttribute(R.attr.colorControlNormal,typedValue, true)
        val color = if (typedValue.resourceId != 0) typedValue.resourceId else typedValue.data
        return context.getColor(color)
    }

    fun getTextColorPrimary(theme: Resources.Theme, context: Context):Int{
        val typedValue = TypedValue()
        theme.resolveAttribute(android.R.attr.textColorPrimary,typedValue, true)
        val color = if (typedValue.resourceId != 0) typedValue.resourceId else typedValue.data
        return context.getColor(color)
    }

    fun getTextColorSecondary(theme: Resources.Theme, context: Context):Int{
        val typedValue = TypedValue()
        theme.resolveAttribute(android.R.attr.textColorSecondary,typedValue, true)
        val color = if (typedValue.resourceId != 0) typedValue.resourceId else typedValue.data
        return context.getColor(color)
    }

    fun getTextColorTertiary(theme: Resources.Theme, context: Context):Int{
        val typedValue = TypedValue()
        theme.resolveAttribute(android.R.attr.textColorTertiary,typedValue, true)
        val color = if (typedValue.resourceId != 0) typedValue.resourceId else typedValue.data
        return context.getColor(color)
    }

    fun getControlNormalColorInverse(theme: Resources.Theme, context: Context):Int{
        val typedValue = TypedValue()
        theme.resolveAttribute(R.attr.colorButtonNormal,typedValue, true)
        val color = if (typedValue.resourceId != 0) typedValue.resourceId else typedValue.data
        return context.getColor(color)
    }

    fun getTextColorPrimaryInverse(theme: Resources.Theme, context: Context):Int{
        val typedValue = TypedValue()
        theme.resolveAttribute(android.R.attr.textColorPrimaryInverse,typedValue, true)
        val color = if (typedValue.resourceId != 0) typedValue.resourceId else typedValue.data
        return context.getColor(color)
    }

    fun getTextColorSecondaryInverse(theme: Resources.Theme, context: Context):Int{
        val typedValue = TypedValue()
        theme.resolveAttribute(android.R.attr.textColorSecondaryInverse,typedValue, true)
        val color = if (typedValue.resourceId != 0) typedValue.resourceId else typedValue.data
        return context.getColor(color)
    }

    fun getTextColorTertiaryInverse(theme: Resources.Theme, context: Context):Int{
        val typedValue = TypedValue()
        theme.resolveAttribute(android.R.attr.textColorTertiaryInverse,typedValue, true)
        val color = if (typedValue.resourceId != 0) typedValue.resourceId else typedValue.data
        return context.getColor(color)
    }

    fun getPrimaryColorTintList(context: Context) = ColorStateList.valueOf(getPrimaryColor(context))

    fun getButtonNormalTintList(theme: Resources.Theme) =
        ColorStateList.valueOf(getColorButtonNormal(theme))
}
