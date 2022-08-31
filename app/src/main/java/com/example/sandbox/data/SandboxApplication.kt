package com.example.sandbox.data

import android.app.Application

class SandboxApplication : Application() {

    companion object {
        lateinit var INSTANCE: SandboxApplication
    }

    init {
        INSTANCE = this
    }
}