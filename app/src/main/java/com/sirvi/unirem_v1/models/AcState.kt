package com.sirvi.unirem_v1.models

data class AcState (
    var isPowerON: Boolean = false,
    var temperature: Int = 24,
    var mode: Int = 0,
    var isSwingON: Boolean = false,
    var timerMode: Int = 0,
    var speed: Int = 0,
)

