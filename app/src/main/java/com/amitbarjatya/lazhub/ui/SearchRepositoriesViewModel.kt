package com.amitbarjatya.lazhub.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.amitbarjatya.lazhub.data.GithubRepository
import com.amitbarjatya.lazhub.model.Repo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

/*
Copyright (c) 2012-2020 Grab Taxi Holdings PTE LTD (GRAB), All Rights Reserved. NOTICE: All information contained herein is, and
remains the property of GRAB. The intellectual and technical concepts contained herein are confidential, proprietary and controlled by
GRAB and may be covered by patents, patents in process, and are protected by trade secret or copyright law.
You are strictly forbidden to copy, download, store (in any medium), transmit, disseminate, adapt or change this material in any way
unless prior written permission is obtained from GRAB. Access to the source code contained herein is hereby forbidden to anyone except
current GRAB employees or contractors with binding Confidentiality and Non-disclosure agreements explicitly covering such access.
The copyright notice above does not evidence any actual or intended publication or disclosure of this source code, which includes
information that is confidential and/or proprietary, and is a trade secret, of GRAB.
ANY REPRODUCTION, MODIFICATION, DISTRIBUTION, PUBLIC PERFORMANCE, OR PUBLIC DISPLAY OF OR THROUGH USE OF THIS SOURCE CODE WITHOUT THE
EXPRESS WRITTEN CONSENT OF GRAB IS STRICTLY PROHIBITED, AND IN VIOLATION OF APPLICABLE LAWS AND INTERNATIONAL TREATIES. THE RECEIPT OR
POSSESSION OF THIS SOURCE CODE AND/OR RELATED INFORMATION DOES NOT CONVEY OR IMPLY ANY RIGHTS TO REPRODUCE, DISCLOSE OR DISTRIBUTE
ITS CONTENTS, OR TO MANUFACTURE, USE, OR SELL ANYTHING THAT IT MAY DESCRIBE, IN WHOLE OR IN PART.
*/

/**
 * ViewModel for the [SearchRepositoriesActivity] screen.
 * The ViewModel works with the [GithubRepository] to get the data.
 */
@ExperimentalCoroutinesApi
class SearchRepositoriesViewModel(private val repository: GithubRepository) : ViewModel() {

    private var currentQueryValue: String? = null

    private var currentSearchResult: Flow<PagingData<Repo>>? = null

    fun searchRepo(queryString: String): Flow<PagingData<Repo>> {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = queryString
        val newResult: Flow<PagingData<Repo>> = repository.getSearchResultStream(queryString)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}