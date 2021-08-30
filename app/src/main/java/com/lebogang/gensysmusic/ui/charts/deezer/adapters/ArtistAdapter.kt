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

package com.lebogang.gensysmusic.ui.charts.deezer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.lebogang.gensysmusic.databinding.ItemArtistOnlineBinding
import com.lebogang.gensysmusic.online.deezer.models.Artist
import com.lebogang.gensysmusic.ui.utils.DiffUtilArtist
import com.lebogang.gensysmusic.ui.utils.ImageLoader
import com.lebogang.gensysmusic.ui.utils.ItemClickInterface
import com.lebogang.gensysmusic.ui.utils.Type

class ArtistAdapter:RecyclerView.Adapter<ArtistAdapter.Holder>() {
    private val asyncListDiffer = AsyncListDiffer(this, DiffUtilArtist)
    lateinit var imageLoader: ImageLoader
    lateinit var itemClickInterface: ItemClickInterface

    fun setData(list: List<Artist>) = asyncListDiffer.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val bind = ItemArtistOnlineBinding.inflate(inflater, parent, false)
        return Holder(bind)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val artist = asyncListDiffer.currentList[position]
        holder.bind.titleView.text = artist.getItemTitle()
        imageLoader.loadImage(artist.getItemArt(), Type.ARTIST,holder.bind.imageView)
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    inner class Holder(val bind:ItemArtistOnlineBinding):RecyclerView.ViewHolder(bind.root){
        init {
            itemView.setOnClickListener { itemClickInterface
                .onItemClick(itemView,asyncListDiffer.currentList[bindingAdapterPosition], Type.ARTIST) }
        }
    }
}
