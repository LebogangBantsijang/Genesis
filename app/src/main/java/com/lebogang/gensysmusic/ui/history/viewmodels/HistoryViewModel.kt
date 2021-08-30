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

package com.lebogang.gensysmusic.ui.history.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lebogang.gensysmusic.database.history.HistoryRepository
import com.lebogang.gensysmusic.database.history.models.History
import com.lebogang.gensysmusic.database.local.models.Music
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.*

class HistoryViewModel(private val historyRepository: HistoryRepository): ViewModel() {

    fun getHistory() = historyRepository.getHistory()

    fun clearAll() = viewModelScope.launch(Dispatchers.IO) {
        historyRepository.clearAll()
    }

    companion object fun addMusicHistory(music: Music) = CoroutineScope(Dispatchers.IO+SupervisorJob()).launch {
        var count = 1
        count += historyRepository.getCount(music.id)
        val date = Calendar.getInstance().time.toString()
        val history = History(0,music.id,music.title,music.artist
            ,music.album,date,count,music.date.toString(),music.contentUri)
        historyRepository.addHistory(history)
    }

}
