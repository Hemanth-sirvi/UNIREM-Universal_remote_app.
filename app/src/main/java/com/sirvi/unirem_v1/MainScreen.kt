package com.sirvi.unirem_v1

import android.app.Activity
import android.hardware.ConsumerIrManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


data class CurrentAcState(
    var isPowerON: Boolean,
    var temperature: Int,
    var mode: Int,
    var isSwingON: Boolean,
    var timerMode: Int,
    var speed: Int,
)

@Composable
fun MainScreen(
    text : String = "nope",
    modifier: Modifier,
    irManager: ConsumerIrManager

) {
    var currentAcState: CurrentAcState by remember { mutableStateOf(CurrentAcState(
        isPowerON = false, temperature = 24, mode = 0, isSwingON = false, timerMode = 0, speed = 0
    )) }
    val temp = currentAcState.temperature
    val tempText = if(currentAcState.isPowerON) " $temp 'c" else " -- 'c"
    Column(modifier = modifier.padding(10.dp).fillMaxSize()
        //.background(Color.Blue)
        .padding(10.dp),
        Arrangement.Top,
        Alignment.CenterHorizontally
    ) {
        Spacer((modifier.fillMaxSize(0.1f)))
        TemperatureScreen(tempText,modifier= Modifier.weight(1f).background(Color.White))
        Spacer(modifier.fillMaxSize(0.2f))
        ButtonLayout(modifier = Modifier.weight(1f).fillMaxSize(),
            currentAcState = currentAcState, onCurrentAcStateChange = {currentAcState = it},
            irManager = irManager
        )
    }

}

@Composable
fun TemperatureScreen(temp : String, modifier: Modifier){

    Card(
        modifier = Modifier,
        //elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(Color.Transparent)

    ) {
        Box(
            modifier = Modifier
                .background(Color.Transparent)
        ) {
            Row(

            ) {

                Text(
                    temp,
                    fontSize = 68.sp,
                    fontFamily = FontFamily(Font(R.font.dseg7classic, FontWeight.Normal)),
                    modifier = Modifier.padding(10.dp),
                    textAlign = TextAlign.Center,
                    color = Color.Black

                )

            }
        }

    }

}

@Composable
fun ButtonLayout(modifier: Modifier,
                 currentAcState: CurrentAcState, onCurrentAcStateChange:(CurrentAcState)-> Unit,
                 irManager: ConsumerIrManager
    ){
    Box(modifier= Modifier){
        Row() {
            Column(modifier = Modifier.weight(1f).fillMaxSize()) {
                PowerButton(modifier = Modifier.weight(1f).fillMaxSize(), currentAcState = currentAcState,onCurrentAcStateChange = onCurrentAcStateChange, irManager = irManager)
                Spacer(modifier = Modifier.height(10.dp))
                ButtonItem(text = "Mascot", modifier = Modifier.weight(2f).fillMaxSize())
            }
            Spacer(modifier= Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f).fillMaxWidth()) {
                TemperatureButton(modifier = Modifier.weight(1f).fillMaxSize(),currentAcState = currentAcState, onCurrentAcStateChange = onCurrentAcStateChange, irManager = irManager)
                Spacer(modifier = Modifier.height(10.dp))
                ModeButton(modifier = Modifier.weight(1f).fillMaxSize(),currentAcState = currentAcState, onCurrentAcStateChange = onCurrentAcStateChange,irManager = irManager)
            }
            Spacer(modifier= Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f).fillMaxWidth()) {
                SwingButton(modifier = Modifier.weight(1f).fillMaxSize(), currentAcState = currentAcState,onCurrentAcStateChange = onCurrentAcStateChange,irManager = irManager)
                Spacer(modifier = Modifier.height(10.dp))
                TimerButton(modifier = Modifier.weight(1f).fillMaxSize(), timerMode = currentAcState.timerMode, onTimerModeChange = {currentAcState.timerMode})
                Spacer(modifier = Modifier.height(10.dp))
                ExitButton(modifier = Modifier.weight(1f).fillMaxSize())

            }
        }

    }
}

@Composable
fun ButtonItem(modifier: Modifier,text:String){
    var isChecked: Boolean by remember { mutableStateOf(false) }
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(10.dp),
        onClick = {
            isChecked = !isChecked
        }
    ) {

        Box(
            modifier = Modifier.background(Color.White).fillMaxSize(),
            contentAlignment = Alignment.Center,

            ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(10.dp).fillMaxSize()
            ) {


                Icon(
                    imageVector = Icons.Filled.PowerSettingsNew,
                    contentDescription = "Power",
                    modifier = Modifier.size(48.dp),
                    tint = if(isChecked) Color.DarkGray else Color.LightGray,

                    )
                    Text(text = text,
                        modifier = Modifier,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
            }

        }


    }
}

@Composable
fun PowerButton(
    modifier: Modifier,
    currentAcState: CurrentAcState,
    onCurrentAcStateChange: (CurrentAcState) -> Unit,
    irManager: ConsumerIrManager){
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(10.dp),
        onClick = {
            val newAcState = CurrentAcState(
                isPowerON = !(currentAcState.isPowerON),
                temperature = currentAcState.temperature,
                mode = currentAcState.mode,
                isSwingON = currentAcState.isSwingON,
                timerMode = currentAcState.timerMode,
                speed = currentAcState.speed
            )
            onCurrentAcStateChange(newAcState)
            acStateChanged(newAcState,irManager)

        }
    ) {

        Box(
            modifier = Modifier.background(Color.White).fillMaxSize(),
            contentAlignment = Alignment.Center,

            ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(10.dp).fillMaxSize()
            ) {


                Icon(
                    imageVector = Icons.Filled.PowerSettingsNew,
                    contentDescription = "Power",
                    modifier = Modifier.size(48.dp),
                    tint = if(currentAcState.isPowerON) Color.DarkGray else Color.LightGray,

                    )
                Text(text = "Power",
                    modifier = Modifier,
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }

        }


    }
}

@Composable
fun TemperatureButton(
        modifier: Modifier,
        currentAcState: CurrentAcState,
        onCurrentAcStateChange: (CurrentAcState) -> Unit,
        irManager: ConsumerIrManager
    ){
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(10.dp),

    ) {
        Box(
            modifier = Modifier.background(Color.White).fillMaxSize()
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                IconButton (
                    onClick = {
                        val newAcState = CurrentAcState(
                            isPowerON = currentAcState.isPowerON,
                            temperature = if(currentAcState.temperature<30 && currentAcState.isPowerON) currentAcState.temperature+1 else currentAcState.temperature,
                            mode = currentAcState.mode,
                            isSwingON = currentAcState.isSwingON,
                            timerMode = currentAcState.timerMode,
                            speed = currentAcState.speed
                        )
                        onCurrentAcStateChange(newAcState)
                        if(currentAcState.isPowerON) acStateChanged(newAcState,irManager)
                    },
                    shape = RectangleShape,
                    modifier = Modifier.weight(3f).fillMaxWidth()
                    ){
                    Icon(painter = painterResource(R.drawable.temperature_increase_black),
                        contentDescription = "Temperature increase"
                        )
                }
                Spacer(modifier.height(1.dp))
                Text(
                    "Temp",
                    modifier.weight(1f).fillMaxWidth(),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
                IconButton (
                    onClick = {
                        val newAcState = CurrentAcState(
                            isPowerON = currentAcState.isPowerON,
                            temperature = if(currentAcState.temperature>18 && currentAcState.isPowerON) currentAcState.temperature-1 else currentAcState.temperature,
                            mode = currentAcState.mode,
                            isSwingON = currentAcState.isSwingON,
                            timerMode = currentAcState.timerMode,
                            speed = currentAcState.speed
                        )
                        onCurrentAcStateChange(newAcState)
                        if(currentAcState.isPowerON) acStateChanged(newAcState,irManager)
                    },
                    shape = RectangleShape,
                    modifier = Modifier.weight(3f).fillMaxWidth()
                ){
                    Icon(painter = painterResource(R.drawable.temperature_decrease_black),
                        contentDescription = "Temperature increase"
                    )
                }
            }


        }
    }

}

@Composable
fun SwingButton(
    modifier: Modifier,
    currentAcState: CurrentAcState,
    onCurrentAcStateChange: (CurrentAcState) -> Unit,
    irManager: ConsumerIrManager
){
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(10.dp),
        onClick = {
            val newAcState = CurrentAcState(
                isPowerON = currentAcState.isPowerON,
                temperature = currentAcState.temperature,
                mode = currentAcState.mode,
                isSwingON = !(currentAcState.isSwingON),
                timerMode = currentAcState.timerMode,
                speed = currentAcState.speed
            )
            onCurrentAcStateChange(newAcState)
            acStateChanged(newAcState, irManager = irManager)
        }
    ) {

        Box(
            modifier = Modifier.background(Color.White).fillMaxSize(),
            contentAlignment = Alignment.Center,

            ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(10.dp).fillMaxSize()
            ) {


                Icon(
                    painter = painterResource(R.drawable.swing_icon),
                    contentDescription = "Swing",
                    modifier = Modifier.size(48.dp),
                    tint = if(currentAcState.isSwingON) Color.DarkGray else Color.LightGray,

                    )
                Text(text = "Swing",
                    modifier = Modifier,
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }

        }


    }
}

@Composable
fun ModeButton(
    modifier:Modifier,
    currentAcState: CurrentAcState,
    onCurrentAcStateChange: (CurrentAcState) -> Unit,
    irManager: ConsumerIrManager
    ){
    val modeIcon = when(currentAcState.mode){
        0 -> R.drawable.mode_cool
        1-> R.drawable.mode_dry
        2-> R.drawable.mode_fan
        else -> R.drawable.mode_cool
    }
    val modeText = when(currentAcState.mode){
        0-> "Mode: Cool"
        1-> "Mode: Dry"
        2-> "Mode: Fan"
        else -> "Mode: Cool"
    }
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(10.dp),
        onClick = {
            val mode :Int = (currentAcState.mode+1)%3
            val newAcState = CurrentAcState(
                isPowerON = currentAcState.isPowerON,
                temperature = currentAcState.temperature,
                mode = mode,
                isSwingON = currentAcState.isSwingON,
                timerMode = currentAcState.timerMode,
                speed = currentAcState.speed
            )
            onCurrentAcStateChange(newAcState)
            acStateChanged(newAcState,irManager)
        }
    ) {

        Box(
            modifier = Modifier.background(Color.White).fillMaxSize(),
            contentAlignment = Alignment.Center,

            ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(10.dp).fillMaxSize()
            ) {


                Icon(
                    painter = painterResource(modeIcon),
                    contentDescription = "Mode",
                    modifier = Modifier.size(48.dp),
                    )
                Text(text = modeText,
                    modifier = Modifier,
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }

        }

    }
}


@Composable
fun TimerButton(modifier: Modifier, timerMode: Int, onTimerModeChange:(Int)-> Unit){
    val timerIcon = when(timerMode){
        0 -> R.drawable.timer_off_icon
        else -> R.drawable.timer_on_icon

    }
    val timerText = when(timerMode){
        0-> "Timer: OFF"
        1-> "Timer: 1h"
        2-> "Timer: 2h"
        3-> "Timer: 3h"
        else -> "Timer: OFF"
    }
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(10.dp),
        onClick = {
            val mode :Int = (timerMode+1)%4
            onTimerModeChange(mode)
        }
    ) {

        Box(
            modifier = Modifier.background(Color.White).fillMaxSize(),
            contentAlignment = Alignment.Center,

            ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(10.dp).fillMaxSize()
            ) {


                Icon(
                    painter = painterResource(timerIcon),
                    contentDescription = "Timer button",
                    modifier = Modifier.size(48.dp),
                )
                Text(text = timerText,
                    modifier = Modifier,
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }

        }

    }
}


@Composable
fun ExitButton(modifier:Modifier){
    val activity = (LocalContext.current as? Activity)
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(10.dp),
        onClick = {
            activity?.finish()
        }
    ) {

        Box(
            modifier = Modifier.background(Color.White).fillMaxSize(),
            contentAlignment = Alignment.Center,

            ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(10.dp).fillMaxSize()
            ) {


                Icon(
                    painter = painterResource(R.drawable.exit_icon),
                    contentDescription = "Exit button",
                    modifier = Modifier.size(48.dp),
                    )
                Text(text = "EXIT",
                    modifier = Modifier,
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }

        }


    }
}

fun buildVoltasPacket(
    currentAcState: CurrentAcState
): ByteArray {

    val data = ByteArray(10)

    // Byte 0 --> Horizontal swing, 0x33 for on and 0x32 for off
    var b0 = 0x33
    if(currentAcState.isSwingON) b0 = b0 or 0x32
    data[0]  = b0.toByte()

    // Byte 1 --> mode + fan speed
    // (0)Chill mode + auto = 0xE8 | Cool mode + auto = 0xE2   | (1)Dry + Fixed  = 0x84
    // (0)Chill mode + low = 0x88  | Cool mode + low = 0x82    | (2)Fan + High = 0x21
    // (0)Chill mode + mid = 0x48  | Cool mode + mid = 0x42
    // (0)Chill mode + high = 0x28 | Cool mode + high = 0x22
    var b1 = 0xE8
    b1 = when((currentAcState.mode*10) + (currentAcState.speed)){
        0-> 0xE8
        1-> 0x88
        2-> 0x48
        3-> 0x28
        10,11,12,13-> 0x84
        20,21,22,23 -> 0x21
        else -> 0xE8
    }

    data[1] = b1.toByte()

    // Byte 2 powerON 0x28 powerOff 0x00 / 0x80?
    var b2 = 0x28
    if (currentAcState.isPowerON) b2 = b2 or 0x80

    data[2] = b2.toByte()

    // Byte 3
    // 24C
    var b3 = 0x12
    b3 = when(currentAcState.temperature){
        18-> 0x12
        19 -> 0x13
        20 -> 0x14
        21 -> 0x15
        22 -> 0x16
        23 -> 0x17
        24 -> 0x18
        25 -> 0x19
        26 -> 0x1A
        27 -> 0x1B
        28 -> 0x1C
        29 -> 0x1D
        30 -> 0x1E
        else -> 0x18

    }
    data[3] = b3.toByte()

    // Remaining default bytes
    data[4] = 0x3B
    data[5] = 0x3B
    data[6] = 0x3B
    data[7] = 0x11
    data[8] = 0x00

    // Checksum
    var sum = 0

    for (i in 0 until 9) {
        sum += data[i].toInt() and 0xFF
    }

    data[9] = (sum.inv() and 0xFF).toByte()

    return data
}

fun encodeVoltas(data: ByteArray): IntArray {

    val raw = mutableListOf<Int>()

    for (byte in data) {

        for (i in 7 downTo 0) {

            val bit = (byte.toInt() shr i) and 1

            raw.add(1000)

            if (bit == 1)
                raw.add(2570)
            else
                raw.add(580)
        }
    }

    // Final stop pulse
    raw.add(1000)

    return raw.toIntArray()
}


fun sendPower(currentAcState: CurrentAcState, irManager: ConsumerIrManager) {

    val packet = buildVoltasPacket(currentAcState = currentAcState)

    val pattern = encodeVoltas(packet)

    irManager.transmit(
        38000,
        pattern
    )
}

fun acStateChanged(
    currentAcState: CurrentAcState,
    irManager: ConsumerIrManager){
    val packet = buildVoltasPacket(currentAcState)
    val pattern = encodeVoltas(packet)
    irManager.transmit(
        38000,
        pattern
    )
}