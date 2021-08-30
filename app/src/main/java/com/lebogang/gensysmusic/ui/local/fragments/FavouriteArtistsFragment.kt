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
import com.lebogang.gensysmusic.database.local.models.Artist
import com.lebogang.gensysmusic.databinding.FragmentFavouriteLayoutBinding
import com.lebogang.gensysmusic.ui.utils.ImageLoader
import com.lebogang.gensysmusic.ui.utils.ItemClickInterface
import com.lebogang.gensysmusic.ui.utils.ItemOptionsInterface
import com.lebogang.gensysmusic.ui.utils.Type
import com.lebogang.gensysmusic.ui.local.ArtistDetailsActivity
import com.lebogang.gensysmusic.ui.local.adapters.ArtistAdapter
import com.lebogang.gensysmusic.ui.local.viewmodel.ArtistViewModel
import com.lebogang.gensysmusic.ui.utils.ModelFactory
import com.lebogang.gensysmusic.utils.Keys

class FavouriteArtistsFragment:Fragment() {
    private lateinit var bind:FragmentFavouriteLayoutBinding
    private val app: GensysApp by lazy { requireActivity().application as GensysApp }
    private val artistViewModel: ArtistViewModel by lazy { ModelFactory(app).getArtistViewModel()}
    private val adapter = ArtistAdapter()

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
        artistViewModel.getFavourites().observe(viewLifecycleOwner,{adapter.setData(it)})
        bind.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        bind.recyclerView.adapter = adapter
    }

    private fun getFavouriteInterface() = object : ItemOptionsInterface {
        override fun onOptionsClick(item: Any) {
            val artist = item as Artist
            artist.isFavourite = !artist.isFavourite
            artistViewModel.addArtist(artist)
        }
    }

    private fun getItemClick() = object : ItemClickInterface {
        override fun onItemClick(view: View, item: Any?, type: Type) {
            val artist = item as Artist
            val intent = Intent(requireContext(), ArtistDetailsActivity::class.java)
            intent.putExtra(Keys.ARTIST_KEY,artist.id)
            startActivity(intent)
        }
    }

}
