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

package com.lebogang.gensysmusic.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.lebogang.gensysmusic.databinding.DialogStatisticBinding
import com.lebogang.gensysmusic.room.models.Statistics
import com.lebogang.gensysmusic.utils.Keys
import com.lebogang.gensysmusic.viewmodels.AudioViewModel
import com.lebogang.gensysmusic.viewmodels.ViewModelFactory

class StatisticsDialog:DialogFragment(){
    private val viewBinding:DialogStatisticBinding by lazy { DialogStatisticBinding.inflate(layoutInflater) }
    private val audioViewModel:AudioViewModel by lazy{ ViewModelFactory(requireActivity().application)
            .getAudioViewModel()}
    private lateinit var statistics: Statistics

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        statistics = requireArguments().getParcelable(Keys.STATISTICS_KEY)!!
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews(){
        viewBinding.titleView.text = statistics.title
        viewBinding.albumView.text = statistics.album
        viewBinding.artistView.text = statistics.artist
        viewBinding.availableView.text = isAvailable(statistics.audioId)
        viewBinding.durationView.text = statistics.duration
        viewBinding.playCountView.text = statistics.playCount.toString()
        viewBinding.dateView.text = statistics.lastPlayed
    }

    private fun isAvailable(audioId:Long):String{
        val isAvailable = audioViewModel.isAudioAvailable(audioId)
        if (isAvailable)
            return "Yes"
        return "No"
    }
}
