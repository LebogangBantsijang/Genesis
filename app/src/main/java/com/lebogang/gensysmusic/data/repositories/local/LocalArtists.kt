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

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Artists.NUMBER_OF_ALBUMS
import android.provider.MediaStore.Audio.Artists.ARTIST
import android.provider.MediaStore.Audio.Artists._ID
import android.provider.MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI
import com.lebogang.gensysmusic.data.models.Artist
import com.lebogang.gensysmusic.data.utils.LocalArtUri

class LocalArtists(val context: Context) {
    val contentResolver: ContentResolver = context.applicationContext.contentResolver
    private val projection = arrayOf(_ID, ARTIST, NUMBER_OF_ALBUMS)
    private val uriMap = HashMap<String, String>()
    private val sortByArtistTitle = "$ARTIST ASC"

    fun getArtists():MutableList<Artist>{
        val cursor = contentResolver.query(EXTERNAL_CONTENT_URI, projection,
                null, null, sortByArtistTitle)
        return loopCursor(cursor)
    }

    fun getArtists(artistName:String):Artist? {
        val selection = "$ARTIST =?"
        val cursor = contentResolver.query(EXTERNAL_CONTENT_URI, projection,
                selection, arrayOf(artistName), sortByArtistTitle)
        return loopCursorItem(cursor)
    }

    private fun loopCursorItem(cursor: Cursor?):Artist?{
        getArtUri()
        cursor?.let{
            if (cursor.moveToFirst()){
                val id = cursor.getLong(cursor.getColumnIndex(_ID))
                val title = cursor.getString(cursor.getColumnIndex(ARTIST))
                val albumCount = cursor.getString(cursor.getColumnIndex(NUMBER_OF_ALBUMS))
                val artist = Artist(id, title, albumCount, uriMap[title])
                cursor.close()
                return artist
            }
        }
        return null
    }

    private fun loopCursor(cursor: Cursor?):MutableList<Artist>{
        getArtUri()
        val linkedHashMap = LinkedHashMap<String, Artist>()
        cursor?.let {
            if (cursor.moveToFirst()){
                do {
                    val id = cursor.getLong(cursor.getColumnIndex(_ID))
                    val title = cursor.getString(cursor.getColumnIndex(ARTIST))
                    val albumCount = cursor.getString(cursor.getColumnIndex(NUMBER_OF_ALBUMS))
                    val artist = Artist(id, title, albumCount, uriMap[title])
                    linkedHashMap[title] = artist
                }while (cursor.moveToNext())
            }
            cursor.close()
        }
        return linkedHashMap.values.toMutableList()
    }

    @SuppressLint("InlinedApi")
    private fun getArtUri(){
        if (uriMap.isEmpty()){
            val cursor = contentResolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                    arrayOf(MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ARTIST)
                    ,null,null, null)
            if (cursor != null ){
                if (cursor.moveToFirst()){
                    do {
                        val title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST))
                        val albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Albums._ID))
                        uriMap[title] = LocalArtUri.getAlbumArt(albumId)
                    }while (cursor.moveToNext())
                }
                cursor.close()
            }
        }
    }

}
