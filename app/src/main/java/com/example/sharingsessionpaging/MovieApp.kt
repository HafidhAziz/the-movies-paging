package com.example.sharingsessionpaging

import android.app.Application
import com.example.sharingsessionpaging.di.networkModule
import com.example.sharingsessionpaging.di.repositoryModule
import com.example.sharingsessionpaging.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@MovieApp)
            modules(listOf(networkModule, repositoryModule, viewModelModule))
        }
    }
}
