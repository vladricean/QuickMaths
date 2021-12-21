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

    private val difficultyLevels = mutableListOf<Int>()

    val userAnswer = MutableLiveData<Int>()

    init {
        setupRulesList()
        _score.value = -1
        onCorrect()
    }

    private fun setupRulesList() {
        difficultyLevels.clear()
        rulesList.clear()
        if(sharedEncryptedPrefs.instance.getBoolean(Constants.SWITCH_ADDITION, true)) rulesList.add("addition")
        if(sharedEncryptedPrefs.instance.getBoolean(Constants.SWITCH_SUBTRACTION, true)) rulesList.add("subtraction")
        if(sharedEncryptedPrefs.instance.getBoolean(Constants.SWITCH_MULTIPLICATION, true)) rulesList.add("multiplication")
        if(sharedEncryptedPrefs.instance.getBoolean(Constants.SWITCH_DIVISION, true)) rulesList.add("division")
        if(sharedEncryptedPrefs.instance.getBoolean(Constants.SWITCH_SQUARE_ROOT, true)) rulesList.add("squareRoot")
        if(sharedEncryptedPrefs.instance.getBoolean(Constants.SWITCH_EXPONENTIAL, true)) rulesList.add("exponential")

        /**
         * 1 - easy
         * 2 - moderate
         * 3 - hard
         */
        difficultyLevels.addAll(listOf(1, 1, 1, 1, 1, 2))
    }

    private fun checkUserAnswer() {
        if (userAnswer.value == answer) {
            onCorrect()
        }
    }

    private fun onCorrect() {
        _score.value = (_score.value)?.plus(1)
        if(score.value?.rem(15) == 0){
            difficultyLevels.add(2)
        }
        if(score.value?.rem(30) == 0){
            difficultyLevels.add(3)
        }

        userAnswer.value = 0
        timer.cancel()
        timer.start()

        when(getRandomRule()) {
            "addition" -> generateAdditionExercise(getRandomDifficulty())
            "subtraction" -> generateSubtractionExercise(getRandomDifficulty())
            "multiplication" -> generateMultiplicationExercise(getRandomDifficulty())
            "division" -> generateDivisionExercise(getRandomDifficulty())
            "squareRoot" -> generateSquareRootExercise(getRandomDifficulty())
            "exponential" -> generateExponentialExercise(getRandomDifficulty())
        }
    }

    private fun getRandomDifficulty(): Int {
        val randomIndex = Random.nextInt(0, difficultyLevels.size)
        return difficultyLevels[randomIndex]
    }

    private fun getRandomRule(): String? {
        if(rulesList.isEmpty()) {
            sharedEncryptedPrefs.instance.edit().putBoolean(Constants.SWITCH_ADDITION, true).apply()
            return "addition"
        }
        val randomIndex = Random.nextInt(0, rulesList.size)
        return rulesList[randomIndex]
    }

    private fun generateAdditionExercise(difficulty: Int) {
        when(difficulty){
            1 -> {
                firstNumber = Random.nextInt(1, 15)
                secondNumber = Random.nextInt(1, 15)
            }
            2 -> {
                firstNumber = Random.nextInt(1, 50)
                secondNumber = Random.nextInt(1, 50)
            }
            3 -> {
                firstNumber = Random.nextInt(1, 120)
                secondNumber = Random.nextInt(1, 120)
            }
        }
        _question.value = "${firstNumber} + ${secondNumber}"
        answer = firstNumber + secondNumber
    }

    private fun generateSubtractionExercise(difficulty: Int) {
        firstNumber = Random.nextInt(5, 12)
        secondNumber = Random.nextInt(1, firstNumber-2)
        _question.value = "${firstNumber} - ${secondNumber}"
        answer = firstNumber - secondNumber
    }

    private fun generateMultiplicationExercise(difficulty: Int) {
        firstNumber = Random.nextInt(2, 12)
        secondNumber = Random.nextInt(2, 12)
        _question.value = "${firstNumber} * ${secondNumber}"
        answer = firstNumber * secondNumber
    }

    private fun generateDivisionExercise(difficulty: Int) {
        firstNumber = Random.nextInt(8, 9)
        secondNumber = Random.nextInt(2, 3)
        _question.value = "${firstNumber} / ${secondNumber}"
        answer = firstNumber / secondNumber
    }

    private fun generateSquareRootExercise(difficulty: Int) {
        firstNumber = Random.nextInt(9, 10)
        _question.value = "√${firstNumber}"
        answer = sqrt(firstNumber.toDouble()).toInt()
    }

    private fun generateExponentialExercise(difficulty: Int) {
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