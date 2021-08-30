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
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.data.models.Audio
import com.lebogang.gensysmusic.databinding.FragmentViewPlaylistBinding
import com.lebogang.gensysmusic.room.models.Playlist
import com.lebogang.gensysmusic.service.MusicQueue
import com.lebogang.gensysmusic.ui.MainActivity
import com.lebogang.gensysmusic.ui.adapters.ItemPlaylistSongAdapter
import com.lebogang.gensysmusic.ui.adapters.utils.OnAudioClickListener
import com.lebogang.gensysmusic.utils.Keys
import com.lebogang.gensysmusic.viewmodels.AudioViewModel
import com.lebogang.gensysmusic.viewmodels.PlaylistViewModel
import com.lebogang.gensysmusic.viewmodels.ViewModelFactory

class ViewPlaylistFragment : Fragment(), OnAudioClickListener {
    private val viewBinding: FragmentViewPlaylistBinding by lazy{ FragmentViewPlaylistBinding.inflate(layoutInflater) }
    private val viewModelPlaylist: PlaylistViewModel by lazy {
        ViewModelFactory(requireActivity().application).getPlaylistViewModel() }
    private val viewModelAudio: AudioViewModel by lazy{
        ViewModelFactory(requireActivity().application)
        .getAudioViewModel()}
    private val adapter = ItemPlaylistSongAdapter().apply { listener = this@ViewPlaylistFragment }
    private lateinit var playlist: Playlist

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.playlist_audio_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.clearAllMenu ->{
                viewModelPlaylist.clearAudioData(playlist.id)
                true
            }
            else-> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        playlist = requireArguments().getParcelable(Keys.PLAYLIST_KEY)!!
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        populateViews()
    }

    private fun initRecyclerView(){
        viewBinding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewBinding.recyclerView.adapter = adapter
        viewBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when(newState){
                    RecyclerView.SCROLL_STATE_DRAGGING ->{
                        MusicQueue.currentAudio.value?.let {
                            if (viewBinding.targetView.visibility != View.VISIBLE)
                                viewBinding.targetView.visibility = View.VISIBLE
                        }
                    }
                    RecyclerView.SCROLL_STATE_IDLE ->{
                        if (viewBinding.targetView.visibility == View.VISIBLE)
                            Handler(Looper.getMainLooper()).postDelayed({
                                viewBinding.targetView.visibility = View.GONE
                            },3000)
                    }
                }
            }
        })
        viewBinding.targetView.setOnClickListener {
            MusicQueue.currentAudio.value?.let {
                viewBinding.recyclerView.scrollToPosition(adapter.nowPlayingIndex(it.id))
            }
        }
    }

    private fun populateViews(){
        viewBinding.titleView.text = playlist.title
        viewModelPlaylist.getPlaylistAudio(playlist.id)
        viewModelPlaylist.liveDataAudioIds.observe(viewLifecycleOwner,{
            viewModelAudio.getAudio(it)
        })
        viewModelAudio.liveData.observe(viewLifecycleOwner,{
            adapter.setAudioData(it)
            loadingView(it.isNotEmpty())
        })
        MusicQueue.currentAudio.observe(viewLifecycleOwner,{ adapter.setNowPlaying(it.id) })
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
     * Remove audio from playlist
     * */
    override fun onAudioClickOptions(audio: Audio) {
        viewModelPlaylist.deletePlaylistAudio(playlist.id, audio.id)
    }
}
