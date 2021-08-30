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

package com.lebogang.gensysmusic.ui.history.dialogs

import android.app.Dialog
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.database.history.models.History
import com.lebogang.gensysmusic.ui.utils.DialogStyle
import com.lebogang.gensysmusic.ui.utils.ImageLoader
import com.lebogang.gensysmusic.ui.utils.Type

class HistoryDialog:DialogFragment(){
    var history:History? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        history = history?: savedInstanceState?.getParcelable("history")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext()).apply {
            background = DialogStyle.getDialogBackground(requireContext())
            setPositiveButton(getString(R.string.close),null)
            setView(R.layout.dialog_history)
        }.create()
    }

    override fun onStart() {
        super.onStart()
        initViews()
    }

    private fun initViews(){
        dialog?.findViewById<ImageView>(R.id.imageView)?.let {
            ImageLoader(requireActivity()).loadImage(history?.artUri, Type.MUSIC,it)
        }
        dialog?.findViewById<TextView>(R.id.titleTextView)?.text = history?.title
        dialog?.findViewById<TextView>(R.id.artistTextView)?.text = history?.artist
        dialog?.findViewById<TextView>(R.id.albumTextView)?.text = history?.album
        dialog?.findViewById<TextView>(R.id.releaseTextView)?.text = history?.releaseDate
        dialog?.findViewById<TextView>(R.id.playedTextView)?.text = history?.date
        dialog?.findViewById<TextView>(R.id.playCount)?.text = history?.playCount.toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable("history",history)
        super.onSaveInstanceState(outState)
    }

}
