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

package com.lebogang.gensysmusic.musicservice

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.*
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.ui.MainActivity
import com.lebogang.gensysmusic.ui.local.HomeActivity
import com.lebogang.gensysmusic.utils.GlideManager
import com.lebogang.gensysmusic.utils.models.MusicAbstract

class NotificationManager(private val context: Context) {
    private val channelId = "113"
    private val channelName = "GensysMusic"
    private val channelDescription = "Enjoy your music"
    val notificationId = 113
    @RequiresApi(Build.VERSION_CODES.N)
    private val channelImportance = NotificationManager.IMPORTANCE_LOW
    @RequiresApi(Build.VERSION_CODES.O)
    private val channel = NotificationChannel(channelId, channelName, channelImportance).apply {
        description = channelDescription
        enableLights(false)
        enableVibration(false)
    }
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE)
            as NotificationManager

    @RequiresApi(Build.VERSION_CODES.O)
    fun createChannel() = notificationManager.createNotificationChannel(channel)

    @SuppressLint("UnspecifiedImmutableFlag")
    fun getNotification(music:MusicAbstract, playbackState: PlaybackState):Notification{
        val  builder = Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_itunes)
            .setCategory(Notification.CATEGORY_TRANSPORT)
            .setContentTitle(music.getItemTitle())
            .setContentText(music.getItemArtist())
            .setColor(0)
            .setColorized(true)
            .setLargeIcon(GlideManager.getBitmap(Uri.parse(music.getItemArt()),context))
            .setShowWhen(false)
            .setVisibility(VISIBILITY_PUBLIC)
            .setPriority(PRIORITY_MIN)
            .setContentIntent(
                PendingIntent.getActivity(context, 146,
                    Intent(context, HomeActivity::class.java), FLAG_UPDATE_CURRENT)
            )
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle().also {
                it.setShowCancelButton(false)
                it.setShowActionsInCompactView(0,1,2)
            })
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val color = Color.valueOf(1f,0.6f,0f).toArgb()
            builder.color = color
            builder.setColorized(true)
        }
        return addActions(builder,playbackState)
    }

    private fun addActions(builder: Builder,playbackState: PlaybackState):Notification{
        builder.addAction(
            R.drawable.ic_round_navigate_before_24, "",
            PendingIntent.getBroadcast(context,0,Intent(MusicBroadcastReceiver.SKIP_PREV_ACTION)
                ,FLAG_UPDATE_CURRENT)
        )
        if (playbackState == PlaybackState.PLAYING)
            builder.addAction(
                R.drawable.ic_round_pause_24, "",
                PendingIntent.getBroadcast(context, 0, Intent(MusicBroadcastReceiver.PAUSE_ACTION)
                    ,FLAG_UPDATE_CURRENT)
            )
        else
            builder.addAction(
                R.drawable.ic_round_play_arrow_24, "",
                PendingIntent.getBroadcast(context, 0, Intent(MusicBroadcastReceiver.PLAY_ACTION)
                    ,FLAG_UPDATE_CURRENT)
            )
        builder.addAction(
            R.drawable.ic_round_navigate_next_24, "",
            PendingIntent.getBroadcast(context, 0, Intent(MusicBroadcastReceiver.SKIP_NEXT_ACTION),
                FLAG_UPDATE_CURRENT)
        )
        return builder.build()
    }
}
