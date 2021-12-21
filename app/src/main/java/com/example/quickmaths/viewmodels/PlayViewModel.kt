package com.example.quickmaths.viewmodels

import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.quickmaths.constants.Constants
import com.example.quickmaths.constants.Constants.DONE
import com.example.quickmaths.constants.Constants.ONE_SECOND
import com.example.quickmaths.sharedEncryptedPrefs
import kotlin.random.Random

class PlayViewModel() : ViewModel() {

    private var firstNumber = 0
    private var secondNumber = 0
    private var answer = 0

    private val timer = object : CountDownTimer(Constants.COUNTDOWN_TIME, ONE_SECOND) {
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

    private val rulesList = mutableListOf<Int>()

    val userAnswer = MutableLiveData<Int>()

    init {
        setupRulesList()
        _score.value = -1
        onCorrect()
    }

    private fun setupRulesList() {
        rulesList.clear()
        if(sharedEncryptedPrefs.instance.getBoolean(Constants.SWITCH_ADDITION, true)) rulesList.add(1)
        if(sharedEncryptedPrefs.instance.getBoolean(Constants.SWITCH_SUBTRACTION, true)) rulesList.add(2)
    }

    private fun checkUserAnswer() {
        if (userAnswer.value == answer) {
            onCorrect()
        }
    }

    private fun onCorrect() {
        _score.value = (_score.value)?.plus(1)
        userAnswer.value = 0
        timer.cancel()
        timer.start()

        when(getRandomRule()) {
            "addition" -> generateAdditionExercise()
            "subtraction" -> generateSubtractionExercise()
        }
    }

    private fun getRandomRule(): String? {
        if(rulesList.isEmpty()) {
            sharedEncryptedPrefs.instance.edit().putBoolean(Constants.SWITCH_ADDITION, true).apply()
            return "addition"
        }
        val rulesMap = mapOf(
            "1" to "addition",
            "2" to "subtraction",
        )
        val randomIndex = Random.nextInt(0, rulesList.size)
        return rulesMap[rulesList[randomIndex].toString()]
    }

    private fun generateAdditionExercise() {
        firstNumber = Random.nextInt(1, 9)
        secondNumber = Random.nextInt(1, 9)
        _question.value = "${firstNumber} + ${secondNumber}"
        answer = firstNumber + secondNumber
    }

    private fun generateSubtractionExercise() {
        firstNumber = Random.nextInt(5, 12)
        secondNumber = Random.nextInt(1, firstNumber-2)
        _question.value = "${firstNumber} - ${secondNumber}"
        answer = firstNumber - secondNumber
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