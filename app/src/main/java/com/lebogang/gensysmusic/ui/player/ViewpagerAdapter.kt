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

package com.lebogang.gensysmusic.ui.player

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lebogang.gensysmusic.databinding.ItemPlayerBinding
import com.lebogang.gensysmusic.database.local.models.Music
import com.lebogang.gensysmusic.ui.utils.ImageLoader
import com.lebogang.gensysmusic.ui.utils.Type

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
class ViewpagerAdapter:RecyclerView.Adapter<ViewpagerAdapter.Holder>(){
    private var list = listOf<Music>()
    lateinit var imageLoader: ImageLoader
    lateinit var context:Context

    fun setData(list: List<Music>){
        this.list = list
        notifyDataSetChanged()
    }

    fun getItems(position: Int): Music = list[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val bind = ItemPlayerBinding.inflate(inflater,parent,false)
        return Holder(bind)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val music = list[position]
        imageLoader.loadImage(music.artUri, Type.MUSIC,holder.bind.imageView)
    }

    override fun getItemCount(): Int = list.size

    inner class Holder(val bind: ItemPlayerBinding) : RecyclerView.ViewHolder(bind.root)
}
