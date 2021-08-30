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
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.databinding.FragmentDeezerViewArtistBinding
import com.lebogang.gensysmusic.network.models.ArtistDeezer
import com.lebogang.gensysmusic.network.models.TrackDeezer
import com.lebogang.gensysmusic.ui.adapters.ItemDeezerAlbumArtistSongAdapter
import com.lebogang.gensysmusic.ui.adapters.utils.OnDeezerAudioClickListener
import com.lebogang.gensysmusic.utils.Keys
import com.lebogang.gensysmusic.utils.GlideManager
import com.lebogang.gensysmusic.viewmodels.DeezerViewModel
import com.lebogang.gensysmusic.viewmodels.ViewModelFactory

class ViewArtistDeezerFragment : Fragment(), OnDeezerAudioClickListener{
    private val viewBinding: FragmentDeezerViewArtistBinding by lazy{
        FragmentDeezerViewArtistBinding.inflate(layoutInflater) }
    private val viewModel:DeezerViewModel by lazy{ViewModelFactory(requireActivity().application)
            .getDeezerViewModel()}
    private lateinit var artist: ArtistDeezer
    private val adapter = ItemDeezerAlbumArtistSongAdapter().apply {
        listener = this@ViewArtistDeezerFragment }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?
                              , savedInstanceState: Bundle?): View {
        artist = requireArguments().getParcelable(Keys.DEEZER_ARTIST_KEY)!!
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GlideManager(this).loadOnline(artist.coverMedium, viewBinding.imageView)
        viewBinding.titleView.text = artist.title
        viewBinding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewBinding.recyclerView.adapter = adapter
        viewModel.liveDataAudioChart.observe(viewLifecycleOwner,{
            adapter.setData(it)
            viewBinding.loadingView.visibility = View.GONE
        })
        viewModel.getArtistTracks(artist.id)
    }

    override fun onAudioClick(audio: TrackDeezer) {
        val controller = findNavController()
        val bundle = Bundle().apply { putParcelable(Keys.DEEZER_SONG_KEY, audio) }
        controller.navigate(R.id.dialogDeezerPreview, bundle)
    }
}
