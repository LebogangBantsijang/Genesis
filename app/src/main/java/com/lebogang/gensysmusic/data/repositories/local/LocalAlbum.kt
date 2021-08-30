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

package com.lebogang.gensysmusic.data.repositories.local

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.MediaStore.Audio.Albums._ID
import android.provider.MediaStore.Audio.Albums.ALBUM
import android.provider.MediaStore.Audio.Albums.ARTIST
import android.provider.MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI
import com.lebogang.gensysmusic.data.models.Album
import com.lebogang.gensysmusic.data.utils.LocalArtUri

class LocalAlbum(val context: Context) {

    val contentResolver:ContentResolver = context.applicationContext.contentResolver
    private val projection = arrayOf(_ID, ALBUM, ARTIST)
    private val sortByAlbumTitle = "$ALBUM ASC"

    suspend fun getAlbums():MutableList<Album>{
        val cursor = contentResolver.query(EXTERNAL_CONTENT_URI, projection, null
            , null, sortByAlbumTitle)
        return loopCursor(cursor)
    }

    fun getAlbums(albumName:String):Album?{
        val selection = "$ALBUM =?"
        val cursor = contentResolver.query(EXTERNAL_CONTENT_URI, projection, selection
            , arrayOf(albumName), sortByAlbumTitle)
        return loopCursorItem(cursor)
    }

    private fun loopCursorItem(cursor: Cursor?):Album?{
        cursor?.let {
            if (cursor.moveToFirst()){
                val id = cursor.getLong(cursor.getColumnIndex(_ID))
                val title = cursor.getString(cursor.getColumnIndex(ALBUM))
                val artist = cursor.getString(cursor.getColumnIndex(ARTIST))
                val albumArtUri = LocalArtUri.getAlbumArt(id)
                val album = Album(id, title,artist, albumArtUri)
                cursor.close()
                return album
            }
        }
        return null
    }

    private fun loopCursor(cursor: Cursor?):MutableList<Album>{
        val linkedHashMap = LinkedHashMap<String, Album>()
        cursor?.let {
            if (cursor.moveToFirst()){
                do {
                    val id = cursor.getLong(cursor.getColumnIndex(_ID))
                    val title = cursor.getString(cursor.getColumnIndex(ALBUM))
                    val artist = cursor.getString(cursor.getColumnIndex(ARTIST))
                    val albumArtUri = LocalArtUri.getAlbumArt(id)
                    val album = Album(id, title,artist, albumArtUri)
                    linkedHashMap[title] = album
                }while (cursor.moveToNext())
            }
            cursor.close()
        }
        return linkedHashMap.values.toMutableList()
    }
}
