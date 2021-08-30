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
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.database.local.models.Music
import com.lebogang.gensysmusic.databinding.ItemMusicBinding
import com.lebogang.gensysmusic.ui.utils.ImageLoader
import com.lebogang.gensysmusic.ui.utils.ItemOptionsInterface
import com.lebogang.gensysmusic.ui.utils.Type
import com.lebogang.gensysmusic.ui.local.Sort
import com.lebogang.gensysmusic.ui.utils.DiffUtilMusic
import com.lebogang.gensysmusic.utils.SortHelper

class MusicAdapter:RecyclerView.Adapter<MusicAdapter.Holder>() {
    private val asyncListDiffer = AsyncListDiffer(this,DiffUtilMusic)
    lateinit var imageLoader: ImageLoader
    lateinit var favouriteClickInterface: ItemOptionsInterface
    lateinit var optionsClickInterface: ItemOptionsInterface
    var selectableBackground:Int = 0
    var hideFavouriteButton:Boolean = false
    var showOptions:Boolean = true

    fun sort(sort: Sort){
        val list = SortHelper.sortListAbstract(sort, asyncListDiffer.currentList)
        asyncListDiffer.submitList(list)
    }

    fun setData(list: List<Music>) = asyncListDiffer.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val bind = ItemMusicBinding.inflate(inflater, parent, false)
        if (!showOptions)
            bind.moreView.visibility = View.GONE
        return Holder(bind)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val music = asyncListDiffer.currentList[position]
        holder.bind.titleTextView.text = music.getItemTitle()
        holder.bind.subtitleView.text = music.getItemArtist()
        if (!hideFavouriteButton){
            if (music.getIsItemFavourite())
                holder.bind.favouriteView.setImageResource(R.drawable.ic_melting_heart_filled_ios)
            else
                holder.bind.favouriteView.setImageResource(R.drawable.ic_melting_heart_ios)
        }
        imageLoader.loadImage(music.getItemArt(), Type.MUSIC,holder.bind.imageView)
    }

    private fun highlight(holder:Holder,isSelected:Boolean){
        if (isSelected)
            holder.bind.root.setBackgroundResource(R.color.primaryColor_light)
        else
            holder.bind.root.setBackgroundResource(selectableBackground)
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    inner class Holder(val bind:ItemMusicBinding):RecyclerView.ViewHolder(bind.root){
        init {
            if (hideFavouriteButton)
                bind.favouriteView.visibility = View.GONE
            else
                bind.favouriteView.setOnClickListener {
                    favouriteClickInterface.onOptionsClick(asyncListDiffer.currentList[bindingAdapterPosition]) }
            if (showOptions)
                bind.moreView.setOnClickListener {
                    optionsClickInterface.onOptionsClick(asyncListDiffer.currentList[bindingAdapterPosition]) }
        }
    }

}
