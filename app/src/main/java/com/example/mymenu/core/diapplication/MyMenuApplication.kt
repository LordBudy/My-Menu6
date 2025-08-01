package com.example.mymenu.core.diapplication

import android.app.Application
import com.example.mymenu.core.basket.di.basketdi
import com.example.mymenu.core.category.di.categorydi
import com.example.mymenu.core.fastsearch.di.fastsearchdi
import com.example.mymenu.core.menu.di.menudi
import com.example.mymenu.core.menumini.di.menuminidi

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
                     categorydi,
                    menudi,
                    fastsearchdi,
                    menuminidi,
                    basketdi
                )
            )
        }
    }
}