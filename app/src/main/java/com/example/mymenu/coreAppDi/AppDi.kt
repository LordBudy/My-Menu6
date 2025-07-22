package com.example.mymenu.coreAppDi

import android.app.Application
import com.example.mymenu.category.di.catDataDi
import com.example.mymenu.category.di.catDomainDi
import com.example.mymenu.category.di.catPresentDi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AppDi : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@AppDi)
            modules(
                listOf(
                    catPresentDi, catDomainDi, catDataDi
                )
            )
        }
    }
}