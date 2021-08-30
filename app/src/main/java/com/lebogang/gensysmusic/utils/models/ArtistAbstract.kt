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

package com.lebogang.gensysmusic.utils.models

import android.os.Parcelable

abstract class ArtistAbstract: Parcelable {

    abstract fun getItemId():Any

    abstract fun getItemTitle():String

    abstract fun getAudioCount():String

    abstract fun getItemSize():String

    abstract fun getIsItemFavourite():Boolean

    abstract fun getItemContent():String

    abstract fun getItemArt():String

    abstract fun getItemDuration():String

}
