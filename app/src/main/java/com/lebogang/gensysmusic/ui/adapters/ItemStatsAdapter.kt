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
import com.lebogang.gensysmusic.databinding.ItemLayoutStatsBinding
import com.lebogang.gensysmusic.room.models.Statistics
import com.lebogang.gensysmusic.ui.adapters.utils.OnStatisticClickListener

class ItemStatsAdapter :RecyclerView.Adapter<ItemStatsAdapter.ViewHolder>(){
    private var listStats = listOf<Statistics>()
    var listener:OnStatisticClickListener? = null

    fun setStatData(list: List<Statistics>){
        listStats = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemLayoutStatsBinding.inflate(inflater, parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stat = listStats[position]
        holder.viewBinding.playCountView.text = stat.playCount.toString()
        holder.viewBinding.titleView.text = stat.title
        holder.viewBinding.durationView.text = stat.duration
    }

    override fun getItemCount(): Int {
        return listStats.size
    }

    inner class ViewHolder(val viewBinding:ItemLayoutStatsBinding):
        RecyclerView.ViewHolder(viewBinding.root){
            init {
                viewBinding.root.setOnClickListener {
                    listener?.onStatisticClick(listStats[adapterPosition])
                }
            }
        }
}
