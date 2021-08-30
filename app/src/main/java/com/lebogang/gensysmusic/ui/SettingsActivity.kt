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

package com.lebogang.gensysmusic.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate.*
import com.google.android.play.core.review.ReviewManagerFactory
import com.lebogang.gensysmusic.GensysApp
import com.lebogang.gensysmusic.databinding.ActivitySettingsBinding
import com.lebogang.gensysmusic.ui.local.viewmodel.MusicViewModel
import com.lebogang.gensysmusic.ui.utils.ModelFactory
import com.lebogang.gensysmusic.utils.Keys

class SettingsActivity : AppCompatActivity() {
    private val bind:ActivitySettingsBinding by lazy{ActivitySettingsBinding.inflate(layoutInflater)}
    private val app:GensysApp by lazy { application as GensysApp }
    private val musicViewModel:MusicViewModel by lazy{ ModelFactory(app).getMusicViewModel()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)
        initToolbar()
        initTheme()
        initViews()
    }

    private fun initToolbar(){
        setSupportActionBar(bind.toolbar)
        bind.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initTheme(){
        bind.themeSwitch.isChecked = getDefaultNightMode() == MODE_NIGHT_YES
        bind.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) setDefaultNightMode(MODE_NIGHT_YES)
            else setDefaultNightMode(MODE_NIGHT_NO)
            getSharedPreferences(Keys.PREFERENCE_NAME,Context.MODE_PRIVATE)
                .edit().putInt(Keys.THEME_KEY, getDefaultNightMode()).apply()
        }
    }

    private fun initViews(){
        bind.resetDatabase.setOnClickListener { app.localContent.initDatabase() }
        bind.rateApp.setOnClickListener {
            val manager = ReviewManagerFactory.create(this)
            manager.requestReviewFlow().addOnCompleteListener {
                manager.launchReviewFlow(this,it.result)
            }
        }
    }

}
