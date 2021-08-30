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

package com.lebogang.gensysmusic.ui.local.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.lebogang.gensysmusic.GensysApp
import com.lebogang.gensysmusic.database.local.models.Album
import com.lebogang.gensysmusic.databinding.FragmentFavouriteLayoutBinding
import com.lebogang.gensysmusic.ui.utils.ImageLoader
import com.lebogang.gensysmusic.ui.utils.ItemClickInterface
import com.lebogang.gensysmusic.ui.utils.ItemOptionsInterface
import com.lebogang.gensysmusic.ui.utils.Type
import com.lebogang.gensysmusic.ui.local.AlbumDetailsActivity
import com.lebogang.gensysmusic.ui.local.adapters.AlbumAdapter
import com.lebogang.gensysmusic.ui.local.viewmodel.AlbumViewModel
import com.lebogang.gensysmusic.ui.utils.ModelFactory
import com.lebogang.gensysmusic.utils.Keys

class FavouriteAlbumsFragment:Fragment() {
    private lateinit var bind:FragmentFavouriteLayoutBinding
    private val app: GensysApp by lazy { requireActivity().application as GensysApp }
    private val albumViewModel: AlbumViewModel by lazy { ModelFactory(app).getAlbumViewModel()}
    private val adapter = AlbumAdapter()

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, state: Bundle?): View {
        bind = FragmentFavouriteLayoutBinding.inflate(inflater, parent, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    private fun initRecycler(){
        adapter.imageLoader = ImageLoader(requireActivity())
        adapter.itemOptionsInterface = getFavouriteInterface()
        adapter.itemClickInterface = getItemClick()
        albumViewModel.getFavouriteAlbums().observe(viewLifecycleOwner,{adapter.setData(it)})
        bind.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        bind.recyclerView.adapter = adapter
    }

    private fun getFavouriteInterface() = object : ItemOptionsInterface {
        override fun onOptionsClick(item: Any) {
            val album = item as Album
            album.isFavourite = !album.isFavourite
            albumViewModel.addAlbum(album)
        }
    }

    private fun getItemClick() = object : ItemClickInterface {
        override fun onItemClick(view: View, item: Any?, type: Type) {
            val album = item as Album
            val intent = Intent(requireContext(), AlbumDetailsActivity::class.java)
            intent.putExtra(Keys.ALBUM_KEY,album.id)
            startActivity(intent)
        }
    }

}
