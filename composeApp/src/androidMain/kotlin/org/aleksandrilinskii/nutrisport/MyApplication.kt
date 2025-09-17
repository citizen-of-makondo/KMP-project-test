package org.aleksandrilinskii.nutrisport

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.initialize
import org.aleksandrilinskii.di.initializeKoinModule
import org.koin.android.ext.koin.androidContext

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKoinModule(
            config = {
                androidContext(this@MyApplication)
            }
        )
        Firebase.initialize(this)
    }
}