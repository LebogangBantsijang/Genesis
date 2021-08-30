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
import androidx.recyclerview.widget.LinearLayoutManager
import com.lebogang.gensysmusic.data.models.Audio
import com.lebogang.gensysmusic.databinding.DialogQueueBinding
import com.lebogang.gensysmusic.service.MusicQueue
import com.lebogang.gensysmusic.ui.MainActivity
import com.lebogang.gensysmusic.ui.adapters.ItemQueueSongAdapter
import com.lebogang.gensysmusic.ui.adapters.utils.OnAudioClickListener

class QueueDialog: DialogFragment(), OnAudioClickListener {
    private val viewBinding:DialogQueueBinding by lazy { DialogQueueBinding.inflate(layoutInflater) }
    private val adapter = ItemQueueSongAdapter().apply { listener = this@QueueDialog }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?
                              , savedInstanceState: Bundle?): View {
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews(){
        viewBinding.cancelView.setOnClickListener { dismiss() }
        viewBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        viewBinding.recyclerView.adapter = adapter
        adapter.setAudioData(MusicQueue.audioQueue)
        viewBinding.counterView.text = MusicQueue.audioQueue.size.toString()
        MusicQueue.currentAudio.observe(viewLifecycleOwner,{
            adapter.setNowPlaying(it.id)
            viewBinding.recyclerView.scrollToPosition(adapter.getNowPlayingIndex())
        })
    }

    override fun onAudioClick(audio: Audio) {
        (requireActivity() as MainActivity).playAudio(audio, null)
    }

    override fun onAudioClickOptions(audio: Audio) {
        MusicQueue.removeAudio(audio)
        adapter.setAudioData(MusicQueue.audioQueue)
        viewBinding.counterView.text = MusicQueue.audioQueue.size.toString()
    }
}
