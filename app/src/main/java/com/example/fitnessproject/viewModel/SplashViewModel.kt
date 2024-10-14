package com.example.fitnessproject.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {
    private val splashShowFlow = MutableStateFlow(true)
    val isSplashShow = splashShowFlow.asStateFlow()

    init {
        viewModelScope.launch {
            delay(3000)
            splashShowFlow.value = false
        }
    }
}