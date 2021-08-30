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
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.lebogang.gensysmusic.database.local.models.Playlist
import com.lebogang.gensysmusic.databinding.ItemPlaylistBinding
import com.lebogang.gensysmusic.ui.utils.DiffUtilPlaylist
import com.lebogang.gensysmusic.ui.utils.ItemClickInterface
import com.lebogang.gensysmusic.ui.utils.ItemOptionsInterface
import com.lebogang.gensysmusic.ui.utils.Type

class PlaylistAdapter :RecyclerView.Adapter<PlaylistAdapter.Holder>(){
    private val asyncListDiffer = AsyncListDiffer(this,DiffUtilPlaylist)
    var showOptions:Boolean = true
    lateinit var itemOptionsInterface: ItemOptionsInterface
    lateinit var itemClickInterface: ItemClickInterface

    fun setData(list: List<Playlist>) = asyncListDiffer.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val bind = ItemPlaylistBinding.inflate(inflater,parent,false)
        if (!showOptions)
            bind.imageButton.visibility = View.GONE
        return Holder(bind)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val playlist = asyncListDiffer.currentList[position]
        holder.bind.titleTextView.text = playlist.title
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    inner class Holder(val bind:ItemPlaylistBinding):RecyclerView.ViewHolder(bind.root){
        init {
            if (showOptions){
                bind.imageButton.setOnClickListener {
                    itemOptionsInterface.onOptionsClick(
                        asyncListDiffer.currentList[bindingAdapterPosition])
                }
            }
            itemView.setOnClickListener {
                itemClickInterface.onItemClick(it,
                    asyncListDiffer.currentList[bindingAdapterPosition], Type.PLAYLIST)
            }
        }
    }
}
