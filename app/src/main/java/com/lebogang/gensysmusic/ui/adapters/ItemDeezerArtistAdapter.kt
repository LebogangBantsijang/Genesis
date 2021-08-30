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
import com.lebogang.gensysmusic.databinding.ItemOnlineDeezerArtistBinding
import com.lebogang.gensysmusic.network.models.ArtistDeezer
import com.lebogang.gensysmusic.ui.adapters.utils.OnDeezerArtistClickListener
import com.lebogang.gensysmusic.utils.GlideManager

class ItemDeezerArtistAdapter:RecyclerView.Adapter<ItemDeezerArtistAdapter.ViewHolder>() {
    private var listArtist = listOf<ArtistDeezer>()
    var listener:OnDeezerArtistClickListener? = null

    fun setData(list:List<ArtistDeezer>){
        listArtist = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemOnlineDeezerArtistBinding.inflate(inflater, parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val artist = listArtist[position]
        holder.viewBinding.titleView.text = artist.title
        GlideManager(holder.itemView).loadOnline(artist.coverMedium, holder.viewBinding.imageView)
    }

    override fun getItemCount(): Int {
        return listArtist.size
    }

    inner class ViewHolder(val viewBinding:ItemOnlineDeezerArtistBinding):RecyclerView.ViewHolder(viewBinding.root){
        init {
            viewBinding.root.setOnClickListener {
                listener?.onArtistClick(listArtist[adapterPosition])
            }
        }
    }
}
