package com.example.quickmaths.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import timber.log.Timber
import java.lang.Error

class RefreshWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "com.example.quickmaths.work.RefreshWorker"
    }

    override suspend fun doWork(): Result {
        try {
            refreshPlayers()
        } catch (e: Error) {
            return Result.retry()
        }
        return Result.success()
    }

    private fun refreshPlayers(){
        val db: FirebaseFirestore = Firebase.firestore
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                Timber.i("The local cache is refreshed!")//
//             for (document in result) {
//                    Timber.i("${document.id} => ${document.data}")
//                }
            }
            .addOnFailureListener { exception ->
                Timber.w("Error getting documents.", exception)
            }
    }
}