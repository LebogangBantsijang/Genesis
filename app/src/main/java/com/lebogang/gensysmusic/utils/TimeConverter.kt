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

package com.lebogang.gensysmusic.utils

import java.util.concurrent.TimeUnit

object TimeConverter {
    private const val MINUTE = 60000

    fun toMinutes(time: Long):String{
        var duration: String
        val min = TimeUnit.MILLISECONDS.toMinutes(time)
        duration = if (min < 10) "0$min" else "" + min
        val temp: Long = time - min * MINUTE
        val sec = TimeUnit.MILLISECONDS.toSeconds(temp)
        duration += if (sec < 10) ":0$sec" else ":$sec"
        return duration
    }

    fun toGeneralTime(time: Long): String {
        return "" + TimeUnit.MINUTES.convert(time, TimeUnit.MILLISECONDS) + " minutes"
    }
}
