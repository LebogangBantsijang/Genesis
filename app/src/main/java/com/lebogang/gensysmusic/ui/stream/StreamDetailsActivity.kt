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

package com.lebogang.gensysmusic.ui.stream

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.lebogang.gensysmusic.databinding.ActivityStreamDetailsBinding
import com.lebogang.gensysmusic.ui.utils.Colors
import com.lebogang.gensysmusic.ui.stream.adapters.ViewpagerAdapter

class StreamDetailsActivity : AppCompatActivity() {
    private val bind:ActivityStreamDetailsBinding by lazy{ActivityStreamDetailsBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)
        initToolbar()
        initViewPager()
    }

    private fun initToolbar(){
        setSupportActionBar(bind.toolbar)
        bind.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initViewPager(){
        bind.viewpager.adapter = ViewpagerAdapter(null, this)
        bind.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position > 0){
                    bind.carousel[position].backgroundTintList =
                        Colors.getPrimaryColorTintList(this@StreamDetailsActivity)
                    bind.carousel[0].backgroundTintList = Colors.getButtonNormalTintList(theme)
                }else{
                    bind.carousel[0].backgroundTintList =
                        Colors.getPrimaryColorTintList(this@StreamDetailsActivity)
                    bind.carousel[position].backgroundTintList = Colors.getButtonNormalTintList(theme)
                }
            }
        })
    }
}
