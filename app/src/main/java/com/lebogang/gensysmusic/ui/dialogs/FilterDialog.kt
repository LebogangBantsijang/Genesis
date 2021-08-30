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
import com.lebogang.gensysmusic.databinding.DialogFilterBinding
import com.lebogang.gensysmusic.settings.ContentSettings

class FilterDialog: DialogFragment(){
    private val viewBinding:DialogFilterBinding by lazy { DialogFilterBinding.inflate(layoutInflater) }
    private val contentSettings:ContentSettings by lazy {
        ContentSettings(requireContext()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSeekView()
    }

    private fun initSeekView(){
        viewBinding.seekBar.progress = (contentSettings.getDurationFilter()/10).toInt()
        viewBinding.saveView.setOnClickListener {
            val duration = (viewBinding.seekBar.progress * 10).toLong()
            contentSettings.setDurationFilter(duration)
            dismiss()
        }
    }
}
