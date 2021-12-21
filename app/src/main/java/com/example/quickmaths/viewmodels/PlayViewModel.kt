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
import kotlin.math.exp
import kotlin.math.pow
import kotlin.math.sqrt
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

    private val rulesList = mutableListOf<String>()

    val userAnswer = MutableLiveData<Int>()

    init {
        setupRulesList()
        _score.value = -1
        onCorrect()
    }

    private fun setupRulesList() {
        rulesList.clear()
        if(sharedEncryptedPrefs.instance.getBoolean(Constants.SWITCH_ADDITION, true)) rulesList.add("addition")
        if(sharedEncryptedPrefs.instance.getBoolean(Constants.SWITCH_SUBTRACTION, true)) rulesList.add("subtraction")
        if(sharedEncryptedPrefs.instance.getBoolean(Constants.SWITCH_MULTIPLICATION, true)) rulesList.add("multiplication")
        if(sharedEncryptedPrefs.instance.getBoolean(Constants.SWITCH_DIVISION, true)) rulesList.add("division")
        if(sharedEncryptedPrefs.instance.getBoolean(Constants.SWITCH_SQUARE_ROOT, true)) rulesList.add("squareRoot")
        if(sharedEncryptedPrefs.instance.getBoolean(Constants.SWITCH_EXPONENTIAL, true)) rulesList.add("exponential")
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
            "multiplication" -> generateMultiplicationExercise()
            "division" -> generateDivisionExercise()
            "squareRoot" -> generateSquareRootExercise()
            "exponential" -> generateExponentialExercise()
        }
    }

    private fun getRandomRule(): String? {
        if(rulesList.isEmpty()) {
            sharedEncryptedPrefs.instance.edit().putBoolean(Constants.SWITCH_ADDITION, true).apply()
            return "addition"
        }
        val randomIndex = Random.nextInt(0, rulesList.size)
        return rulesList[randomIndex]
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

    private fun generateMultiplicationExercise() {
        firstNumber = Random.nextInt(2, 12)
        secondNumber = Random.nextInt(2, 12)
        _question.value = "${firstNumber} * ${secondNumber}"
        answer = firstNumber * secondNumber
    }

    private fun generateDivisionExercise() {
        firstNumber = Random.nextInt(8, 9)
        secondNumber = Random.nextInt(2, 3)
        _question.value = "${firstNumber} / ${secondNumber}"
        answer = firstNumber / secondNumber
    }

    private fun generateSquareRootExercise() {
        firstNumber = Random.nextInt(9, 10)
        _question.value = "√${firstNumber}"
        answer = sqrt(firstNumber.toDouble()).toInt()
    }

    private fun generateExponentialExercise() {
        firstNumber = Random.nextInt(2, 4)
        secondNumber = Random.nextInt(2, 4)
        _question.value = "${firstNumber} ^ ${secondNumber}"
        answer = firstNumber.toDouble().pow(secondNumber).toInt()
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