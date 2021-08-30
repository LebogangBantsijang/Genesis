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

package com.lebogang.gensysmusic.ui.local.playlist.dialogs

import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lebogang.gensysmusic.GensysApp
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.database.local.models.Playlist
import com.lebogang.gensysmusic.ui.utils.ModelFactory
import com.lebogang.gensysmusic.ui.utils.DialogStyle
import com.lebogang.gensysmusic.ui.local.viewmodel.PlaylistViewModel
import com.lebogang.gensysmusic.utils.Keys

class PlaylistOptionsDialog: DialogFragment() {
    var playlist: Playlist? = null
    private val playlistViewModel: PlaylistViewModel by lazy {
        ModelFactory(requireActivity().application as GensysApp).getPlaylistViewModel()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playlist = playlist?:savedInstanceState?.getParcelable(Keys.PLAYLIST_KEY)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext()).apply {
            background = DialogStyle.getDialogBackground(requireContext())
            setPositiveButton(getString(R.string.close),null)
            setView(R.layout.dialog_playlist_options)
        }.create()
    }

    override fun onStart() {
        super.onStart()
        initViews()
    }

    private fun initViews(){
        dialog?.findViewById<TextView>(R.id.titleTextView)?.text = playlist?.title
        dialog?.findViewById<TextView>(R.id.updateView)?.setOnClickListener {
            playlist?.let {
                val playlistDialog = PlaylistDialog()
                playlistDialog.playlist = it
                playlistDialog.isUpdating = true
                playlistDialog.showNow(requireActivity().supportFragmentManager,null)
                dismiss()
            }
        }
        dialog?.findViewById<TextView>(R.id.deleteView)?.setOnClickListener {
            playlist?.let {
                playlistViewModel.delete(it)
                Toast.makeText(
                    requireContext(),
                    getString(R.string.playlist_deleted),
                    Toast.LENGTH_SHORT
                ).show()
                dismiss()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(Keys.PLAYLIST_KEY,playlist)
        super.onSaveInstanceState(outState)
    }
}
