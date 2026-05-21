package com.sirvi.unirem_v1.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.sirvi.unirem_v1.models.AcState
import com.sirvi.unirem_v1.models.IrController

class MainScreenViewModel(application: Application): AndroidViewModel(application) {
    val irController = IrController(application)

    var state by  mutableStateOf(AcState())
        private set
    fun onPowerClicked(){
        state = state.copy(isPowerON = !state.isPowerON)
        irController.acStateChanged(currentAcState = state)
    }
    fun onSwingClicked(){
        state = state.copy(isSwingON = !state.isSwingON)
        irController.acStateChanged(currentAcState = state)
    }
    fun onTemperatureIncrease(){
        state = state.copy(temperature = if(state.temperature<30 && state.isPowerON) state.temperature++ else state.temperature)
        irController.acStateChanged(currentAcState = state)
    }
    fun onTemperatureDecrease(){
        state = state.copy(temperature = if(state.temperature>18 && state.isSwingON) state.temperature-- else state.temperature)
        irController.acStateChanged(currentAcState = state)
    }
    fun onModeChangeClicked(){
        state = state.copy(mode = (state.mode+1)%3)
        irController.acStateChanged(currentAcState = state)
    }

}