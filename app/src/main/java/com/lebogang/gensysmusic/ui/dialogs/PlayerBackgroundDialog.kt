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
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.databinding.DialogPlayerBackgroundBinding
import com.lebogang.gensysmusic.settings.PlayerBackgroundType
import com.lebogang.gensysmusic.settings.PlayerSettings
import com.lebogang.gensysmusic.ui.MainActivity

class PlayerBackgroundDialog : DialogFragment(){
    private val viewBinding:DialogPlayerBackgroundBinding by lazy {
        DialogPlayerBackgroundBinding.inflate(layoutInflater) }
    private val playerSettings:PlayerSettings by lazy {
        PlayerSettings(requireContext()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?
                              , savedInstanceState: Bundle?): View {
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        val backgroundType = playerSettings.getBackgroundType()
        viewBinding.radioGroup.check(getValueId(backgroundType))
        viewBinding.saveView.setOnClickListener {
            when(viewBinding.radioGroup.checkedRadioButtonId){
                R.id.noneView-> playerSettings.setBackgroundType(PlayerBackgroundType.NONE)
                R.id.adaptiveImage-> playerSettings.setBackgroundType(PlayerBackgroundType.ADAPTIVE_IMAGE)
                R.id.adaptiveBlurr-> playerSettings.setBackgroundType(PlayerBackgroundType.ADAPTIVE_BLURRY)
                R.id.gif-> playerSettings.setBackgroundType(PlayerBackgroundType.GIF)
            }
            (requireActivity() as MainActivity).changePlayerBackground(playerSettings.getBackgroundType())
            dismiss()
        }
    }

    private fun getValueId(type:PlayerBackgroundType):Int{
        return when(type){
            PlayerBackgroundType.NONE-> R.id.noneView
            PlayerBackgroundType.ADAPTIVE_IMAGE-> R.id.adaptiveImage
            PlayerBackgroundType.ADAPTIVE_BLURRY-> R.id.adaptiveBlurr
            PlayerBackgroundType.GIF -> R.id.gif
        }
    }

}
