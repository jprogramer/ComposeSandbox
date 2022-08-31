package com.example.sandbox.data

import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface EntryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: Entry)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<Entry>)

    @Query("delete from entry")
    suspend fun deleteAll()

    @Transaction
    suspend fun replaceAll(entities: List<Entry>) {
        deleteAll()
        insertAll(entities)
    }

    fun search(query: String): PagingSource<Int, Entry> {
        return searchExact("%$query%")
    }

    @Query(
        "select * from entry where " +
                "value LIKE :query " +
                "ORDER BY value ASC"
    )
    fun searchExact(query: String): PagingSource<Int, Entry>

    @Query("select prevKey, nextKey from entry where entry_id = :id")
    suspend fun findKeys(id: Long): Keys?
}