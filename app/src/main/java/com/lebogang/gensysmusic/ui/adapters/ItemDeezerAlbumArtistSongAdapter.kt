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

package com.lebogang.gensysmusic.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lebogang.gensysmusic.databinding.ItemOnlineDeezerAlbumArtistSongBinding
import com.lebogang.gensysmusic.network.models.TrackDeezer
import com.lebogang.gensysmusic.ui.adapters.utils.OnDeezerAudioClickListener
import com.lebogang.gensysmusic.ui.helpers.ThemeHelper

class ItemDeezerAlbumArtistSongAdapter:RecyclerView.Adapter<ItemDeezerAlbumArtistSongAdapter.ViewHolder>() {
    private var listDeezer:List<TrackDeezer> = listOf()
    var listener:OnDeezerAudioClickListener? = null

    fun setData(list: List<TrackDeezer>){
        listDeezer = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemOnlineDeezerAlbumArtistSongBinding.inflate(inflater,parent, false)
        return ViewHolder(viewBinding)
    }

    @Suppress("DEPRECATION")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val track = listDeezer[position]
        holder.viewBinding.titleView.text = track.title
        holder.viewBinding.subtitleView.text = track.artist.title
        if (track.hasExplicitLyrics)
            holder.viewBinding.explicitView.setTextColor(ThemeHelper.PRIMARY_COLOR)
        else
            holder.viewBinding.explicitView.setTextColor(ThemeHelper.SECONDARY_TEXTCOLOR_NO_DISABLE)
    }

    override fun getItemCount(): Int {
        return listDeezer.size
    }

    inner class ViewHolder(val viewBinding:ItemOnlineDeezerAlbumArtistSongBinding)
        :RecyclerView.ViewHolder(viewBinding.root){
        init {
            viewBinding.root.setOnClickListener {
                listener?.onAudioClick(listDeezer[adapterPosition])
            }
        }
    }
}
