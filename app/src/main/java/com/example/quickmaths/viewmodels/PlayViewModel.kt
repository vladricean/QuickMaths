package com.example.quickmaths.viewmodels

import android.app.Application
import android.os.CountDownTimer
import android.provider.SyncStateContract
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.quickmaths.constants.Constants
import com.example.quickmaths.sharedEncryptedPrefs
import com.example.quickmaths.util.SingleLiveEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import timber.log.Timber
import kotlin.random.Random

class PlayViewModel() : ViewModel() {

    companion object {
        private const val DONE = 0L
        private const val ONE_SECOND = 1000L
        private const val COUNTDOWN_TIME = 2500L
    }

    private var firstNumber = 0
    private var secondNumber = 0
    private var answer = 0

    private val timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
        override fun onTick(millisUntilFinished: Long) {
            _currentTime.value = millisUntilFinished / ONE_SECOND
        }

        override fun onFinish() {
            _currentTime.value = DONE
            onLost()
        }
    }

    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime

    val currentTimeString = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    private val _question = MutableLiveData<String>()
    val question: LiveData<String>
        get() = _question

    private val _getScoreAndNavigateToLost = MutableLiveData<Int>()
    val getScoreAndNavigateToLost: LiveData<Int>
        get() = _getScoreAndNavigateToLost

    val userAnswer = MutableLiveData<Int>()

    init {
        val addition = sharedEncryptedPrefs.instance.getBoolean(Constants.SWITCH_ADDITION, true)
        val subtraction = sharedEncryptedPrefs.instance.getBoolean(Constants.SWITCH_SUBTRACTION, true)
        Timber.i("addition: ${addition} - subtraction: ${subtraction}")
        _score.value = 0
        userAnswer.value = 0
        firstNumber = Random.nextInt(1, 2)
        secondNumber = Random.nextInt(1, 2)
        _question.value = "${firstNumber} + ${secondNumber}"
        answer = firstNumber + secondNumber
        timer.start()
    }

    private fun checkUserAnswer() {
        if (userAnswer.value == answer) {
            onCorrect()
        }
    }

    private fun onCorrect() {
        _score.value = (_score.value)?.plus(1)
        firstNumber = Random.nextInt(1, 2)
        secondNumber = Random.nextInt(1, 2)
        _question.value = "${firstNumber} + ${secondNumber}"
        answer = firstNumber + secondNumber
        userAnswer.value = 0
        timer.cancel()
        timer.start()
    }

    private fun onLost() {
        _getScoreAndNavigateToLost.value = _score.value
    }

    fun onClickNumber(number: Int) {
        if (number == -1) { // Del button
            userAnswer.value = 0
        } else {
            if (userAnswer.value!! < 9999999) {
                userAnswer.value = userAnswer.value?.times(10)?.plus(number)
            }
        }
        checkUserAnswer()
    }

    fun onClickBackspace(){
        userAnswer.value = userAnswer.value?.div(10)
    }

}