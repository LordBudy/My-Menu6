package com.example.mymenu.core.diapplication

import android.app.Application
import com.example.mymenu.core.basket.di.basketdi
import com.example.mymenu.core.menu.di.menudi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyMenuApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MyMenuApplication)
            modules(
                listOf(
                    menudi,
                    basketdi
                )
            )
        }
    }
}