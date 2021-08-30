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
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.lebogang.gensysmusic.data.models.Album
import com.lebogang.gensysmusic.databinding.ItemLocalAlbumBinding
import com.lebogang.gensysmusic.ui.adapters.utils.OnAlbumClickListener
import com.lebogang.gensysmusic.utils.GlideManager

class ItemAlbumAdapter:RecyclerView.Adapter<ItemAlbumAdapter.ViewHolder>(), Filterable{
    private val filteredList = arrayListOf<Album>()
    private var listAlbum = arrayListOf<Album>()
    var listener:OnAlbumClickListener? = null
    private var isUserSearching = false

    fun setAlbumData(mutableList: MutableList<Album>){
        isUserSearching = false
        filteredList.clear()
        listAlbum.clear()
        notifyDataSetChanged()
        for (x in 0 until mutableList.size){
            listAlbum.add(mutableList[x])
            notifyItemInserted(x)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemLocalAlbumBinding.inflate(inflater)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album = if(!isUserSearching) listAlbum[position] else filteredList[position]
        holder.viewBinding.titleView.text = album.title
        holder.viewBinding.subtitleView.text = album.artist
        GlideManager(holder.itemView).loadAlbumArt(album.getArtUri(), holder.viewBinding.imageView)
    }

    override fun getItemCount(): Int {
        if (isUserSearching)
            return filteredList.size
        return listAlbum.size
    }

    inner class ViewHolder(val viewBinding:ItemLocalAlbumBinding):RecyclerView.ViewHolder(viewBinding.root){
        init {
            viewBinding.root.setOnClickListener { listener?.onAlbumClick(getItem(), viewBinding.imageView) }
        }

        private fun getItem(): Album {
            return when(isUserSearching){
                true -> filteredList[adapterPosition]
                else -> listAlbum[adapterPosition]
            }
        }
    }

    override fun getFilter(): Filter {
        return object :Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                isUserSearching = false
                constraint?.let {
                    isUserSearching = true
                    filteredList.clear()
                    for(x in 0 until listAlbum.size){
                        val album = listAlbum[x]
                        if (album.title.toLowerCase().contains(it.toString().toLowerCase())){
                            filteredList.add(album)
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
