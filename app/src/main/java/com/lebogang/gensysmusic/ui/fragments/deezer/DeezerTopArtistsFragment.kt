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
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.databinding.FragmentDeezerArtistsBinding
import com.lebogang.gensysmusic.network.models.ArtistDeezer
import com.lebogang.gensysmusic.ui.adapters.ItemDeezerArtistAdapter
import com.lebogang.gensysmusic.ui.adapters.utils.OnDeezerArtistClickListener
import com.lebogang.gensysmusic.utils.Keys
import com.lebogang.gensysmusic.viewmodels.DeezerViewModel
import com.lebogang.gensysmusic.viewmodels.ViewModelFactory

class DeezerTopArtistsFragment: Fragment(), OnDeezerArtistClickListener {
    private val viewBinding:FragmentDeezerArtistsBinding by lazy {FragmentDeezerArtistsBinding.inflate(layoutInflater)}
    private val adapterArtists = ItemDeezerArtistAdapter().apply { listener = this@DeezerTopArtistsFragment }
    private val viewModel: DeezerViewModel by lazy { ViewModelFactory(requireActivity().application)
            .getDeezerViewModel() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initViews(){
        initLoadingView(true)
        viewBinding.recyclerView.layoutManager = StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL)
        viewBinding.recyclerView.adapter = adapterArtists
        viewModel.getChartArtist()
    }

    private fun initObservers(){
        viewModel.liveDataArtistChart.observe(viewLifecycleOwner,{
            adapterArtists.setData(it)
            initLoadingView(false)
        })
    }

    private fun initLoadingView(value:Boolean){
        if (value)
            viewBinding.loadingView.visibility = View.VISIBLE
        else
            viewBinding.loadingView.visibility = View.GONE
    }

    override fun onArtistClick(artist: ArtistDeezer) {
        val controller = findNavController()
        val bundle = Bundle().apply { putParcelable(Keys.DEEZER_ARTIST_KEY, artist) }
        controller.navigate(R.id.viewArtistDeezerFragment, bundle)
    }
}
