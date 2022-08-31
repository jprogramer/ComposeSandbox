package com.example.sandbox.data

import android.util.Log
import androidx.paging.*
import com.example.sandbox.util.io
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.cancellation.CancellationException

class EntriesRepository private constructor() {
    companion object {
        val INSTANCE = EntriesRepository()
    }

    fun getEntries(query: String, pagingConfig: PagingConfig): Flow<PagingData<Entry>> =
        @OptIn(ExperimentalPagingApi::class)
        Pager(
            config = pagingConfig,
            remoteMediator = EntriesRemoteMediator(query),
            pagingSourceFactory = {
                Log.i("£££", "paging source factory called")
                SandboxDatabase.INSTANCE.entryDAO().search(query)
            }
        ).flow

    @OptIn(ExperimentalPagingApi::class)
    inner class EntriesRemoteMediator(
        private val query: String,
    ) : RemoteMediator<Int, Entry>() {

        override suspend fun initialize(): InitializeAction {
            Log.i("£££", "RemoteMediator.initialize() called")
            return InitializeAction.LAUNCH_INITIAL_REFRESH
        }

        override suspend fun load(
            loadType: LoadType,
            state: PagingState<Int, Entry>
        ): MediatorResult {
            Log.i("£££", "RemoteMediator.load is called (state.isEmpty=${state.isEmpty()})")
            return io {
                // an ugly manual injection to replace HILT
                val dao = SandboxDatabase.INSTANCE.entryDAO()

                val page: Int? = when (loadType) {
                    LoadType.APPEND -> getLastItemKeys(dao, state)?.nextKey
                    LoadType.PREPEND -> getFirstItemKeys(dao, state)?.prevKey
                    LoadType.REFRESH -> getCurrentItemKey(dao, state).let {
                        if (it == null || it.prevKey == Keys.NULL_PAGE) Keys.FIRST_PAGE else it.prevKey + 1
                    }
                }

                if (page == null)
                    return@io MediatorResult.Success(false)
                else if (page == Keys.NULL_PAGE)
                    return@io MediatorResult.Success(true)

                try {
                    // using fake API call with only one page for now
                    // later this will call an actual API that support paging
                    delay(3000)
                    dao.replaceAll(Entry.fakeData("api"))
                    return@io MediatorResult.Success(endOfPaginationReached = true)
                } catch (e: CancellationException) {
                    throw e
                } catch (e: Exception) {
                    return@io MediatorResult.Error(e)
                }
            }
        }

        private suspend fun getLastItemKeys(
            dao: EntryDAO,
            state: PagingState<Int, Entry>,
        ): Keys? = state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data
            ?.lastOrNull()
            ?.let { dao.findKeys(it.id) }

        private suspend fun getFirstItemKeys(
            dao: EntryDAO,
            state: PagingState<Int, Entry>,
        ): Keys? = state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data
            ?.firstOrNull()
            ?.let { dao.findKeys(it.id) }

        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        private suspend fun getCurrentItemKey(
            dao: EntryDAO,
            state: PagingState<Int, Entry>,
        ): Keys? =
            state.anchorPosition?.let { position ->
                state.closestItemToPosition(position)?.id?.let { id ->
                    dao.findKeys(id)
                }
            }
    }
}