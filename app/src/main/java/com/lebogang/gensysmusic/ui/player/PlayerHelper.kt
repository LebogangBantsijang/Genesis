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

package com.lebogang.gensysmusic.ui.player

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lebogang.gensysmusic.databinding.ActivityPlayerBinding

abstract class PlayerHelper: AppCompatActivity() {
    internal val bind: ActivityPlayerBinding by lazy{ ActivityPlayerBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)
    }

    /*internal fun initColors(music: AbstractMusic){
        Glide.with(this).asBitmap().load(music.getItemImagePath())
            .listener(getGlideListener(music))
            .submit()
    }

    internal fun loadNormalColors(music: AbstractMusic){
        bind.previousButton.imageTintList = ColorStateList
            .valueOf(Colors.getControlNormalColor(theme,this))
        bind.nextButton.imageTintList = ColorStateList
            .valueOf(Colors.getControlNormalColor(theme,this))
        bind.previousButton.imageTintList = ColorStateList.
        valueOf(Colors.getControlNormalColor(theme,this))
        bind.repeatView.imageTintList = ColorStateList
            .valueOf(Colors.getControlNormalColor(theme,this))
        bind.volumeView.imageTintList = ColorStateList
            .valueOf(Colors.getControlNormalColor(theme,this))
        if (!music.isItemFavourite())
            bind.favouriteView.imageTintList = ColorStateList
                .valueOf(Colors.getControlNormalColor(theme,this))
        bind.previousButton.imageTintList = ColorStateList
            .valueOf(Colors.getControlNormalColor(theme,this))
        bind.titleTextView.setTextColor(Colors.getTextColorPrimary(theme,this))
        bind.dateTextView.setTextColor(Colors.getTextColorTertiary(theme,this))
        bind.timeStartTextView.setTextColor(Colors.getTextColorSecondary(theme,this))
        bind.timeEndTextView.setTextColor(Colors.getTextColorSecondary(theme,this))
        bind.toolbar.setTitleTextColor(Colors.getTextColorPrimary(theme,this))
        bind.toolbar.setNavigationIconTint(Colors.getTextColorPrimary(theme,this))
        bind.toolbar.setSubtitleTextColor(Colors.getTextColorTertiary(theme,this))
        bind.appName.setTextColor(Colors.getTextColorTertiary(theme,this))
    }

    internal fun loadNormalColorsInverse(music: AbstractMusic){
        bind.previousButton.imageTintList = ColorStateList
            .valueOf(Colors.getControlNormalColorInverse(theme,this))
        bind.nextButton.imageTintList = ColorStateList
            .valueOf(Colors.getControlNormalColorInverse(theme,this))
        bind.previousButton.imageTintList = ColorStateList.
        valueOf(Colors.getControlNormalColorInverse(theme,this))
        bind.repeatView.imageTintList = ColorStateList
            .valueOf(Colors.getControlNormalColorInverse(theme,this))
        bind.volumeView.imageTintList = ColorStateList
            .valueOf(Colors.getControlNormalColorInverse(theme,this))
        if (!music.isItemFavourite())
            bind.favouriteView.imageTintList = ColorStateList
                .valueOf(Colors.getControlNormalColorInverse(theme,this))
        bind.previousButton.imageTintList = ColorStateList
            .valueOf(Colors.getControlNormalColorInverse(theme,this))
        bind.titleTextView.setTextColor(Colors.getTextColorPrimaryInverse(theme,this))
        bind.dateTextView.setTextColor(Colors.getTextColorTertiaryInverse(theme,this))
        bind.timeStartTextView.setTextColor(Colors.getTextColorSecondaryInverse(theme,this))
        bind.timeEndTextView.setTextColor(Colors.getTextColorSecondaryInverse(theme,this))
        bind.toolbar.setTitleTextColor(Colors.getTextColorPrimaryInverse(theme,this))
        bind.toolbar.setNavigationIconTint(Colors.getTextColorPrimaryInverse(theme,this))
        bind.toolbar.setSubtitleTextColor(Colors.getTextColorTertiaryInverse(theme,this))
        bind.appName.setTextColor(Colors.getTextColorTertiaryInverse(theme,this))
    }

    internal fun loadLightColors(music: AbstractMusic){
        val color = Colors.getWhiteColor(this)
        bind.previousButton.imageTintList = ColorStateList.valueOf(color)
        bind.nextButton.imageTintList = ColorStateList.valueOf(color)
        bind.previousButton.imageTintList = ColorStateList.valueOf(color)
        bind.repeatView.imageTintList = ColorStateList.valueOf(color)
        bind.volumeView.imageTintList = ColorStateList.valueOf(color)
        if (!music.isItemFavourite())
            bind.favouriteView.imageTintList = ColorStateList.valueOf(color)
        bind.previousButton.imageTintList = ColorStateList.valueOf(color)
        bind.titleTextView.setTextColor(color)
        bind.dateTextView.setTextColor(color)
        bind.timeStartTextView.setTextColor(color)
        bind.timeEndTextView.setTextColor(color)
        bind.toolbar.setTitleTextColor(color)
        bind.toolbar.setSubtitleTextColor(color)
        bind.toolbar.setNavigationIconTint(color)
        bind.appName.setTextColor(color)
    }

    private fun getGlideListener(music: AbstractMusic) = object :RequestListener<Bitmap>{
        override fun onLoadFailed(e: GlideException?, m: Any?, t: Target<Bitmap>?, first: Boolean): Boolean {
            bind.root.setBackgroundColor(Colors.getSurfaceColor(theme))
            loadNormalColors(music)
            return true
        }

        override fun onResourceReady(bitmap: Bitmap?, m: Any?, t: Target<Bitmap>?, s: DataSource?,
        first: Boolean): Boolean {
            if (bitmap != null){
                Palette.Builder(bitmap).generate().dominantSwatch?.let {
                    bind.root.setBackgroundColor(it.rgb)
                    if (ColorUtils.calculateLuminance(it.rgb) < 0.5)
                        loadLightColors(music)
                    else{
                        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
                            loadNormalColorsInverse(music)
                        else
                            loadNormalColors(music)
                    }
                }
            }
            return true
        }
    }*/

}
