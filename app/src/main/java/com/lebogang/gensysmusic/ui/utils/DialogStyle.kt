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
import android.util.TypedValue
import com.google.android.material.shape.MaterialShapeDrawable

object DialogStyle {

    private fun getDialogElevation(context: Context):Float{
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 32f, context.resources.displayMetrics
        )
    }

    private fun getDialogCornerRadius(context: Context):Float{
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 12f, context.resources.displayMetrics
        )
    }

    fun getDialogBackground(context: Context): MaterialShapeDrawable {
        return MaterialShapeDrawable.createWithElevationOverlay(
            context,
            getDialogElevation(context)
        ).apply {
            setCornerSize(getDialogCornerRadius(context))
        }
    }
}
