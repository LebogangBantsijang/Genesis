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
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore.Audio.Media.*
import android.text.format.Formatter
import com.lebogang.gensysmusic.data.models.Audio
import com.lebogang.gensysmusic.data.utils.LocalArtUri
import com.lebogang.gensysmusic.settings.ContentSettings
import com.lebogang.gensysmusic.utils.TimeConverter


class LocalAudio(private val context:Context) {

    val contentResolver :ContentResolver = context.applicationContext.contentResolver
    @SuppressLint("InlinedApi")
    private val projection = arrayOf(_ID, TITLE, ARTIST, ALBUM, ALBUM_ID, DURATION, SIZE)
    private val contentSettings = ContentSettings(context)
    @SuppressLint("InlinedApi")
    private val selection = "$DURATION >=? AND $IS_MUSIC"

    fun getAudio():MutableList<Audio>{
        val sortOrder = contentSettings.getSortOrder()
        val durationFilter = contentSettings.getDurationFilter()
        val cursor = contentResolver.query(EXTERNAL_CONTENT_URI, projection,
                selection, arrayOf(durationFilter.toString()), sortOrder)
        return loopCursor(cursor)
    }

    @SuppressLint("InlinedApi")
    fun getAlbumAudio(albumName:String):MutableList<Audio>{
        val sortOrder = contentSettings.getSortOrder()
        val durationFilter = contentSettings.getDurationFilter()
        val selection = "$DURATION >=? AND $ALBUM =? AND $IS_MUSIC"
        val cursor = contentResolver.query(EXTERNAL_CONTENT_URI, projection,
                selection, arrayOf(durationFilter.toString(), albumName), sortOrder)
        return loopCursor(cursor)
    }

    @SuppressLint("InlinedApi")
    fun getArtistAudio(artistName:String):MutableList<Audio>{
        val sortOrder = contentSettings.getSortOrder()
        val durationFilter = contentSettings.getDurationFilter()
        val selection = "$DURATION >=? AND $ARTIST =? AND $IS_MUSIC"
        val cursor = contentResolver.query(EXTERNAL_CONTENT_URI, projection,
                selection, arrayOf(durationFilter.toString(), artistName), sortOrder)
        return loopCursor(cursor)
    }

    @SuppressLint("InlinedApi")
    fun getAudio(id:Long):Audio{
        val sortOrder = contentSettings.getSortOrder()
        val durationFilter = contentSettings.getDurationFilter()
        val selection = "$DURATION >=? AND $_ID =? AND $IS_MUSIC"
        val cursor = contentResolver.query(EXTERNAL_CONTENT_URI, projection,
            selection, arrayOf(durationFilter.toString(), id.toString()), sortOrder)
        return loopCursor(cursor)[0]
    }

    @SuppressLint("InlinedApi")
    fun isAudioAvailable(id:Long):Boolean{
        val sortOrder = contentSettings.getSortOrder()
        val durationFilter = contentSettings.getDurationFilter()
        val selection = "$DURATION >=? AND $_ID =? AND $IS_MUSIC"
        val cursor = contentResolver.query(EXTERNAL_CONTENT_URI, projection,
                selection, arrayOf(durationFilter.toString(), id.toString()), sortOrder)
        if (loopCursor(cursor).size > 0)
            return true
        return false
    }

    fun getAudio(uri: Uri):MutableList<Audio>{
        val sortOrder = contentSettings.getSortOrder()
        val cursor = contentResolver.query(uri, projection, IS_MUSIC,
                null , sortOrder)
        return loopCursor(cursor)
    }

    fun getAudio(audioIdList:List<Long>?):MutableList<Audio>{
        val durationFilter = contentSettings.getDurationFilter()
        val cursor = contentResolver.query(EXTERNAL_CONTENT_URI, projection,
                selection, arrayOf(durationFilter.toString()), null)
        return loopCursor(cursor, audioIdList)
    }

    fun deleteAudio(audio: Audio){
        contentResolver.delete(EXTERNAL_CONTENT_URI, "$_ID =?", arrayOf(audio.id.toString()))
    }

    fun updateAudio(audio: Audio, contentValues: ContentValues){
        contentResolver.update(audio.getUri(), contentValues, "$_ID =?", arrayOf(audio.id.toString()))
    }

    @SuppressLint("InlinedApi")
    private fun loopCursor(cursor: Cursor?):MutableList<Audio>{
        val linkedHashMap = LinkedHashMap<Long,Audio>()
        cursor?.let {
            if (cursor.moveToFirst()){
                do {
                    val id = cursor.getLong(cursor.getColumnIndex(_ID))
                    val title = cursor.getString(cursor.getColumnIndex(TITLE))
                    val artist = cursor.getString(cursor.getColumnIndex(ARTIST))
                    val album = cursor.getString(cursor.getColumnIndex(ALBUM))
                    val duration = cursor.getLong(cursor.getColumnIndex(DURATION))
                    val durationFormatted = TimeConverter.toMinutes(duration)
                    val size = Formatter.formatShortFileSize(context, cursor.getLong(cursor.getColumnIndex(SIZE)))
                    val albumArt = LocalArtUri.getAlbumArt(cursor.getLong(cursor.getColumnIndex(ALBUM_ID)))
                    val uri = ContentUris.withAppendedId(EXTERNAL_CONTENT_URI, id).toString()
                    val audio = Audio(id, title, artist, album, duration, durationFormatted
                            , size, albumArt, uri)
                    linkedHashMap[id] = audio
                }while (cursor.moveToNext())
            }
            cursor.close()
        }
        return linkedHashMap.values.toMutableList()
    }

    @SuppressLint("InlinedApi")
    private fun loopCursor(cursor:Cursor?, idList: List<Long>?):MutableList<Audio>{
        val linkedHashMap = LinkedHashMap<Long,Audio>()
        cursor?.let {
            if (cursor.moveToFirst()){
                do {
                    val id = cursor.getLong(cursor.getColumnIndex(_ID))
                    val title = cursor.getString(cursor.getColumnIndex(TITLE))
                    val artist = cursor.getString(cursor.getColumnIndex(ARTIST))
                    val album = cursor.getString(cursor.getColumnIndex(ALBUM))
                    val duration = cursor.getLong(cursor.getColumnIndex(DURATION))
                    val durationFormatted = TimeConverter.toMinutes(duration)
                    val size = Formatter.formatShortFileSize(context, cursor.getLong(cursor.getColumnIndex(SIZE)))
                    val albumArt = LocalArtUri.getAlbumArt(cursor.getLong(cursor.getColumnIndex(ALBUM_ID)))
                    val uri = ContentUris.withAppendedId(EXTERNAL_CONTENT_URI, id).toString()
                    val audio = Audio(id, title, artist, album, duration, durationFormatted
                        , size, albumArt, uri)
                    if(idList != null && idList.contains(id))
                        linkedHashMap[id] = audio
                }while (cursor.moveToNext())
            }
            cursor.close()
        }
        return linkedHashMap.values.toMutableList()
    }

}
