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
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.lebogang.gensysmusic.data.models.Audio
import com.lebogang.gensysmusic.databinding.ItemLocalSongBinding
import com.lebogang.gensysmusic.ui.adapters.utils.AdapterInterface
import com.lebogang.gensysmusic.ui.adapters.utils.OnAudioClickListener
import com.lebogang.gensysmusic.ui.helpers.ThemeHelper
import com.lebogang.gensysmusic.utils.GlideManager

class ItemSongAdapter:RecyclerView.Adapter<ItemSongAdapter.ViewHolder>(), Filterable, AdapterInterface {
    var listener:OnAudioClickListener? = null
    private var listAudio = mutableListOf<Audio>()
    private val filteredList = mutableListOf<Audio>()
    var audioId:Long = -1
    private var isUserSearching = false

    override fun setAudioData(mutableList: MutableList<Audio>){
        isUserSearching = false
        filteredList.clear()
        listAudio = mutableList
        notifyDataSetChanged()
    }


    override fun setNowPlaying(audioId:Long){
        this.audioId = audioId
        notifyDataSetChanged()
    }

    fun getList():MutableList<Audio>{
        if (isUserSearching)
            return filteredList
        return listAudio
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
        val viewBinding = ItemLocalSongBinding.inflate(inflater, parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val audio = if(!isUserSearching) listAudio[position] else filteredList[position]
        val subtitle = audio.artist + "-" + audio.album
        holder.viewBinding.titleView.text = audio.title
        holder.viewBinding.subtitleView.text = subtitle
        holder.viewBinding.durationView.text = audio.durationFormatted
        GlideManager(holder.itemView).loadAudioArt(audio.getArtUri(), holder.viewBinding.imageView)
        updateNowPlaying(holder, audio)
    }

    private fun updateNowPlaying(holder: ViewHolder, audio: Audio){
        if (audio.id == audioId){
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
        if (isUserSearching)
            return filteredList.size
        return listAudio.size
    }

    inner class ViewHolder(val viewBinding:ItemLocalSongBinding)
        :RecyclerView.ViewHolder(viewBinding.root){
        init {
            viewBinding.root.setOnClickListener {
                listener?.onAudioClick(getItem())
            }
            viewBinding.optionsView.setOnClickListener {
                listener?.onAudioClickOptions(getItem()) }
            viewBinding.root.setOnLongClickListener {
                listener?.onAudioClickOptions(getItem())
                true
            }
        }

        private fun getItem():Audio{
            return when(isUserSearching){
                true -> filteredList[adapterPosition]
                else -> listAudio[adapterPosition]
            }
        }
    }

    override fun getFilter(): Filter {
        return object:Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                isUserSearching = false
                constraint?.let {
                    isUserSearching = true
                    filteredList.clear()
                    for (x in 0 until listAudio.size){
                        val audio = listAudio[x]
                        if (audio.title.toLowerCase().contains(it.toString().toLowerCase())){
                            filteredList.add(audio)
                        }
                    }
                }
                return FilterResults()
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                notifyDataSetChanged()
            }

        }
    }
}
