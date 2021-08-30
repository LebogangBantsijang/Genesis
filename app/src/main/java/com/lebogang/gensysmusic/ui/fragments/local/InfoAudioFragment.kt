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

package com.lebogang.gensysmusic.ui.fragments.local

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lebogang.gensysmusic.data.models.Audio
import com.lebogang.gensysmusic.databinding.FragmentAudioInfoBinding
import com.lebogang.gensysmusic.utils.Keys
import com.lebogang.gensysmusic.utils.GlideManager

class InfoAudioFragment : Fragment(){
    private val viewBinding: FragmentAudioInfoBinding by lazy{ FragmentAudioInfoBinding.inflate(layoutInflater) }
    private lateinit var audio: Audio

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        audio = requireArguments().getParcelable(Keys.MUSIC_KEY)!!
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.titleView.text = audio.title
        viewBinding.artistView.text = audio.artist
        viewBinding.albumView.text = audio.album
        viewBinding.durationView.text = audio.durationFormatted
        viewBinding.sizeView.text = audio.size
        GlideManager(this).loadAudioArt(audio.getArtUri(),viewBinding.coverView)
    }
}
