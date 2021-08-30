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

package com.lebogang.gensysmusic.ui.local.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lebogang.gensysmusic.GensysApp
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.database.local.models.Music
import com.lebogang.gensysmusic.database.local.models.Playlist
import com.lebogang.gensysmusic.ui.utils.DialogStyle
import com.lebogang.gensysmusic.ui.utils.ItemClickInterface
import com.lebogang.gensysmusic.ui.utils.Type
import com.lebogang.gensysmusic.ui.local.adapters.PlaylistAdapter
import com.lebogang.gensysmusic.ui.utils.ModelFactory
import com.lebogang.gensysmusic.ui.local.viewmodel.PlaylistViewModel
import com.lebogang.gensysmusic.utils.Keys

class AddToPlaylistDialog: DialogFragment() {
    var music: Music? = null
    private val playlistViewModel: PlaylistViewModel by lazy {
        ModelFactory(requireActivity().application as GensysApp).getPlaylistViewModel()}
    private val adapter = PlaylistAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        music = music?:savedInstanceState?.getParcelable(Keys.MUSIC_KEY)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext()).apply {
            background = DialogStyle.getDialogBackground(requireContext())
            setPositiveButton(getString(R.string.close),null)
            setView(R.layout.dialog_add_audio_to_playlist)
        }.create()
    }

    override fun onStart() {
        super.onStart()
        initRecyclerView()
    }

    private fun initRecyclerView(){
        adapter.itemClickInterface = getItemClickInterface()
        adapter.showOptions = false
        playlistViewModel.getPlaylists().observe(requireActivity(),{
            adapter.setData(it)
        })
        val recyclerView = dialog?.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView?.layoutManager= LinearLayoutManager(requireContext())
        recyclerView?.adapter = adapter
    }

    private fun getItemClickInterface(): ItemClickInterface = object : ItemClickInterface {
        override fun onItemClick(view: View, item: Any?, type: Type) {
            music?.let {
                val playlist = item as Playlist
                playlistViewModel.addPlaylistMembers(Playlist.Members(0,playlist.id,it.id))
                Toast.makeText(requireContext(),getString(R.string.items_added),Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(Keys.MUSIC_KEY,music)
        super.onSaveInstanceState(outState)
    }
}
