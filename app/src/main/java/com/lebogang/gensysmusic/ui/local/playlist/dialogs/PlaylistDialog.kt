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
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.lebogang.gensysmusic.GensysApp
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.database.local.models.Playlist
import com.lebogang.gensysmusic.ui.utils.ModelFactory
import com.lebogang.gensysmusic.ui.utils.DialogStyle
import com.lebogang.gensysmusic.ui.local.viewmodel.PlaylistViewModel
import com.lebogang.gensysmusic.utils.Keys

class PlaylistDialog: DialogFragment() {
    var isUpdating:Boolean = false
    var playlist: Playlist? = null
    private val playlistViewModel: PlaylistViewModel by lazy {
        ModelFactory(requireActivity().application as GensysApp).getPlaylistViewModel()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playlist = playlist?:savedInstanceState?.getParcelable(Keys.PLAYLIST_KEY)
        isUpdating = savedInstanceState?.getBoolean("update")?:false
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext()).apply {
            background = DialogStyle.getDialogBackground(requireContext())
            setNegativeButton(getString(R.string.close),null)
            setPositiveButton(getString(R.string.save),getListener())
            setView(R.layout.dialog_playlist)
        }.create()
    }

    override fun onStart() {
        super.onStart()
        if (isUpdating)
            dialog?.findViewById<TextInputEditText>(R.id.titleEditText)?.setText(playlist?.title)
    }

    private fun getListener(): DialogInterface.OnClickListener = DialogInterface.OnClickListener { _, _ ->
        val title = this.dialog?.findViewById<TextInputEditText>(R.id.titleEditText)?.text?.toString()
        if (isUpdating){
            if (!title.isNullOrBlank()){
                playlist?.title
                update(playlist!!)
            }else
                Toast.makeText(
                    requireContext(),
                    getString(R.string.invalid_input),
                    Toast.LENGTH_SHORT
                ).show()
        }else{
            if (!title.isNullOrBlank()){
                val playlist = Playlist(0, title)
                update(playlist)
            }else
                Toast.makeText(requireContext(), getString(R.string.invalid_input), Toast.LENGTH_SHORT).show()
        }
    }

    private fun update(playlist: Playlist){
        playlistViewModel.addPlaylist(playlist)
        Toast.makeText(requireContext(), getString(R.string.playlist_updated), Toast.LENGTH_SHORT).show()
        dismiss()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(Keys.PLAYLIST_KEY,playlist)
        outState.putBoolean("update",isUpdating)
        super.onSaveInstanceState(outState)
    }

}
