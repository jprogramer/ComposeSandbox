package com.example.sandbox.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.sandbox.data.EntriesRepository
import com.example.sandbox.data.Entry
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class EntriesViewModel : ViewModel() {

    val repository = EntriesRepository.INSTANCE

    val pagingConfig = PagingConfig(64)

    val queryInput = MutableSharedFlow<String>()

    init {
        queryInput.conflate()
            .distinctUntilChanged()
            .debounce(1000)
            .onStart { emit("youcef") }
    }

    fun updateQuery(query: CharSequence) {
        viewModelScope.launch { queryInput.emit(query.toString()) }
    }

    fun getEntriesItemsData(): Flow<PagingData<Entry>> =
        queryInput.flatMapLatest { query -> getEntriesItems(query) }
            .cachedIn(viewModelScope)

    private fun getEntriesItems(query: String): Flow<PagingData<Entry>> =
        repository.getEntries(query, pagingConfig)
}