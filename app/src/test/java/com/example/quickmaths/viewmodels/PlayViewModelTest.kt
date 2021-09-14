package com.example.quickmaths.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.quickmaths.getOrAwaitValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.CoreMatchers.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PlayViewModelTest {

    private lateinit var playViewModel: PlayViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel(){
        playViewModel = PlayViewModel(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun onClickDelButton_userAnswerIsZero(){
        // When clicking Zero Button
        playViewModel.onClickNumber(-1)

        // User answer is set to zero
        assertThat(playViewModel.userAnswer.getOrAwaitValue(), `is`(0))
    }

}