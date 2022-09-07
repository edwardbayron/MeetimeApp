package com.paybrother

import android.app.Application
import android.content.Intent
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MeetimeApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        val intent = Intent(applicationContext, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)

    }

}