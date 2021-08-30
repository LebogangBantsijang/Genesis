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

package com.lebogang.gensysmusic.ui.stream.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.lebogang.gensysmusic.databinding.FragmentDetailsVisualsBinding
import com.lebogang.gensysmusic.ui.utils.ImageLoader
import com.lebogang.gensysmusic.utils.Keys
import com.lebogang.gensysmusic.utils.models.ArtistAbstract
import jp.wasabeef.blurry.Blurry

class DetailsVisualFragment(var details: ArtistAbstract?):Fragment(){
    private lateinit var bind:FragmentDetailsVisualsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        details = savedInstanceState?.getParcelable(Keys.ITEM_KEY)
    }

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, state: Bundle?): View {
        bind = FragmentDetailsVisualsBinding.inflate(inflater,parent,false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews(){
        details?.let {
            ImageLoader(requireActivity())
                .loadImage(it.getItemArt(),bind.imageView,getRequestListener())
        }
    }

    private fun getRequestListener() = object :RequestListener<Bitmap>{
        override fun onLoadFailed(e: GlideException?,
                                  m: Any?, t: Target<Bitmap>?, first: Boolean): Boolean = true

        override fun onResourceReady(r: Bitmap?, m: Any?, t: Target<Bitmap>?, s: DataSource?,
                                     first: Boolean): Boolean {
            r?.let {
                Blurry.with(requireContext()).async()
                    .radius(10)
                    .from(it)
                    .into(bind.blurryView)
            }
            return true
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(Keys.ITEM_KEY,details)
        super.onSaveInstanceState(outState)
    }
}
