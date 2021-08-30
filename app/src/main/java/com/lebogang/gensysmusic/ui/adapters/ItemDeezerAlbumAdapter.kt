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
import com.lebogang.gensysmusic.databinding.ItemOnlineDeezerAlbumBinding
import com.lebogang.gensysmusic.network.models.AlbumDeezer
import com.lebogang.gensysmusic.ui.adapters.utils.OnDeezerAlbumClickListener
import com.lebogang.gensysmusic.ui.helpers.ThemeHelper
import com.lebogang.gensysmusic.utils.GlideManager

class ItemDeezerAlbumAdapter:RecyclerView.Adapter<ItemDeezerAlbumAdapter.ViewHolder>() {
    private var listAlbum = listOf<AlbumDeezer>()
    var listener:OnDeezerAlbumClickListener? = null

    fun setData(list:List<AlbumDeezer>){
        listAlbum = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemOnlineDeezerAlbumBinding.inflate(inflater, parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album = listAlbum[position]
        holder.viewBinding.titleView.text = album.title
        holder.viewBinding.subtitleView.text = album.artist.title
        if (album.hasExplicitLyrics)
            holder.viewBinding.explicitView.setTextColor(ThemeHelper.PRIMARY_COLOR)
        else
            holder.viewBinding.explicitView.setTextColor(ThemeHelper.SECONDARY_TEXTCOLOR_NO_DISABLE)
        GlideManager(holder.itemView).loadOnline(album.coverMedium, holder.viewBinding.imageView)
    }

    override fun getItemCount(): Int {
        return listAlbum.size
    }

    inner class ViewHolder(val viewBinding:ItemOnlineDeezerAlbumBinding):RecyclerView.ViewHolder(viewBinding.root){
        init {
            viewBinding.root.setOnClickListener {
                listener?.onAlbumClick(listAlbum[adapterPosition])
            }
        }
    }
}
