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

package com.lebogang.gensysmusic

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lebogang.gensysmusic.database.history.HistoryAccess
import com.lebogang.gensysmusic.database.history.models.History
import com.lebogang.gensysmusic.database.local.*
import com.lebogang.gensysmusic.database.local.models.*
import com.lebogang.gensysmusic.database.user.UserAccess
import com.lebogang.gensysmusic.database.user.models.User

@Database(entities = [Music::class, Album::class, Artist::class, Playlist::class,History::class,
    Playlist.Members::class, Genre::class,Genre.Members::class,User::class,Favourite::class]
    ,version = 20,
    exportSchema = false)
abstract class GensysDatabase:RoomDatabase() {
    abstract fun getMusicAccess():MusicAccess
    abstract fun getAlbumAccess():AlbumAccess
    abstract fun getArtistAccess():ArtistAccess
    abstract fun getPlaylistAccess():PlaylistAccess
    abstract fun getHistoryAccess():HistoryAccess
    abstract fun getGenreAccess():GenreAccess
    abstract fun getUserAccess():UserAccess
    abstract fun getFavouriteAccess():FavouriteAccess

    companion object AppDatabase{
        @Volatile
        private var gensysDatabase:GensysDatabase? = null

        fun getDatabase(context: Context):GensysDatabase{
            return gensysDatabase ?: synchronized(this){
                val db = Room.databaseBuilder(context, GensysDatabase::class.java
                    , "VibeDatabase")
                    .fallbackToDestructiveMigration()
                    .build()
                gensysDatabase = db
                db
            }
        }
    }
}
