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

package com.lebogang.gensysmusic.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.lebogang.gensysmusic.R
import jp.wasabeef.blurry.Blurry
import java.io.FileNotFoundException

class GlideManager {
    private val requestBuilder: RequestBuilder<Drawable>
    private val audioErrorResource = R.drawable.ic_custom_song
    private val audioErrorCircularResource = R.drawable.ic_custom_circular_song
    private val artistErrorResource = R.drawable.ic_custom_artist
    private val albumErrorResource = R.drawable.ic_custom_album

    constructor(root: View){
        requestBuilder= initRequestBuilder(Glide.with(root).asDrawable())
    }
    constructor(root: AppCompatActivity){
        requestBuilder= initRequestBuilder(Glide.with(root).asDrawable())
    }
    constructor(root: Fragment){
        requestBuilder= initRequestBuilder(Glide.with(root).asDrawable())
    }

    private fun initRequestBuilder(builder: RequestBuilder<Drawable>): RequestBuilder<Drawable> {
        var skipCache = false
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            skipCache = true
        return builder.apply{
            skipMemoryCache(skipCache)
            diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        }
    }

    fun loadAudioArt(uri: Uri?, view: ImageView): GlideManager {
        requestBuilder.load(uri)
                .centerCrop()
                .error(audioErrorResource)
                .override(view.width, view.height)
                .into(view)
                .waitForLayout()
                .clearOnDetach()
        return this
    }

    fun loadAudioArtCircularCrop(uri: Uri?, view: ImageView): GlideManager {
        requestBuilder.load(uri)
            .circleCrop()
            .error(audioErrorCircularResource)
            .override(view.width, view.height)
            .into(view)
            .waitForLayout()
            .clearOnDetach()
        return this
    }

    fun loadOnline(path:String?, view: ImageView): GlideManager {
        requestBuilder.load(path)
                .centerCrop()
                .error(audioErrorResource)
                .override(view.width, view.height)
                .into(view)
                .waitForLayout()
                .clearOnDetach()
        return this
    }

    fun loadArtistArt(uri: Uri?, view: ImageView): GlideManager {
        requestBuilder.load(uri)
                .centerCrop()
                .error(artistErrorResource)
                .override(view.width, view.height)
                .into(view)
                .waitForLayout()
                .clearOnDetach()
        return this
    }

    fun loadAlbumArt(uri: Uri?, view: ImageView): GlideManager {
        requestBuilder.load(uri)
                .centerCrop()
                .error(albumErrorResource)
                .override(view.width, view.height)
                .into(view)
                .waitForLayout()
                .clearOnDetach()
        return this
    }

    fun loadBlurred(root: Context, uri: Uri?, view: ImageView): GlideManager {
        Glide.with(root).asBitmap()
                .centerCrop()
                .load(uri)
                .addListener(getCallbacks(root, view))
                .submit(view.width, view.height)
        return this
    }

    fun loadGif(activity: AppCompatActivity, view: ImageView){
        var skipCache = false
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            skipCache = true
        Glide.with(activity).asGif()
            .skipMemoryCache(skipCache)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .centerCrop()
            .load(R.raw.gif_2)
            .into(view)
            .waitForLayout()
            .clearOnDetach()
    }

    private fun getCallbacks(root: Context, view: ImageView): RequestListener<Bitmap> {
        return object : RequestListener<Bitmap> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?
                                      , isFirstResource: Boolean): Boolean {
                view.setImageDrawable(null)
                return true
            }
            override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?
                                         , dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                resource?.let {
                    Blurry.with(root)
                            .radius(12)
                            .sampling(4)
                            .async()
                            .from(it)
                            .into(view)
                }
                return true
            }
        }
    }

    companion object{
        fun getBitmap(uri: Uri, context:Context): Bitmap {
            return try {
                val inputStream = context.contentResolver.openInputStream(uri)
                BitmapFactory.decodeStream(inputStream)
            }catch (e: FileNotFoundException){
                BitmapFactory.decodeResource(context.resources, R.raw.default_bg)
            }
        }
    }
}
