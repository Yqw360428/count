package com.someca.count

import android.app.Application
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustConfig
import com.adjust.sdk.LogLevel
import kotlin.properties.Delegates

class App : Application() {
    companion object {
        var instance by Delegates.notNull<App>()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        Adjust.initSdk(
            AdjustConfig(
                this,
                "rnv2v2xu6xog",
                AdjustConfig.ENVIRONMENT_PRODUCTION
            ).apply {
                setLogLevel(LogLevel.WARN)
            }
        )
    }
}