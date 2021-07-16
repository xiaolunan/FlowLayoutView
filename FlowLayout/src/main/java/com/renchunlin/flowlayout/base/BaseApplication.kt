package com.renchunlin.flowlayout.base

import android.app.Application
import android.content.Context

/*
 * class title: 
 * Author by RenChunLin, Email 18957806320@163.com, Date on 2021/6/29.
 * PS: Not easy to write code, please indicate.
 */
class BaseApplication : Application() {

    companion object {
        lateinit var sContext: Context
        fun getAppContext(): Context = sContext
    }

    override fun onCreate() {
        super.onCreate()
        sContext = applicationContext
    }

}