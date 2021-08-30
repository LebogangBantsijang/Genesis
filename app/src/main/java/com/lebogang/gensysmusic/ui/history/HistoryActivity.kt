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

package com.lebogang.gensysmusic.ui.history

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lebogang.gensysmusic.GensysApp
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.database.history.models.History
import com.lebogang.gensysmusic.databinding.ActivityHistoryBinding
import com.lebogang.gensysmusic.ui.utils.DialogStyle
import com.lebogang.gensysmusic.ui.utils.ItemClickInterface
import com.lebogang.gensysmusic.ui.utils.ModelFactory
import com.lebogang.gensysmusic.ui.utils.Type
import com.lebogang.gensysmusic.ui.history.adapters.HistoryAdapter
import com.lebogang.gensysmusic.ui.history.dialogs.HistoryDialog
import com.lebogang.gensysmusic.ui.history.viewmodels.HistoryViewModel

class HistoryActivity : AppCompatActivity() {
    private val bind:ActivityHistoryBinding by lazy{ActivityHistoryBinding.inflate(layoutInflater)}
    private val app:GensysApp by lazy {application as GensysApp}
    private val viewModel:HistoryViewModel by lazy { ModelFactory(app).getHistoryViewModel() }
    private val adapter = HistoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)
        initToolbar()
        initRecyclerView()
    }

    private fun initToolbar(){
        setSupportActionBar(bind.toolbar)
        bind.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.application_delete, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.clear ->{
                showClearDialog()
                true
            }
            else -> false
        }
    }

    private fun initRecyclerView(){
        adapter.itemClickInterface = getItemClickInterface()
        viewModel.getHistory().asLiveData().observe(this,{
            adapter.setData(it)
        })
        bind.recyclerView.layoutManager = LinearLayoutManager(this)
        bind.recyclerView.adapter = adapter
    }

    private fun getItemClickInterface() = object: ItemClickInterface {
        override fun onItemClick(view: View, item: Any?, type: Type) {
            val history = item as History
            val dialog = HistoryDialog()
            dialog.history = history
            dialog.show(supportFragmentManager, null)
        }
    }

    private fun showClearDialog() = MaterialAlertDialogBuilder(this)
        .setBackground(DialogStyle.getDialogBackground(this))
        .setTitle(getString(R.string.clear_all))
        .setMessage(getString(R.string.reset_history))
        .setNegativeButton(getString(R.string.no),null)
        .setPositiveButton(getString(R.string.yes)){ _, _->
            viewModel.clearAll()
        }.create().show()
}
