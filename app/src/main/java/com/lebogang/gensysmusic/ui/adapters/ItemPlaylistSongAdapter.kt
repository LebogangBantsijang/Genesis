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
import com.lebogang.gensysmusic.databinding.ItemLocalPlaylistSongBinding
import com.lebogang.gensysmusic.ui.adapters.utils.AdapterInterface
import com.lebogang.gensysmusic.ui.adapters.utils.OnAudioClickListener
import com.lebogang.gensysmusic.ui.helpers.ThemeHelper
import com.lebogang.gensysmusic.utils.GlideManager

class ItemPlaylistSongAdapter:RecyclerView.Adapter<ItemPlaylistSongAdapter.Holder>(),AdapterInterface{
    private var audioId:Long = -1
    var listAudio = mutableListOf<Audio>()
    var listener:OnAudioClickListener? = null

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemLocalPlaylistSongBinding.inflate(inflater, parent, false)
        return Holder(viewBinding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val audio = listAudio[position]
        val subtitle = audio.artist + "-" + audio.album
        holder.viewBinding.titleView.text = audio.title
        holder.viewBinding.subtitleView.text = subtitle
        holder.viewBinding.durationView.text = audio.durationFormatted
        GlideManager(holder.itemView).loadAudioArt(audio.getArtUri(), holder.viewBinding.imageView)
        updateNowPlaying(holder, audio)
    }

    private fun updateNowPlaying(holder: Holder, audio: Audio){
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

    inner class Holder(val viewBinding:ItemLocalPlaylistSongBinding):RecyclerView.ViewHolder(viewBinding.root){
        init {
            viewBinding.root.setOnClickListener {
                listener?.onAudioClick(listAudio[adapterPosition]) }
            viewBinding.deleteView.setOnClickListener {
                listener?.onAudioClickOptions(listAudio[adapterPosition]) }
        }
    }
}
