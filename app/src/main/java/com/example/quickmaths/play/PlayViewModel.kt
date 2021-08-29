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
    private var answer = ""

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    private val _question = MutableLiveData<String>()
    val question: LiveData<String>
        get() = _question
    
    val userAnswer = MutableLiveData<String>()
    
    init {
        _score.value = 0
        firstNumber = Random.nextInt(1, 10)
        secondNumber = Random.nextInt(1, 10)
        _question.value = "${firstNumber} + ${secondNumber}"
        answer = "${firstNumber + secondNumber}"
    }

    fun checkUserAnswer(){
        if(userAnswer.value.equals(answer)){
            onCorrect()
        }
    }

    fun onCorrect(){
        _score.value = (_score.value)?.plus(1)
        firstNumber = Random.nextInt(1, 10)
        secondNumber = Random.nextInt(1, 10)
        _question.value = "${firstNumber} + ${secondNumber}"
        answer = "${firstNumber + secondNumber}"
    }
    

}