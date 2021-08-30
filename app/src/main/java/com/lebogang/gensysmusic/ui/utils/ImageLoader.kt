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

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestListener
import com.lebogang.gensysmusic.R

class ImageLoader(private val activity: FragmentActivity) {

    fun loadImage(url:String?, type: Type, view: ImageView){
        when(type){
            Type.ALBUM -> loadAlbum(url,view)
            Type.ARTIST -> loadArtist(url, view)
            Type.MUSIC -> loadMusic(url,view)
            Type.USER -> loadUser(url, view)
            Type.PLAYER -> loadMusic(url,view)
            else -> {}
        }
    }

    fun loadImage(url:String?, view: ImageView, listener: RequestListener<Bitmap>){
        Glide.with(activity).asBitmap()
            .load(url)
            .listener(listener)
            .downsample(DownsampleStrategy.AT_MOST)
            .override(view.width,view.height)
            .error(R.drawable.ic_musical_notes_ios)
            .centerCrop()
            .into(view)
    }

    fun loadGif(view: ImageView){
        Glide.with(activity)
            .asGif()
            .load(R.raw.gif_2)
            .downsample(DownsampleStrategy.AT_MOST)
            .override(view.width,view.height)
            .error(R.drawable.music_drawable)
            .centerCrop()
            .into(view)
    }

    private fun loadMusic(url:String?, view: ImageView){
        Glide.with(activity)
            .load(url)
            .downsample(DownsampleStrategy.AT_MOST)
            .override(view.width,view.height)
            .error(R.drawable.music_drawable)
            .centerCrop()
            .into(view)
    }

    private fun loadAlbum(url:String?, view: ImageView){
        Glide.with(activity)
            .load(url)
            .downsample(DownsampleStrategy.AT_MOST)
            .override(view.width,view.height)
            .centerCrop()
            .error(R.drawable.album_drawable)
            .into(view)
    }

    private fun loadArtist(url:String?, view: ImageView){
        Glide.with(activity)
            .load(url)
            .downsample(DownsampleStrategy.AT_MOST)
            .error(R.drawable.artist_drawable)
            .override(view.width,view.height)
            .circleCrop()
            .into(view)
    }

    private fun loadUser(url:String?, view: ImageView){
        Glide.with(activity).load(url)
            .placeholder(R.drawable.ic_male_user_ios)
            .override(view.width,view.height)
            .circleCrop()
            .into(view)
    }

}
