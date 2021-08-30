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

package com.lebogang.gensysmusic.ui.local.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.databinding.FragmentNowPlayingBinding
import com.lebogang.gensysmusic.ui.utils.ImageLoader
import com.lebogang.gensysmusic.ui.utils.Type

class NowPlayingFragment: Fragment() {
    private lateinit var bind:FragmentNowPlayingBinding
    private val imageLoader: ImageLoader by lazy { ImageLoader(requireActivity()) }
    private var url:String? = null
    private val preference:SharedPreferences by lazy{requireContext()
        .getSharedPreferences("",Context.MODE_PRIVATE)}

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, state: Bundle?): View {
        bind = FragmentNowPlayingBinding.inflate(layoutInflater,parent, false)
        url = state?.getString("url")
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setImage(url)
        initViews()
    }

    private fun initViews(){
        bind.changeImageButton.setOnClickListener {displayMessage()}
    }

    fun setImage(url:String?){
        this.url = url
        val loadGif = preference.getBoolean("gif", false)
        if (loadGif) imageLoader.loadGif(bind.imageView)
        else imageLoader.loadImage(url, Type.PLAYER,bind.imageView)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("url",url)
        super.onSaveInstanceState(outState)
    }

    private fun displayMessage(){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.customize_title))
            .setMessage(getString(R.string.customize_message))
            .setNegativeButton("Reset") { _, _ ->
                preference.edit().putBoolean("gif",false).apply()
                setImage(url)
            }
            .setPositiveButton("Change") { _, _ ->
                preference.edit().putBoolean("gif",true).apply()
                setImage(url)
            }.create().show()
    }

}
