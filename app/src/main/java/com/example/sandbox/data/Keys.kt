package com.example.sandbox.data

data class Keys(val prevKey: Int, val nextKey: Int) {
    companion object {
        const val FIRST_PAGE: Int = 0
        const val NULL_PAGE: Int = -1
    }
}