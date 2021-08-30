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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lebogang.gensysmusic.database.local.models.Music
import com.lebogang.gensysmusic.databinding.FragmentLyricsBinding
import com.lebogang.gensysmusic.utils.Keys

class LyricsFragment: Fragment() {
    private lateinit var bind:FragmentLyricsBinding
    private var music:Music? = null

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, state: Bundle?): View {
        bind = FragmentLyricsBinding.inflate(layoutInflater,parent, false)
        music = state?.getParcelable(Keys.MUSIC_KEY)
        return bind.root
    }

    fun setMusic(music:Music){
        this.music = music
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(Keys.MUSIC_KEY, music)
        super.onSaveInstanceState(outState)
    }
}
