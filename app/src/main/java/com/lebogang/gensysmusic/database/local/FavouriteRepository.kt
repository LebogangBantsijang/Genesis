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

package com.lebogang.gensysmusic.database.local

import androidx.annotation.WorkerThread
import com.lebogang.gensysmusic.database.local.models.Favourite

class FavouriteRepository(private val favouriteAccess: FavouriteAccess) {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun getMediaIds() = favouriteAccess.getMediaIds()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun addMediaId(favourite: Favourite) = favouriteAccess.addItem(favourite)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun removeMediaId(favourite: Favourite) = favouriteAccess.deleteItem(favourite)
}
