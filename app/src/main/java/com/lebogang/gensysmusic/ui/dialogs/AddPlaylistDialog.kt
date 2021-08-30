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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.lebogang.gensysmusic.databinding.DialogAddPlaylistBinding
import com.lebogang.gensysmusic.room.models.Playlist
import com.lebogang.gensysmusic.viewmodels.PlaylistViewModel
import com.lebogang.gensysmusic.viewmodels.ViewModelFactory

class AddPlaylistDialog:DialogFragment() {
    private val viewBinding:DialogAddPlaylistBinding by lazy{DialogAddPlaylistBinding.inflate(layoutInflater)}
    private val playlistViewModel:PlaylistViewModel by lazy {
        ViewModelFactory(requireActivity().application).getPlaylistViewModel() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSaveView()
    }

    private fun initSaveView(){
        viewBinding.saveView.setOnClickListener {
            val name = viewBinding.playlistNameView.text
            if (!name.isNullOrEmpty()){
                playlistViewModel.insertPlaylist(Playlist(0, name.toString()))
                dismiss()
            }
        }
    }
}
