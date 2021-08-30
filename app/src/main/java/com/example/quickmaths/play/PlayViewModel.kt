package com.example.quickmaths.play

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quickmaths.SingleLiveEvent
import kotlin.random.Random

class PlayViewModel : ViewModel() {

    private var firstNumber = 0
    private var secondNumber = 0
    private var answer = 0

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
        firstNumber = Random.nextInt(1, 30)
        secondNumber = Random.nextInt(1, 30)
        _question.value = "${firstNumber} + ${secondNumber}"
        answer = firstNumber + secondNumber
    }

    fun checkUserAnswer(){
        if(userAnswer.value == answer){
            onCorrect()
        }
    }

    fun onCorrect(){
        _score.value = (_score.value)?.plus(1)
        firstNumber = Random.nextInt(1, 30)
        secondNumber = Random.nextInt(1, 30)
        _question.value = "${firstNumber} + ${secondNumber}"
        userAnswer.value = 0
        answer = firstNumber + secondNumber
    }

    fun onClickOne(){
        userAnswer.value = userAnswer.value?.times(10)?.plus(1)
        checkUserAnswer()
    }
    fun onClickTwo(){
        userAnswer.value = userAnswer.value?.times(10)?.plus(2)
        checkUserAnswer()
    }
    fun onClickThree(){
        userAnswer.value = userAnswer.value?.times(10)?.plus(3)
        checkUserAnswer()
    }
    fun onClickFour(){
        userAnswer.value = userAnswer.value?.times(10)?.plus(4)
        checkUserAnswer()
    }
    fun onClickFive(){
        userAnswer.value = userAnswer.value?.times(10)?.plus(5)
        checkUserAnswer()
    }
    fun onClickSix(){
        userAnswer.value = userAnswer.value?.times(10)?.plus(6)
        checkUserAnswer()
    }
    fun onClickSeven(){
        userAnswer.value = userAnswer.value?.times(10)?.plus(7)
        checkUserAnswer()
    }
    fun onClickEight(){
        userAnswer.value = userAnswer.value?.times(10)?.plus(8)
        checkUserAnswer()
    }
    fun onClickNine(){
        userAnswer.value = userAnswer.value?.times(10)?.plus(9)
        checkUserAnswer()
    }
    fun onClickZero(){
        userAnswer.value = userAnswer.value?.times(10)
        checkUserAnswer()
    }
    fun onClickBackspace(){
        userAnswer.value = userAnswer.value?.div(10)
    }
    fun onClickClear(){
        userAnswer.value = 0
    }

}