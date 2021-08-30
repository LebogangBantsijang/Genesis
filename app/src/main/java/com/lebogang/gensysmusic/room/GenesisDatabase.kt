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

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lebogang.gensysmusic.room.dao.PlaylistAudioBridgeDao
import com.lebogang.gensysmusic.room.dao.PlaylistDao
import com.lebogang.gensysmusic.room.dao.StatisticsDao
import com.lebogang.gensysmusic.room.models.Playlist
import com.lebogang.gensysmusic.room.models.PlaylistBridgeTable
import com.lebogang.gensysmusic.room.models.Statistics

@Database(entities = [Playlist::class, PlaylistBridgeTable::class, Statistics::class],version = 8, exportSchema = false)
abstract class GenesisDatabase: RoomDatabase() {
    abstract fun playlistDao(): PlaylistDao
    abstract fun playlistAudioBridge(): PlaylistAudioBridgeDao
    abstract fun statisticsDao():StatisticsDao

    companion object Database{
        @Volatile
        private var DATABASE: GenesisDatabase? = null

        fun getDatabase(context: Context): GenesisDatabase {
            return DATABASE?: synchronized(this){
                val db = Room.databaseBuilder(
                    context.applicationContext, GenesisDatabase::class.java
                        , "GenesisDatabase"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                DATABASE = db
                db
            }
        }
    }
}
