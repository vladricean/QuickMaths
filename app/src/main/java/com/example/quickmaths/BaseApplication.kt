package com.example.quickmaths

import android.app.Application
import androidx.work.*
import com.example.quickmaths.services.QuickMathsEncryptedSharedPreferences
import com.example.quickmaths.work.RefreshWorker
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

val sharedEncryptedPrefs: QuickMathsEncryptedSharedPreferences by lazy {
    BaseApplication.encryptedPrefs!!
}

@HiltAndroidApp
class BaseApplication : Application() {

    companion object {
        var encryptedPrefs: QuickMathsEncryptedSharedPreferences? = null
        lateinit var instance: BaseApplication
            private set
    }

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        instance = this
        encryptedPrefs = QuickMathsEncryptedSharedPreferences(applicationContext)

        delayedInit()
    }

    private fun delayedInit(){
        applicationScope.launch {
            if (BuildConfig.DEBUG) {
                Timber.plant(Timber.DebugTree())
            }
            setupRecurringWork()
        }
    }

    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .setRequiresDeviceIdle(true)
            .build()

        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RefreshWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest)
    }
}