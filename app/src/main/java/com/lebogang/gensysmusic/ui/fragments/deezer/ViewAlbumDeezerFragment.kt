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

package com.lebogang.gensysmusic.ui.fragments.deezer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.databinding.FragmentDeezerViewAlbumBinding
import com.lebogang.gensysmusic.network.models.AlbumDeezer
import com.lebogang.gensysmusic.network.models.TrackDeezer
import com.lebogang.gensysmusic.ui.adapters.ItemDeezerAlbumArtistSongAdapter
import com.lebogang.gensysmusic.ui.adapters.utils.OnDeezerAudioClickListener
import com.lebogang.gensysmusic.ui.helpers.ThemeHelper
import com.lebogang.gensysmusic.utils.Keys
import com.lebogang.gensysmusic.utils.GlideManager
import com.lebogang.gensysmusic.viewmodels.DeezerViewModel
import com.lebogang.gensysmusic.viewmodels.ViewModelFactory

class ViewAlbumDeezerFragment: Fragment(), OnDeezerAudioClickListener {
    private val viewBinding: FragmentDeezerViewAlbumBinding by lazy { FragmentDeezerViewAlbumBinding
            .inflate(layoutInflater) }
    private val viewModel: DeezerViewModel by lazy { ViewModelFactory(requireActivity().application)
            .getDeezerViewModel()}
    private lateinit var album: AlbumDeezer
    private val adapter = ItemDeezerAlbumArtistSongAdapter().apply { listener = this@ViewAlbumDeezerFragment }
    @ColorInt
    private var explicitColor:Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        album = requireArguments().getParcelable(Keys.DEEZER_ALBUM_KEY)!!
        explicitColor = if (album.hasExplicitLyrics) ThemeHelper.PRIMARY_COLOR else
            ThemeHelper.SECONDARY_TEXTCOLOR_NO_DISABLE
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GlideManager(this).loadOnline(album.coverMedium, viewBinding.imageView)
        viewBinding.titleView.text = album.title
        viewBinding.subtitleView.text = album.artist.title
        viewBinding.explicitView.setTextColor(explicitColor)
        viewBinding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewBinding.recyclerView.adapter = adapter
        viewModel.liveDataAudioChart.observe(viewLifecycleOwner, {
            adapter.setData(it)
            viewBinding.loadingView.visibility = View.GONE
        })
        viewModel.getAlbumTracks(album.id)
    }

    override fun onAudioClick(audio: TrackDeezer) {
        val controller = findNavController()
        val bundle = Bundle().apply {
            putParcelable(Keys.DEEZER_SONG_KEY, audio)
            putString(Keys.ALBUM_ART_URL_KEY, album.coverMedium)
        }
        controller.navigate(R.id.dialogDeezerPreview, bundle)
    }
}
