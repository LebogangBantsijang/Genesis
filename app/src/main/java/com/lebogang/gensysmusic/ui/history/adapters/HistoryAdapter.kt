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

package com.lebogang.gensysmusic.ui.history.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lebogang.gensysmusic.database.history.models.History
import com.lebogang.gensysmusic.databinding.ItemMusicHistoryBinding
import com.lebogang.gensysmusic.ui.utils.ImageLoader
import com.lebogang.gensysmusic.ui.utils.ItemClickInterface
import com.lebogang.gensysmusic.ui.utils.Type

class HistoryAdapter:RecyclerView.Adapter<HistoryAdapter.Holder>() {
    private var asyncListDiffer = AsyncListDiffer(this,DiffCallBack)
    lateinit var itemClickInterface: ItemClickInterface
    lateinit var imageLoader: ImageLoader

    fun setData(list: MutableList<History>) = asyncListDiffer.submitList(list)

    companion object DiffCallBack:DiffUtil.ItemCallback<History>(){
        override fun areItemsTheSame(o: History, n: History): Boolean = o.id == n.id

        override fun areContentsTheSame(o: History, n: History): Boolean = o.equals(n)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val bind = ItemMusicHistoryBinding.inflate(inflater, parent, false)
        return Holder(bind)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val history = asyncListDiffer.currentList[position]
        holder.bind.titleView.text = history.title
        holder.bind.subtitleView.text = history.artist
        imageLoader.loadImage(history.artUri, Type.MUSIC,holder.bind.imageView)
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    inner class Holder(val bind:ItemMusicHistoryBinding):RecyclerView.ViewHolder(bind.root){
        init {
            itemView.setOnClickListener {
                itemClickInterface.onItemClick(itemView
                    ,asyncListDiffer.currentList[bindingAdapterPosition], Type.MUSIC)}
        }
    }
}
