package com.paybrother.old//package com.paybrother
//
//import android.app.Application
//import android.content.Intent
//import com.orhanobut.logger.AndroidLogAdapter
//import com.orhanobut.logger.Logger
//import dagger.hilt.android.HiltAndroidApp
//
//
//class MeetimeApplication : Application(){
//
//    override fun onCreate() {
//        super.onCreate()
//        val intent = Intent(applicationContext, HomeActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        startActivity(intent)
//
//        Logger.addLogAdapter(AndroidLogAdapter())
//    }
//
//}