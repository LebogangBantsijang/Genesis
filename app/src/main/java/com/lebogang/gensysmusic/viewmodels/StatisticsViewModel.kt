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

package com.lebogang.gensysmusic.viewmodels

import androidx.lifecycle.*
import com.lebogang.gensysmusic.room.StatisticsRepo
import com.lebogang.gensysmusic.room.models.Statistics
import kotlinx.coroutines.launch

class StatisticsViewModel(private val statisticsRepo: StatisticsRepo):ViewModel() {
    val liveData:LiveData<List<Statistics>> = statisticsRepo.getStats().asLiveData()

    fun deleteStat(statistics: Statistics) = viewModelScope.launch {
        statisticsRepo.deleteStat(statistics)
    }

    fun clearAll() = viewModelScope.launch {
        statisticsRepo.clearAll()
    }
}
