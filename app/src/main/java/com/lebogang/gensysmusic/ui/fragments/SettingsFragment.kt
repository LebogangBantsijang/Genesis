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

package com.lebogang.gensysmusic.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.review.ReviewManagerFactory
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment(){
    private val viewBinding:FragmentSettingsBinding by lazy{ FragmentSettingsBinding.inflate(layoutInflater)}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.backgroundView.setOnClickListener {
            val controller = findNavController()
            controller.navigate(R.id.playerBackgroundDialog)
        }
        viewBinding.filterView.setOnClickListener {
            val controller = findNavController()
            controller.navigate(R.id.filterDialog)
        }
        viewBinding.selectThemeView.setOnClickListener {
            val controller = findNavController()
            controller.navigate(R.id.themeDialog)
        }
        viewBinding.volumeView.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                val controller = findNavController()
                controller.navigate(R.id.audioProcessingDialog)
            }else{
                Snackbar.make(viewBinding.root,"Not Supported", Snackbar.LENGTH_LONG).show()
            }
        }
        viewBinding.rateView.setOnClickListener {
            val rateManager = ReviewManagerFactory.create(requireContext())
            val request = rateManager.requestReviewFlow()
            request.addOnCompleteListener {
                if(it.isSuccessful){
                    val info = it.result
                    rateManager.launchReviewFlow(requireActivity(), info)
                }
            }
        }
    }

}
