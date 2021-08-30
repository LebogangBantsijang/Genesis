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
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.databinding.FragmentPlaylistBinding
import com.lebogang.gensysmusic.room.models.Playlist
import com.lebogang.gensysmusic.ui.adapters.ItemPlaylistAdapter
import com.lebogang.gensysmusic.ui.adapters.utils.OnPlaylistClickListener
import com.lebogang.gensysmusic.utils.Keys
import com.lebogang.gensysmusic.viewmodels.PlaylistViewModel
import com.lebogang.gensysmusic.viewmodels.ViewModelFactory

class PlaylistFragment: Fragment(), OnPlaylistClickListener {
    private val viewBinding: FragmentPlaylistBinding by lazy{ FragmentPlaylistBinding.inflate(layoutInflater) }
    private val viewModel: PlaylistViewModel by lazy{
        ViewModelFactory(requireActivity().application).getPlaylistViewModel() }
    private val adapter = ItemPlaylistAdapter().apply { listener = this@PlaylistFragment}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        populateViews()
    }

    private fun initRecyclerView(){
        viewBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        viewBinding.recyclerView.adapter = adapter
    }

    private fun populateViews(){
        viewModel.liveData.observe(viewLifecycleOwner, {
            //set playlist data to adapter
            adapter.setPlaylistData(it)
            //show/hide loading view
            loadingView(it.isNotEmpty())
            //display total
            val count = getString(R.string.total) + " " + it.size.toString()
            viewBinding.counterView.text = count
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.playlists_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.addMenu ->{
                val controller = findNavController()
                controller.navigate(R.id.addPlaylistDialog)
                true
            }
            R.id.clearMenu ->{
                viewModel.clearData()
                true
            }
            else-> false
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
     * Navigate to playlist view fragment
     * @param playlist: playlist to view
     * */
    override fun onPlaylistClick(playlist: Playlist) {
        val bundle = Bundle().apply { putParcelable(Keys.PLAYLIST_KEY, playlist) }
        val controller = findNavController()
        controller.navigate(R.id.viewPlaylistFragment, bundle)
    }

    /**
     * Open that playlist edit dialog
     * @param playlist: playlist to edit
     * */
    override fun onPlaylistEditClick(playlist: Playlist) {
        val bundle = Bundle().apply { putParcelable(Keys.PLAYLIST_KEY, playlist) }
        val controller = findNavController()
        controller.navigate(R.id.updatePlaylistDialog, bundle)
    }

    /**
     * Remove a playlist from room
     * @param playlist: Playlist to remove
     * */
    override fun onPlaylistDeleteClick(playlist: Playlist) {
        viewModel.deletePlaylist(playlist)
    }

}
