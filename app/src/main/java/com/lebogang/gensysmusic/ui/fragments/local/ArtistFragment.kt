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

package com.lebogang.gensysmusic.ui.fragments.local

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.data.models.Artist
import com.lebogang.gensysmusic.databinding.FragmentArtistsBinding
import com.lebogang.gensysmusic.settings.ThemeSettings
import com.lebogang.gensysmusic.ui.adapters.ItemArtistAdapter
import com.lebogang.gensysmusic.ui.adapters.utils.OnArtistClickListener
import com.lebogang.gensysmusic.utils.Keys
import com.lebogang.gensysmusic.viewmodels.ArtistViewModel
import com.lebogang.gensysmusic.viewmodels.ViewModelFactory

class ArtistFragment: Fragment(), OnArtistClickListener {
    private val viewBinding: FragmentArtistsBinding by lazy { FragmentArtistsBinding.inflate(layoutInflater) }
    private val adapter = ItemArtistAdapter().apply { listener = this@ArtistFragment}
    private val viewModel: ArtistViewModel by lazy {
        ViewModelFactory(requireActivity().application).getArtistViewModel() }
    private val themeSettings: ThemeSettings by lazy { ThemeSettings(requireContext()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?
                              , savedInstanceState: Bundle?): View {
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclingView()
        initSearchView()
        requestPermission()
    }

    /**
     * Seeing that this is the home fragment, check if write permissions are granted
     * */
    private fun requestPermission(){
        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED){
            populateView()
        }else
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 25)
    }

    private fun initRecyclingView(){
        viewBinding.recyclerView.layoutManager = StaggeredGridLayoutManager(themeSettings.getColumnCount(),
                StaggeredGridLayoutManager.VERTICAL)
        viewBinding.recyclerView.adapter = adapter
    }

    private fun populateView(){
        viewModel.getArtists()
        viewModel.registerContentObserver()
        viewModel.liveData.observe(viewLifecycleOwner, {
            adapter.setArtistData(it)
            loadingView(it.isNotEmpty())
            val count = getString(R.string.total) + " " + it.size.toString()
            viewBinding.counterView.text = count
        })
    }

    /**
     * Create search view for artists
     * */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.column_view_menu, menu)
    }

    /**
     * Handle artist options: mainly columns
     * */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.two_column ->{
                themeSettings.setColumnCount(2)
                (viewBinding.recyclerView.layoutManager as StaggeredGridLayoutManager).spanCount = 2
                true
            }
            R.id.three_column ->{
                themeSettings.setColumnCount(3)
                (viewBinding.recyclerView.layoutManager as StaggeredGridLayoutManager).spanCount = 3
                true
            }
            R.id.search ->{
                showHideSearchView(true)
                true
            }
            else ->super.onOptionsItemSelected(item)
        }
    }

    private fun showHideSearchView(show:Boolean){
        if (show){
            viewBinding.searchContainerView.visibility = View.VISIBLE
            viewBinding.searchContainerView.animate().alpha(1f).setDuration(1500)
                .withEndAction { viewBinding.searchView.requestFocus() }.start()
        }else{
            viewBinding.searchContainerView.animate().alpha(0f).setDuration(1000)
                .withEndAction {
                    viewBinding.recyclerView.requestFocus()
                    viewBinding.searchContainerView.visibility = View.GONE
                }.start()
        }
    }

    private fun initSearchView(){
        viewBinding.closeSearchView.setOnClickListener { showHideSearchView(false) }
    }

    /**
     * Hide or show loading view
     * */
    private fun loadingView(hasContent:Boolean){
        viewBinding.loadingView.visibility = View.GONE
        if (hasContent){
            viewBinding.noContentView.text = null
        }else{
            viewBinding.noContentView.text = getString(R.string.no_content)
        }
    }

    /**
     * Remove content observers
     * */
    override fun onDestroy() {
        super.onDestroy()
        viewModel.unregisterContentContentObserver()
    }

    /**
     * Navigate to artist view fragment
     * @param artist: artist to view
     * @param imageView: Planning to use it for that shared animations thing, forgot the name
     * */
    override fun onArtistClick(artist: Artist, imageView: View) {
        val bundle = Bundle().apply{putParcelable(Keys.ARTIST_KEY, artist)}
        val controller = findNavController()
        controller.navigate(R.id.viewArtistFragment, bundle)
    }

}
