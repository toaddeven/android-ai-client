package com.openclaw.android

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class OpenClawApp : Application() {
    
    override fun onCreate() {
        super.onCreate()
    }
}
