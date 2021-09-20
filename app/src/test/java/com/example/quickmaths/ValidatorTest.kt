package com.example.quickmaths

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ValidatorTest {

    @Test
    fun validator_firstAndSecondNumberInInterval_returnsTrue() {
        assertThat(Validator.numbersAreInRange(1,35)).isTrue()
    }

    @Test
    fun validator_firstAndSecondNumberInInterval_returnsFalse() {
        assertThat(Validator.numbersAreInRange(1,35)).isFalse()
    }

}