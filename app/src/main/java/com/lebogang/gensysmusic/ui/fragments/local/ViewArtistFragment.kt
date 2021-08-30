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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.data.models.Artist
import com.lebogang.gensysmusic.data.models.Audio
import com.lebogang.gensysmusic.databinding.FragmentArtistViewBinding
import com.lebogang.gensysmusic.service.MusicQueue
import com.lebogang.gensysmusic.ui.MainActivity
import com.lebogang.gensysmusic.ui.adapters.ItemAlbumArtistSongAdapter
import com.lebogang.gensysmusic.ui.adapters.utils.OnAudioClickListener
import com.lebogang.gensysmusic.utils.Keys
import com.lebogang.gensysmusic.utils.GlideManager
import com.lebogang.gensysmusic.viewmodels.AudioViewModel
import com.lebogang.gensysmusic.viewmodels.ViewModelFactory

class ViewArtistFragment: Fragment(), OnAudioClickListener {
    private val viewBinding: FragmentArtistViewBinding by lazy{ FragmentArtistViewBinding.inflate(layoutInflater) }
    private val viewModelAudio: AudioViewModel by lazy {
        ViewModelFactory(requireActivity().application)
            .getAudioViewModel()}
    private val adapter = ItemAlbumArtistSongAdapter().apply { listener = this@ViewArtistFragment }
    private lateinit var artist: Artist

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        artist = requireArguments().getParcelable(Keys.ARTIST_KEY)!!
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        populateViews()
        initButtons()
    }

    private fun initRecyclerView(){
        viewBinding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewBinding.recyclerView.adapter = adapter
    }

    private fun populateViews(){
        viewBinding.titleView.text = artist.title
        GlideManager(this).loadAlbumArt(artist.getArtUri(),viewBinding.artView)
        viewModelAudio.getArtistAudio(artist.title)
        viewModelAudio.liveData.observe(viewLifecycleOwner,{list->
            adapter.setAudioData(list)
            viewBinding.subtitleView.text = list.size.toString()
            loadingView(list.isNotEmpty())
        })
        MusicQueue.currentAudio.observe(viewLifecycleOwner,{ adapter.setNowPlaying(it.id) })
    }

    /**
     * Init that random play button
     * */
    private fun initButtons(){
        viewBinding.randomPlayView.setOnClickListener {
            if (adapter.listAudio.isNotEmpty()){
                val audio = adapter.listAudio[0]
                onAudioClick(audio)
            }
        }
    }

    /**
     * Show or hide loading view
     * */
    private fun loadingView(hasContent:Boolean){
        viewBinding.loadingView.visibility = View.GONE
        if (hasContent){
            viewBinding.noContentView.text = null
        }else{
            viewBinding.noContentView.text = getString(R.string.no_content)
        }
    }

    /**
     * Play audio
     * @param audio: audio to play
     * */
    override fun onAudioClick(audio: Audio) {
        (requireActivity() as MainActivity).playAudio(audio, adapter.listAudio)
    }

    /**
     * Open audio context menu (Audio processing dialog).
     * @param audio: audio for the menu
     * */
    override fun onAudioClickOptions(audio: Audio) {
        val bundle = Bundle().apply{
            putParcelable(Keys.MUSIC_KEY, audio)
            putBoolean(Keys.ENABLE_UPDATE_KEY,false) }
        val controller = findNavController()
        controller.navigate(R.id.audioOptionsDialog, bundle)
    }
}
