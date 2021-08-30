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
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.data.models.Audio
import com.lebogang.gensysmusic.databinding.FragmentSongsBinding
import com.lebogang.gensysmusic.service.MusicQueue
import com.lebogang.gensysmusic.settings.ContentSettings
import com.lebogang.gensysmusic.ui.MainActivity
import com.lebogang.gensysmusic.ui.adapters.ItemSongAdapter
import com.lebogang.gensysmusic.ui.adapters.utils.OnAudioClickListener
import com.lebogang.gensysmusic.utils.Keys
import com.lebogang.gensysmusic.viewmodels.AudioViewModel
import com.lebogang.gensysmusic.viewmodels.ViewModelFactory

class SongsFragment: Fragment(), OnAudioClickListener {
    private val viewBinding: FragmentSongsBinding by lazy { FragmentSongsBinding.inflate(layoutInflater) }
    private val adapter = ItemSongAdapter().apply { listener= this@SongsFragment }
    private val viewModel: AudioViewModel by lazy {
        ViewModelFactory(requireActivity().application).getAudioViewModel() }
    private val contentSettings: ContentSettings by lazy { ContentSettings(requireContext()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initSearchView()
        requestPermission()
    }

    private fun initRecyclerView(){
        viewBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        viewBinding.recyclerView.adapter = adapter
        viewBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when(newState){
                    RecyclerView.SCROLL_STATE_DRAGGING ->{
                        MusicQueue.currentAudio.value?.let {
                            if (viewBinding.targetView.visibility != View.VISIBLE)
                                viewBinding.targetView.visibility = View.VISIBLE
                        }
                    }
                    RecyclerView.SCROLL_STATE_IDLE ->{
                        if (viewBinding.targetView.visibility == View.VISIBLE)
                            Handler(Looper.getMainLooper()).postDelayed({
                                viewBinding.targetView.visibility = View.GONE
                            },3000)
                    }
                }
            }
        })
        viewBinding.targetView.setOnClickListener {
            MusicQueue.currentAudio.value?.let {
                viewBinding.recyclerView.scrollToPosition(adapter.nowPlayingIndex(it.id))
            }
        }
    }

    /**
     * Seeing that this is the home fragment, check if write permissions are granted
     * */
    private fun requestPermission(){
        if(ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED){
            populateViews()
        }else
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 25)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>
                                            , grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 25 && grantResults.isNotEmpty()){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                populateViews()
            }else
                requireActivity().finish()
        }
    }

    /**
     * Create search view for songs
     * */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.audio_sort_menu, menu)
    }

    /**
     * Handle songs options
     * */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.titleAsc ->{
                contentSettings.setSortOrder(contentSettings.titleAsc)
                viewModel.getAudio()
                true
            }
            R.id.titleDesc ->{
                contentSettings.setSortOrder(contentSettings.titleDesc)
                viewModel.getAudio()
                true
            }
            R.id.dateAsc ->{
                contentSettings.setSortOrder(contentSettings.dateAsc)
                viewModel.getAudio()
                true
            }
            R.id.dateDesc ->{
                contentSettings.setSortOrder(contentSettings.dateDesc)
                viewModel.getAudio()
                true
            }
            R.id.search->{
                showHideSearchView(true)
                true
            }
            R.id.refresh->{
                viewModel.getAudio()
                true
            }
            else-> false
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
                    viewBinding.searchView.text = null
                    viewBinding.recyclerView.requestFocus()
                    viewBinding.searchContainerView.visibility = View.GONE
                }.start()
        }
    }

    private fun initSearchView(){
        viewBinding.closeSearchView.setOnClickListener { showHideSearchView(false) }
    }

    private fun populateViews(){
        viewModel.getAudio()
        viewModel.registerContentObserver()
        viewModel.liveData.observe(viewLifecycleOwner,{
            adapter.setAudioData(it)
            loadingView(it.isNotEmpty())
            val count = getString(R.string.total) + " " + it.size.toString()
            viewBinding.counterView.text = count
        })
        MusicQueue.currentAudio.observe(viewLifecycleOwner,{ adapter.setNowPlaying(it.id) })
    }

    /**
     * Show or hide loading view
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
     * Remove content observer
     * */
    override fun onDestroy() {
        super.onDestroy()
        viewModel.unregisterContentContentObserver()
    }

    /**
     * Play audio
     * @param audio: audio play
     * */
    override fun onAudioClick(audio: Audio) {
        (requireActivity() as MainActivity).playAudio(audio, adapter.getList())
    }

    /**
     * Open that audio options dialog.
     * @param audio: audio for the menu
     * */
    override fun onAudioClickOptions(audio: Audio) {
        val bundle = Bundle().apply{
            putParcelable(Keys.MUSIC_KEY, audio)
            putBoolean(Keys.ENABLE_UPDATE_KEY,true) }
        val controller = findNavController()
        controller.navigate(R.id.audioOptionsDialog, bundle)
    }

}
