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

package com.lebogang.gensysmusic.room

import androidx.annotation.WorkerThread
import com.lebogang.gensysmusic.data.models.Audio
import com.lebogang.gensysmusic.room.dao.StatisticsDao
import com.lebogang.gensysmusic.room.models.Statistics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class StatisticsRepo(private val statisticsRepo: StatisticsDao) {

    fun getStats():Flow<List<Statistics>> = statisticsRepo.getStats()
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun insertStat(audio: Audio) = scope.launch{
        val date = SimpleDateFormat.getDateInstance().format(Date().time)
        val oldStat = statisticsRepo.getStat(audio.id)
        if (oldStat != null){
            oldStat.lastPlayed = date
            oldStat.playCount = (1 + oldStat.playCount)
            statisticsRepo.insertStat(oldStat)
        }else{
            val statistics = Statistics(0,audio.id, audio.title, audio.album,
                    audio.artist,audio.durationFormatted,1, date)
            statisticsRepo.insertStat(statistics)
        }
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteStat(statistics: Statistics){
        statisticsRepo.deleteStat(statistics)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun clearAll(){
        statisticsRepo.clearAll()
    }


}
