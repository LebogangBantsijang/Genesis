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
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.databinding.FragmentDeezerSearchBinding
import com.lebogang.gensysmusic.network.models.AlbumDeezer
import com.lebogang.gensysmusic.network.models.ArtistDeezer
import com.lebogang.gensysmusic.network.models.TrackDeezer
import com.lebogang.gensysmusic.ui.adapters.ItemDeezerAlbumAdapter
import com.lebogang.gensysmusic.ui.adapters.ItemDeezerArtistAdapter
import com.lebogang.gensysmusic.ui.adapters.ItemDeezerSongAdapter
import com.lebogang.gensysmusic.ui.adapters.utils.OnDeezerAlbumClickListener
import com.lebogang.gensysmusic.ui.adapters.utils.OnDeezerArtistClickListener
import com.lebogang.gensysmusic.ui.adapters.utils.OnDeezerAudioClickListener
import com.lebogang.gensysmusic.utils.Keys
import com.lebogang.gensysmusic.viewmodels.DeezerViewModel
import com.lebogang.gensysmusic.viewmodels.ViewModelFactory

class DeezerSearchFragment: Fragment(), OnDeezerAudioClickListener, OnDeezerArtistClickListener
        , OnDeezerAlbumClickListener {
    private val viewBinding: FragmentDeezerSearchBinding by lazy{
        FragmentDeezerSearchBinding.inflate(layoutInflater) }
    private val viewModel: DeezerViewModel by lazy{
        ViewModelFactory(requireActivity().application).getDeezerViewModel()}
    private val adapterTrack = ItemDeezerSongAdapter().apply { listener = this@DeezerSearchFragment }
    private val adapterArtist = ItemDeezerArtistAdapter().apply { listener = this@DeezerSearchFragment }
    private val adapterAlbum = ItemDeezerAlbumAdapter().apply { listener = this@DeezerSearchFragment }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObservers()
    }

    private fun initView(){
        viewBinding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewBinding.recyclerView.adapter = adapterTrack
        viewBinding.submitView.setOnClickListener {
            val query = viewBinding.searchView.text
            if (!query.isNullOrEmpty()){
                query(viewBinding.chipGroupView.checkedChipId, query.toString())
            }
        }
        viewBinding.searchView.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                val query = viewBinding.searchView.text
                if (!query.isNullOrEmpty()){
                    query(viewBinding.chipGroupView.checkedChipId, query.toString())
                }
                true
            }else
                false
        }
        viewBinding.chipGroupView.setOnCheckedChangeListener{_ , id->
            when(id){
                R.id.chipTrack -> {
                    viewBinding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                    viewBinding.recyclerView.adapter = adapterTrack
                    viewBinding.recyclerView.scrollToPosition(0)
                }
                R.id.chipArtist -> {
                    viewBinding.recyclerView.layoutManager = StaggeredGridLayoutManager(2
                            , StaggeredGridLayoutManager.VERTICAL)
                    viewBinding.recyclerView.adapter = adapterArtist
                    viewBinding.recyclerView.scrollToPosition(0)
                }
                R.id.chipAlbum -> {
                    viewBinding.recyclerView.layoutManager = StaggeredGridLayoutManager(2
                            , StaggeredGridLayoutManager.VERTICAL)
                    viewBinding.recyclerView.adapter = adapterAlbum
                    viewBinding.recyclerView.scrollToPosition(0)
                }
            }
            val query = viewBinding.searchView.text
            if (!query.isNullOrEmpty()){
                query(viewBinding.chipGroupView.checkedChipId, query.toString())
            }
        }
    }

    private fun query(chipId:Int, query:String){
        initLoadingView(true)
        when(chipId){
            R.id.chipTrack -> viewModel.getTrackSearchResults(query)
            R.id.chipArtist -> viewModel.getArtistSearchResults(query)
            R.id.chipAlbum -> viewModel.getAlbumSearchResults(query)
        }
    }

    private fun initLoadingView(value:Boolean){
        if (value)
            viewBinding.loadingView.visibility = View.VISIBLE
        else
            viewBinding.loadingView.visibility = View.GONE
    }

    private fun initObservers(){
        viewModel.liveDataAudioChart.observe(viewLifecycleOwner, {
            adapterTrack.setData(it)
            initLoadingView(false)
        })
        viewModel.liveDataArtistChart.observe(viewLifecycleOwner,{
            adapterArtist.setData(it)
            initLoadingView(false)
        })
        viewModel.liveDataAlbumChart.observe(viewLifecycleOwner, {
            adapterAlbum.setData(it)
            initLoadingView(false)
        })
    }

    override fun onAudioClick(audio: TrackDeezer) {
        val controller = findNavController()
        val bundle = Bundle().apply { putParcelable(Keys.DEEZER_SONG_KEY, audio) }
        controller.navigate(R.id.dialogDeezerPreview, bundle)
    }

    override fun onArtistClick(artist: ArtistDeezer) {
        val controller = findNavController()
        val bundle = Bundle().apply { putParcelable(Keys.DEEZER_ARTIST_KEY, artist) }
        controller.navigate(R.id.viewArtistDeezerFragment, bundle)
    }

    override fun onAlbumClick(album: AlbumDeezer) {
        val controller = findNavController()
        val bundle = Bundle().apply { putParcelable(Keys.DEEZER_ALBUM_KEY, album) }
        controller.navigate(R.id.viewAlbumDeezerFragment, bundle)
    }
}
