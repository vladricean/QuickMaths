package com.example.quickmaths.play

import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateUtils
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.quickmaths.SingleLiveEvent
import kotlin.random.Random

class PlayViewModel : ViewModel() {

    companion object {
        private const val DONE = 0L
        private const val ONE_SECOND = 1000L
        private const val COUNTDOWN_TIME = 5900L
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
            onNavigateToLostEvent.call()
        }
    }

    private val onNavigateToLostEvent = SingleLiveEvent<Void>()

    fun onNavigateToLostEvent(): LiveData<Void> {
        return onNavigateToLostEvent
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

    val userAnswer = MutableLiveData<Int>()

    init {
        _score.value = 0
        userAnswer.value = 0
        firstNumber = Random.nextInt(1, 10)
        secondNumber = Random.nextInt(1, 10)
        _question.value = "${firstNumber} + ${secondNumber}"
        answer = firstNumber + secondNumber
        timer.start()
    }

    fun checkUserAnswer() {
        if (userAnswer.value == answer) {
            onCorrect()
        }
    }

    fun onCorrect() {
        _score.value = (_score.value)?.plus(1)
        firstNumber = Random.nextInt(1, 10)
        secondNumber = Random.nextInt(1, 10)
        _question.value = "${firstNumber} + ${secondNumber}"
        userAnswer.value = 0
        answer = firstNumber + secondNumber
        timer.cancel()
        timer.start()
    }

    fun onClickOne() {
        userAnswer.value = userAnswer.value?.times(10)?.plus(1)
        checkUserAnswer()
    }

    fun onClickTwo() {
        userAnswer.value = userAnswer.value?.times(10)?.plus(2)
        checkUserAnswer()
    }

    fun onClickThree() {
        userAnswer.value = userAnswer.value?.times(10)?.plus(3)
        checkUserAnswer()
    }

    fun onClickFour() {
        userAnswer.value = userAnswer.value?.times(10)?.plus(4)
        checkUserAnswer()
    }

    fun onClickFive() {
        userAnswer.value = userAnswer.value?.times(10)?.plus(5)
        checkUserAnswer()
    }

    fun onClickSix() {
        userAnswer.value = userAnswer.value?.times(10)?.plus(6)
        checkUserAnswer()
    }

    fun onClickSeven() {
        userAnswer.value = userAnswer.value?.times(10)?.plus(7)
        checkUserAnswer()
    }

    fun onClickEight() {
        userAnswer.value = userAnswer.value?.times(10)?.plus(8)
        checkUserAnswer()
    }

    fun onClickNine() {
        userAnswer.value = userAnswer.value?.times(10)?.plus(9)
        checkUserAnswer()
    }

    fun onClickZero() {
        userAnswer.value = userAnswer.value?.times(10)
        checkUserAnswer()
    }

    fun onClickClear() {
        userAnswer.value = 0
    }

}