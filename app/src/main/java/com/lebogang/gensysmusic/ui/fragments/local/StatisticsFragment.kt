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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.databinding.FragmentStatisticsBinding
import com.lebogang.gensysmusic.room.models.Statistics
import com.lebogang.gensysmusic.ui.adapters.ItemStatsAdapter
import com.lebogang.gensysmusic.ui.adapters.utils.OnStatisticClickListener
import com.lebogang.gensysmusic.utils.Keys
import com.lebogang.gensysmusic.viewmodels.StatisticsViewModel
import com.lebogang.gensysmusic.viewmodels.ViewModelFactory

class StatisticsFragment: Fragment(), OnStatisticClickListener {
    private val viewBinding: FragmentStatisticsBinding by lazy{
        FragmentStatisticsBinding.inflate(layoutInflater)
    }
    private val viewModel: StatisticsViewModel by lazy{
        ViewModelFactory(requireActivity().application).getStatisticsViewModel()}
    private val adapter = ItemStatsAdapter().apply { listener = this@StatisticsFragment }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView(){
        viewBinding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewBinding.recyclerView.adapter = adapter
        viewModel.liveData.observe(viewLifecycleOwner,{
            adapter.setStatData(it)
        })
    }

    /**
     * Navigate to the statistics view fragment
     * @param statistics: statistic to view
     * */
    override fun onStatisticClick(statistics: Statistics) {
        val bundle = Bundle().apply{putParcelable(Keys.STATISTICS_KEY, statistics)}
        val controller = findNavController()
        controller.navigate(R.id.statisticsDialog, bundle)
    }
}
