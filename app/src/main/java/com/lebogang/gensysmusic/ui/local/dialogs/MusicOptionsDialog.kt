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

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lebogang.gensysmusic.GensysApp
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.database.local.models.Music
import com.lebogang.gensysmusic.ui.utils.DialogStyle
import com.lebogang.gensysmusic.ui.local.AlbumDetailsActivity
import com.lebogang.gensysmusic.ui.local.ArtistDetailsActivity
import com.lebogang.gensysmusic.ui.local.EditActivity
import com.lebogang.gensysmusic.ui.local.InformationActivity
import com.lebogang.gensysmusic.ui.utils.ModelFactory
import com.lebogang.gensysmusic.ui.local.viewmodel.MusicViewModel
import com.lebogang.gensysmusic.utils.Keys

class MusicOptionsDialog :DialogFragment(){
    var music:Music? = null
    var showExtraOption :Boolean = true
    private val musicViewModel: MusicViewModel by lazy {
        ModelFactory(requireActivity().application as GensysApp).getMusicViewModel()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        music = music?:savedInstanceState?.getParcelable(Keys.MUSIC_KEY)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext()).apply {
            background = DialogStyle.getDialogBackground(requireContext())
            setPositiveButton(getString(R.string.close),null)
            setView(R.layout.dialog_music_options)
        }.create()
    }

    override fun onStart() {
        super.onStart()
        initViews()
    }

    @SuppressLint("SetTextI18n")
    private fun initViews(){
        music?.let{
            dialog?.findViewById<TextView>(R.id.titleTextView)?.text = it.title
            dialog?.findViewById<TextView>(R.id.subtitleView)?.text = it.artist
            if (showExtraOption){
                dialog?.findViewById<MaterialButton>(R.id.albumView)?.text =
                    getString(R.string.view_album) + ":" + it.album
                dialog?.findViewById<MaterialButton>(R.id.artistView)?.text =
                    getString(R.string.view_artist) + ":" + it.artist
            }else{
                dialog?.findViewById<MaterialButton>(R.id.albumView)?.visibility = View.GONE
                dialog?.findViewById<MaterialButton>(R.id.artistView)?.visibility = View.GONE
            }
            initOnClicks(it)
        }
    }

    private fun initOnClicks(music: Music){
        dialog?.findViewById<MaterialButton>(R.id.playlistView)?.setOnClickListener {
            val addToPlaylistDialog = AddToPlaylistDialog()
            addToPlaylistDialog.music = music
            addToPlaylistDialog.showNow(requireActivity().supportFragmentManager,null)
            dismiss()
        }
        dialog?.findViewById<MaterialButton>(R.id.favouriteView)?.setOnClickListener {
            music.isFavourite = true
            musicViewModel.addMusic(music)
            dismiss()
            Toast.makeText(requireContext(),
                getString(R.string.added_to_favourites),Toast.LENGTH_SHORT).show()
        }
        dialog?.findViewById<MaterialButton>(R.id.infoView)?.setOnClickListener {
            val intent = Intent(requireContext(),InformationActivity::class.java)
            intent.putExtra(Keys.MUSIC_KEY, music)
            startActivity(intent)
            dismiss()
        }
        if (showExtraOption){
            dialog?.findViewById<MaterialButton>(R.id.albumView)?.setOnClickListener {
                val intent = Intent(requireContext(),AlbumDetailsActivity::class.java)
                intent.putExtra(Keys.ALBUM_KEY, music.albumId)
                startActivity(intent)
                dismiss()
            }
            dialog?.findViewById<MaterialButton>(R.id.artistView)?.setOnClickListener {
                val intent = Intent(requireContext(),ArtistDetailsActivity::class.java)
                intent.putExtra(Keys.ARTIST_KEY, music.artistId)
                startActivity(intent)
                dismiss()
            }
        }
        dialog?.findViewById<TextView>(R.id.editView)?.setOnClickListener {
            val intent = Intent(requireContext(),EditActivity::class.java)
            intent.putExtra(Keys.MUSIC_KEY, music)
            startActivity(intent)
            dismiss()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(Keys.MUSIC_KEY,music)
        super.onSaveInstanceState(outState)
    }
}
