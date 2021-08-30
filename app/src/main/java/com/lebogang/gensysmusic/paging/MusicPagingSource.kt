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

package com.lebogang.gensysmusic.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lebogang.gensysmusic.database.local.MusicRepository
import com.lebogang.gensysmusic.database.local.models.Music
import kotlinx.coroutines.flow.first

class MusicPagingSource(private val musicRepository: MusicRepository) : PagingSource<Int, Music>() {
    var query:String? = null

    override fun getRefreshKey(state: PagingState<Int, Music>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Music> {
        TODO("Not yet implemented")
    }

}
