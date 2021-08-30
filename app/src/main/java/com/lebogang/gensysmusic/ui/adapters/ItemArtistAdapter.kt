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
import com.lebogang.gensysmusic.data.models.Artist
import com.lebogang.gensysmusic.databinding.ItemLocalArtistBinding
import com.lebogang.gensysmusic.ui.adapters.utils.OnArtistClickListener
import com.lebogang.gensysmusic.utils.GlideManager

class ItemArtistAdapter:RecyclerView.Adapter<ItemArtistAdapter.ViewHolder>(), Filterable{
    var listener:OnArtistClickListener? = null
    private var listArtist = arrayListOf<Artist>()
    private val filteredList = arrayListOf<Artist>()
    private var isUserSearching = false

    fun setArtistData(mutableList: MutableList<Artist>){
        isUserSearching = false
        filteredList.clear()
        listArtist.clear()
        notifyDataSetChanged()
        for (x in 0 until mutableList.size){
            listArtist.add(mutableList[x])
            notifyItemInserted(x)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemLocalArtistBinding.inflate(inflater, parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val artist = if(!isUserSearching) listArtist[position] else filteredList[position]
        holder.viewBinding.titleView.text = artist.title
        GlideManager(holder.itemView).loadArtistArt(artist.getArtUri(), holder.viewBinding.imageView)
    }

    override fun getItemCount(): Int {
        if (isUserSearching)
            return filteredList.size
        return listArtist.size
    }

    inner class ViewHolder(val viewBinding:ItemLocalArtistBinding):RecyclerView.ViewHolder(viewBinding.root){
        init {
            viewBinding.root.setOnClickListener {
                listener?.onArtistClick(getItem(),viewBinding.imageView)
            }
        }

        private fun getItem(): Artist {
            return when(isUserSearching){
                true -> filteredList[adapterPosition]
                else -> listArtist[adapterPosition]
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
                    for(x in 0 until listArtist.size){
                        val artist = listArtist[x]
                        if (artist.title.toLowerCase().contains(it.toString().toLowerCase())){
                            filteredList.add(artist)
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
