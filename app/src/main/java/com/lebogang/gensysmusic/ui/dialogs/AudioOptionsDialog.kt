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

package com.lebogang.gensysmusic.ui.dialogs

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.data.models.Audio
import com.lebogang.gensysmusic.databinding.DialogAudioOptionsBinding
import com.lebogang.gensysmusic.utils.Keys
import com.lebogang.gensysmusic.utils.GlideManager
import com.lebogang.gensysmusic.viewmodels.AlbumViewModel
import com.lebogang.gensysmusic.viewmodels.ArtistViewModel
import com.lebogang.gensysmusic.viewmodels.ViewModelFactory

class AudioOptionsDialog :BottomSheetDialogFragment(){
    private val viewBinding:DialogAudioOptionsBinding by lazy { DialogAudioOptionsBinding.inflate(layoutInflater) }
    private lateinit var audio:Audio
    private val viewModelAlbum:AlbumViewModel by lazy{ViewModelFactory(requireActivity().application)
            .getAlbumViewModel()}
    private val viewModelArtist:ArtistViewModel by lazy { ViewModelFactory(requireActivity().application)
            .getArtistViewModel() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        audio = requireArguments().getParcelable(Keys.MUSIC_KEY)!!
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateViews()
        initOnClicks()
    }

    @SuppressLint("SetTextI18n")
    private fun populateViews(){
        GlideManager(this).loadAudioArt(audio.getArtUri(), viewBinding.coverView)
        viewBinding.titleView.text = audio.title
        viewBinding.subtitleView.text = audio.artist
        viewBinding.albumView.text = getString(R.string.view_album) + ":" + audio.album
        viewBinding.artistView.text = getString(R.string.view_artist) + ":" + audio.artist
    }

    private fun initOnClicks(){
        viewBinding.addToListView.setOnClickListener {
            val bundle = Bundle().apply { putParcelable(Keys.MUSIC_KEY, audio) }
            val controller = findNavController()
            controller.navigate(R.id.selectPlaylistDialog, bundle)
        }
        viewBinding.artistView.setOnClickListener {
            val artist = viewModelArtist.getArtists(audio.artist)
            val bundle = Bundle().apply { putParcelable(Keys.ARTIST_KEY, artist) }
            val controller = findNavController()
            controller.navigate(R.id.viewArtistFragment, bundle)
        }
        viewBinding.albumView.setOnClickListener {
            val album = viewModelAlbum.getAlbums(audio.album)
            val bundle = Bundle().apply { putParcelable(Keys.ALBUM_KEY, album) }
            val controller = findNavController()
            controller.navigate(R.id.viewAlbumFragment, bundle)
        }
        if(requireArguments().getBoolean(Keys.ENABLE_UPDATE_KEY))
            viewBinding.editView.setOnClickListener {
                val bundle = Bundle().apply { putParcelable(Keys.MUSIC_KEY, audio) }
                val controller = findNavController()
                controller.navigate(R.id.editAudioFragment, bundle)
            }
        else
            viewBinding.editView.visibility = View.GONE

        viewBinding.infoView.setOnClickListener {
            val bundle = Bundle().apply { putParcelable(Keys.MUSIC_KEY, audio) }
            val controller = findNavController()
            controller.navigate(R.id.infoAudioFragment, bundle)
        }
        viewBinding.shareView.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, audio.getUri())
                type = "audio/*"
            }
            startActivity(Intent.createChooser(intent,"Share from Genesis"))
        }
    }

}
