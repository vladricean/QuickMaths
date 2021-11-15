package com.example.quickmaths

import android.app.Application
import com.example.quickmaths.services.QuickMathsEncryptedSharedPreferences
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

val encryptedPrefs: QuickMathsEncryptedSharedPreferences by lazy {
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
        delayedInit()

        instance = this
        encryptedPrefs = QuickMathsEncryptedSharedPreferences(applicationContext)
    }

    private fun delayedInit(){
        applicationScope.launch {
            if (BuildConfig.DEBUG) {
                Timber.plant(Timber.DebugTree())
            }
//            setupRecurringWork()
        }
    }

//    private fun setupRecurringWork(){
//        val constraints = Constraints.Builder()
//            .setRequiredNetworkType(NetworkType.UNMETERED)
//            .setRequiresBatteryNotLow(true)
//            .setRequiresCharging(true)
//            .apply {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    setRequiresDeviceIdle(true)
//                }
//            }
//            .build()
//
//        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.DAYS)
//            .setConstraints(constraints)
//            .build()
//
//        WorkManager.getInstance().enqueueUniquePeriodicWork(
//            RefreshDataWorker.WORK_NAME,
//            ExistingPeriodicWorkPolicy.KEEP,
//            repeatingRequest)
//    }
}