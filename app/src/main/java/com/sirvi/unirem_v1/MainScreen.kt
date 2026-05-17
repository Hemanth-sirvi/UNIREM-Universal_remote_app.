package com.sirvi.unirem_v1

import android.app.Activity
import android.view.Display
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@Composable
fun MainScreen(text : String = "nope", modifier: Modifier) {
    var temp:Int by remember { mutableIntStateOf(24) }
    var isPowerON: Boolean by remember { mutableStateOf(false) }
    val tempText = if(isPowerON) "$temp 'C" else " --'C"
    var isSwingON: Boolean by remember { mutableStateOf(false)}
    var selectedMode: Int by remember { mutableIntStateOf(0) }
    var timerMode: Int by remember { mutableIntStateOf(0)}
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
            isPowerON = isPowerON, onPowerPressed = {isPowerON = it},
            tempValue = temp, onTemperatureChangePressed = {temp = it},
            isSwingON = isSwingON, onSwingPressed = {isSwingON = it},
            selectedMode = selectedMode, onModeChange = {selectedMode  = it},
            timerMode = timerMode, onTimerModeChange = {timerMode = it}
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
                    "T:",
                    fontSize = 64.sp,
                    fontFamily = FontFamily(Font(R.font.dseg7classic, FontWeight.Normal)),
                    modifier = Modifier.padding(10.dp),
                    textAlign = TextAlign.Center,
                    color = Color.Black,

                    )
                Text(
                    temp,
                    fontSize = 64.sp,
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
                 isPowerON: Boolean, onPowerPressed: (Boolean) -> Unit,
                 tempValue:Int, onTemperatureChangePressed:(Int) -> Unit,
                 isSwingON: Boolean, onSwingPressed:(Boolean) -> Unit,
                 selectedMode: Int, onModeChange: (Int) -> Unit,
                 timerMode: Int, onTimerModeChange: (Int) -> Unit,
    ){
    Box(modifier= Modifier){
        Row() {
            Column(modifier = Modifier.weight(1f).fillMaxSize()) {
                PowerButton(modifier = Modifier.weight(1f).fillMaxSize(),isPowerON = isPowerON,onPowerPressed = onPowerPressed)
                Spacer(modifier = Modifier.height(10.dp))
                ButtonItem(text = "Mascot", modifier = Modifier.weight(2f).fillMaxSize())
            }
            Spacer(modifier= Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f).fillMaxWidth()) {
                TemperatureButton(modifier = Modifier.weight(1f).fillMaxSize(),tempvalue = tempValue, onTemperatureChangePressed = onTemperatureChangePressed,isPowerON =isPowerON)
                Spacer(modifier = Modifier.height(10.dp))
                ModeButton(modifier = Modifier.weight(1f).fillMaxSize(),selectedMode = selectedMode, onModeChange = onModeChange)
            }
            Spacer(modifier= Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f).fillMaxWidth()) {
                SwingButton(modifier = Modifier.weight(1f).fillMaxSize(), isSwingON = isSwingON, onSwingPressed = onSwingPressed)
                Spacer(modifier = Modifier.height(10.dp))
                TimerButton(modifier = Modifier.weight(1f).fillMaxSize(), timerMode = timerMode, onTimerModeChange = onTimerModeChange)
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
fun PowerButton(modifier: Modifier, isPowerON: Boolean = false, onPowerPressed:(Boolean)-> Unit){
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(10.dp),
        onClick = {
            onPowerPressed(!isPowerON)
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
                    tint = if(isPowerON) Color.DarkGray else Color.LightGray,

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
        tempvalue: Int, onTemperatureChangePressed: (Int) -> Unit ,
        isPowerON: Boolean
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
                        val newTemp: Int = if(tempvalue<30 && isPowerON) tempvalue+1 else tempvalue
                        onTemperatureChangePressed(newTemp)
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
                        val newTemp: Int = if(tempvalue>16 && isPowerON) tempvalue-1 else tempvalue
                        onTemperatureChangePressed(newTemp)
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
fun SwingButton(modifier: Modifier, isSwingON: Boolean  = false, onSwingPressed: (Boolean) -> Unit){
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(10.dp),
        onClick = {
            onSwingPressed(!isSwingON)
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
                    tint = if(isSwingON) Color.DarkGray else Color.LightGray,

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
fun ModeButton(modifier:Modifier, selectedMode:Int, onModeChange:(Int) -> Unit){
    val modeIcon = when(selectedMode){
        0 -> R.drawable.mode_ac
        1 -> R.drawable.mode_fan
        2-> R.drawable.mode_dual
        else -> R.drawable.mode_ac
    }
    val modeText = when(selectedMode){
        0-> "Mode: AC"
        1-> "Mode: Fan"
        2-> "Mode: Dual"
        else -> "Mode: AC"
    }
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(10.dp),
        onClick = {
            val mode :Int = (selectedMode+1)%3
            onModeChange(mode)
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
                    contentDescription = "Swing",
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