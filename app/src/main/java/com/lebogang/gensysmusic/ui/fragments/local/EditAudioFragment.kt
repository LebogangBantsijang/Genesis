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

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.data.models.Audio
import com.lebogang.gensysmusic.databinding.FragmentEditAudioBinding
import com.lebogang.gensysmusic.utils.Keys
import com.lebogang.gensysmusic.utils.GlideManager
import com.lebogang.gensysmusic.viewmodels.AudioViewModel
import com.lebogang.gensysmusic.viewmodels.ViewModelFactory

class EditAudioFragment: Fragment() {
    private val viewBinding: FragmentEditAudioBinding by lazy { FragmentEditAudioBinding.inflate(layoutInflater) }
    private val viewModel: AudioViewModel by lazy { ViewModelFactory(requireActivity().application).getAudioViewModel()}
    private lateinit var audio: Audio

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        audio = requireArguments().getParcelable(Keys.MUSIC_KEY)!!
        return viewBinding.root
    }

    @SuppressLint("InlinedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateViews()
        onSaveNewValues()
    }

    private fun populateViews(){
        GlideManager(this).loadAudioArt(audio.getArtUri(),viewBinding.coverView)
        viewBinding.titleView.setText(audio.title)
        viewBinding.artistView.setText(audio.artist)
        viewBinding.albumView.setText(audio.album)
    }

    /**
     * Save the new value to the media store when the save button is clicked
     * */
    @SuppressLint("InlinedApi")
    private fun onSaveNewValues(){
        viewBinding.updateView.setOnClickListener {
            val title = viewBinding.titleView.text
            val artist = viewBinding.artistView.text
            val album = viewBinding.albumView.text
            if (!title.isNullOrEmpty() and !artist.isNullOrEmpty() and !album.isNullOrEmpty()){
                val values = ContentValues().apply {
                    put(MediaStore.Audio.Media.TITLE, title.toString())
                    put(MediaStore.Audio.Media.ARTIST, artist.toString())
                    put(MediaStore.Audio.Media.ALBUM, album.toString())
                }
                viewModel.updateAudio(audio, values)
                Snackbar.make(viewBinding.root, getString(R.string.update_successful), Snackbar.LENGTH_SHORT)
                        .show()
            }else{
                Snackbar.make(viewBinding.root, getString(R.string.null_values), Snackbar.LENGTH_SHORT)
                        .show()
            }
        }
    }

}
