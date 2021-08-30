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

import com.lebogang.gensysmusic.database.local.models.Music
import com.lebogang.gensysmusic.ui.local.Sort
import com.lebogang.gensysmusic.utils.models.MusicAbstract

object SortHelper {

    fun sortListAbstract(sort: Sort,list: List<MusicAbstract>):List<MusicAbstract> = list.sortedWith { o1, o2 ->
        when(sort) {
            Sort.TITLE-> when {
                o1.getItemTitle() > o2.getItemTitle() -> 1
                o1.getItemTitle() < o2.getItemTitle() -> -1
                else -> 0
            }
            Sort.NEW -> when {
                o1.getItemDate() < o2.getItemDate() -> 1
                o1.getItemDate() > o2.getItemDate() -> -1
                else -> 0
            }
            Sort.DURATION -> when {
                o1.getItemDuration() < o2.getItemDuration() -> 1
                o1.getItemDuration() > o2.getItemDuration() -> -1
                else -> 0
            }
        }
    }

    fun sortList(sort: Sort,list: List<Music>):List<Music> = list.sortedWith { o1, o2 ->
        when(sort) {
            Sort.TITLE-> when {
                o1.getItemTitle() > o2.getItemTitle() -> 1
                o1.getItemTitle() < o2.getItemTitle() -> -1
                else -> 0
            }
            Sort.NEW -> when {
                o1.getItemDate() < o2.getItemDate() -> 1
                o1.getItemDate() > o2.getItemDate() -> -1
                else -> 0
            }
            Sort.DURATION -> when {
                o1.getItemDuration() < o2.getItemDuration() -> 1
                o1.getItemDuration() > o2.getItemDuration() -> -1
                else -> 0
            }
        }
    }

}
