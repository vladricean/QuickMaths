package com.example.quickmaths.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.quickmaths.database.getDatabase
import com.example.quickmaths.repository.PlayersRepository
import retrofit2.HttpException
import timber.log.Timber

class RefreshDataWorker(appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "com.example.quickmaths.work.RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = PlayersRepository(database)

        try {
            repository.refreshPlayers()
            Timber.d("Work request for sync is run")
        } catch (e: HttpException){
            return Result.retry()
        }

        return Result.success()
    }
}