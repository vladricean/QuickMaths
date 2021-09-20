package com.example.quickmaths

import org.junit.Test

class Validator {

    companion object {
        fun numbersAreInRange(firstNumber: Int, secondNumber: Int): Boolean {
            return when {
                firstNumber < 1 -> {
                    false
                }
                firstNumber > 30 -> {
                    false
                }
                secondNumber < 1 -> {
                    false
                }
                secondNumber > 30 -> {
                    false
                }
                else -> {
                    true
                }
            }
        }
    }



}