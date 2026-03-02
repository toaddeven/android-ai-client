package com.openclaw.android.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class ScreenCaptureService : Service() {
    override fun onBind(intent: Intent?): IBinder? = null
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Screen recording logic
        return START_STICKY
    }
}
