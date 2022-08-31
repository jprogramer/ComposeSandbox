package com.example.sandbox.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Entry.TABLE)
data class Entry(
    @PrimaryKey @ColumnInfo(name = ID) val id: Long,
    val value: String,
    val prevKey: Int,
    val nextKey: Int,
) {
    companion object {
        fun fakeData(valuePrefix: String): List<Entry> {
            val values = 100..999
            val entries = Array(10) {
                Entry(
                    it.toLong(),
                    valuePrefix + "_value:" + values.random(),
                    Keys.NULL_PAGE,
                    Keys.NULL_PAGE
                )
            }
            return entries.toList()
        }

        const val TABLE = "entry"
        const val ID = TABLE + "_id"
    }
}