package com.someca.count

import android.app.Application
import kotlin.properties.Delegates

class App : Application(){
    companion object{
        var instance by Delegates.notNull<App>()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}