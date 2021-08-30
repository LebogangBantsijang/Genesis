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
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lebogang.gensysmusic.data.models.Audio
import com.lebogang.gensysmusic.databinding.ItemLocalAlbumArtistSongBinding
import com.lebogang.gensysmusic.ui.adapters.utils.AdapterInterface
import com.lebogang.gensysmusic.ui.adapters.utils.OnAudioClickListener
import com.lebogang.gensysmusic.ui.helpers.ThemeHelper

class ItemAlbumArtistSongAdapter :RecyclerView.Adapter<ItemAlbumArtistSongAdapter.ViewHolder>(), AdapterInterface{
    var listener: OnAudioClickListener? = null
    var listAudio = mutableListOf<Audio>()
    var audioId:Long = -3

    override fun setAudioData(mutableList: MutableList<Audio>){
        listAudio = mutableList
        notifyDataSetChanged()
    }

    override fun setNowPlaying(audioId:Long){
        this.audioId = audioId
        notifyDataSetChanged()
    }

    fun nowPlayingIndex(audioId: Long):Int{
        listAudio.forEach {
            if (it.id == audioId){
                return listAudio.indexOf(it)
            }
        }
        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemLocalAlbumArtistSongBinding.inflate(inflater, parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val audio = listAudio[position]
        val subtitle = audio.artist + "-" + audio.album
        val counter = (1+position).toString()
        holder.viewBinding.titleView.text = audio.title
        holder.viewBinding.subtitleView.text = subtitle
        holder.viewBinding.counterView.text = counter
        holder.viewBinding.durationView.text = audio.durationFormatted
        updateNowPlaying(holder, audio)
    }

    private fun updateNowPlaying(holder: ViewHolder, audio: Audio){
        if (audio.id == audioId) {
            holder.viewBinding.lottieView.visibility = View.VISIBLE
            holder.viewBinding.titleView.setTextColor(ThemeHelper.PRIMARY_COLOR)
            holder.viewBinding.subtitleView.setTextColor(ThemeHelper.PRIMARY_COLOR)
        }
        else{
            holder.viewBinding.lottieView.visibility = View.GONE
            holder.viewBinding.titleView.setTextColor(ThemeHelper.PRIMARY_TEXTCOLOR)
            holder.viewBinding.subtitleView.setTextColor(ThemeHelper.SECONDARY_TEXTCOLOR)
        }
    }

    override fun getItemCount(): Int {
        return listAudio.size
    }

    inner class ViewHolder(val viewBinding: ItemLocalAlbumArtistSongBinding)
        :RecyclerView.ViewHolder(viewBinding.root){
        init {
            viewBinding.root.setOnClickListener {
                listener?.onAudioClick(listAudio[adapterPosition]) }
            viewBinding.optionsView.setOnClickListener {
                listener?.onAudioClickOptions(listAudio[adapterPosition]) }
            viewBinding.root.setOnLongClickListener {
                listener?.onAudioClickOptions(listAudio[adapterPosition])
                true
            }
        }
    }
}
