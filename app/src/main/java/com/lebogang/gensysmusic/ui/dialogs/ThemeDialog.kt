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
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.DialogFragment
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.databinding.DialogThemeBinding
import com.lebogang.gensysmusic.settings.ThemeSettings

class ThemeDialog: DialogFragment() {
    private val viewBinding:DialogThemeBinding by lazy {DialogThemeBinding.inflate(layoutInflater)}
    private val themeSettings:ThemeSettings by lazy {
        ThemeSettings(requireContext()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?
                              , savedInstanceState: Bundle?): View {
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews(){
        viewBinding.radioGroup.check(getCheckId())
        viewBinding.saveView.setOnClickListener {
            when(viewBinding.radioGroup.checkedRadioButtonId){
                R.id.lightButton ->{
                    themeSettings.setThemeMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                R.id.darkButton ->{
                    themeSettings.setThemeMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
            }
            requireActivity().recreate()
            dismiss()
        }
    }

    private fun getCheckId():Int{
        return when(themeSettings.getThemeMode()){
            AppCompatDelegate.MODE_NIGHT_YES -> R.id.darkButton
            else -> R.id.lightButton
        }
    }
}
