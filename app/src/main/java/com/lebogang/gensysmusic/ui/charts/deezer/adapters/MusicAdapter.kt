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

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.databinding.ItemMusicOnlineBinding
import com.lebogang.gensysmusic.online.deezer.models.Music
import com.lebogang.gensysmusic.ui.utils.DiffUtilMusic
import com.lebogang.gensysmusic.ui.utils.ImageLoader
import com.lebogang.gensysmusic.ui.utils.ItemClickInterface
import com.lebogang.gensysmusic.ui.utils.Type

class MusicAdapter :RecyclerView.Adapter<MusicAdapter.Holder>(){
    private val asyncListDiffer = AsyncListDiffer(this, DiffUtilMusic)
    lateinit var imageLoader: ImageLoader
    lateinit var itemClickInterface: ItemClickInterface
    var artUrl:String? = null
    var showMusicNumber:Boolean = true
    @ColorInt private var explicitColor:Int = -1

    fun setData(list: List<Music>) = asyncListDiffer.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        if (explicitColor<0)
            explicitColor = parent.context.getColor(R.color.red)
        val inflater = LayoutInflater.from(parent.context)
        val bind = ItemMusicOnlineBinding.inflate(inflater, parent, false)
        return Holder(bind)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val music = asyncListDiffer.currentList[position]
        holder.bind.titleView.text = music.getItemTitle()
        holder.bind.subtitleView.text = music.getItemArtist()
        holder.bind.albumTitleTextView.text = music.getItemAlbum()
        imageLoader.loadImage(music.getItemArt(), Type.MUSIC,holder.bind.imageView)
        if (showMusicNumber){
            val number = "#: " + (1+ position).toString()
            holder.bind.numberTextView.text = number
        }
        if (music.getItemAlbum() == "Explicit" && (music as Music).hasExplicitLyrics)
            holder.bind.albumTitleTextView.setTextColor(explicitColor)
        else
            holder.bind.albumTitleTextView.setTextColor(Color.LTGRAY)
    }

    override fun getItemCount():Int = asyncListDiffer.currentList.size

    inner class Holder(val bind:ItemMusicOnlineBinding):RecyclerView.ViewHolder(bind.root){
        init {
            itemView.setOnClickListener {
                itemClickInterface.onItemClick(itemView
                    ,asyncListDiffer.currentList[bindingAdapterPosition], Type.MUSIC) }
        }
    }

}
