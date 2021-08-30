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

package com.lebogang.gensysmusic.ui.local.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.database.local.models.Album
import com.lebogang.gensysmusic.databinding.ItemAlbumBinding
import com.lebogang.gensysmusic.ui.utils.*

class AlbumAdapter:RecyclerView.Adapter<AlbumAdapter.Holder>() {
    private val asyncListDiffer = AsyncListDiffer(this,DiffUtilAlbum)
    lateinit var imageLoader: ImageLoader
    lateinit var itemOptionsInterface: ItemOptionsInterface
    lateinit var itemClickInterface: ItemClickInterface

    fun setData(list: List<Album>) = asyncListDiffer.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val bind = ItemAlbumBinding.inflate(inflater, parent, false)
        return Holder(bind)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val album = asyncListDiffer.currentList[position]
        holder.bind.titleTextView.text = album.getItemTitle()
        holder.bind.artistTextView.text = album.getItemArtist()
        holder.bind.durationTextView.text = album.getItemDuration()
        holder.bind.sizeTextView.text = album.getItemSize()
        imageLoader.loadImage(album.getItemArt(), Type.ALBUM, holder.bind.imageView)
        if (album.getIsItemFavourite())
            holder.bind.favouriteImageView.setImageResource(R.drawable.ic_melting_heart_filled_ios)
        else
            holder.bind.favouriteImageView.setImageResource(R.drawable.ic_melting_heart_ios)
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    inner class Holder(val bind:ItemAlbumBinding):RecyclerView.ViewHolder(bind.root){
        init {
            bind.favouriteImageView.setOnClickListener { itemOptionsInterface
                .onOptionsClick(asyncListDiffer.currentList[bindingAdapterPosition]) }
            bind.root.setOnClickListener { itemClickInterface.onItemClick(itemView
                ,asyncListDiffer.currentList[bindingAdapterPosition], Type.ALBUM) }
        }
    }
}
