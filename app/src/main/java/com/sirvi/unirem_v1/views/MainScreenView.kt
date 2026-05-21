package com.sirvi.unirem_v1.views

import android.app.Activity
import android.app.Application
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sirvi.unirem_v1.R
import com.sirvi.unirem_v1.models.AcState
import com.sirvi.unirem_v1.viewmodels.MainScreenViewModel

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = viewModel(factory = ViewModelProvider.AndroidViewModelFactory.getInstance(
        LocalContext.current.applicationContext as Application
    ))

) {

    val state = viewModel.state
    Column(modifier = Modifier.padding(10.dp).fillMaxSize()
        //.background(Color.Blue)
        .padding(10.dp),
        Arrangement.Top,
        Alignment.CenterHorizontally
    ) {
        Spacer((Modifier.fillMaxSize(0.1f)))
        TemperatureScreen(state,modifier= Modifier.weight(1f).background(MaterialTheme.colorScheme.primary))
        Spacer(Modifier.fillMaxSize(0.2f))
        ButtonLayout(modifier = Modifier.weight(1f).fillMaxSize(),
            currentAcState = state,
            onPowerClicked = {viewModel.onPowerClicked()},
            onTempIncClicked = {viewModel.onTemperatureIncrease()},
            onTempDecClicked = {viewModel.onTemperatureDecrease()},
            onSwingClicked = {viewModel.onSwingClicked()},
            onTimerModeChange = {state.timerMode},
            onModeChange = {viewModel.onModeChangeClicked()}
        )
    }

}

@Composable
fun TemperatureScreen(state: AcState , modifier: Modifier){
    val temp = if(state.isPowerON) "${state.temperature} 'C " else "-- 'C"
    Card(
        modifier = modifier,
        //elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)

    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Row(

            ) {

                Text(
                    temp,
                    fontSize = 68.sp,
                    fontFamily = FontFamily(Font(R.font.dseg7classic, FontWeight.Normal)),
                    modifier = Modifier.padding(10.dp),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface

                )

            }
        }

    }

}

@Composable
fun ButtonLayout(modifier: Modifier,
                 currentAcState: AcState,
                 onPowerClicked:()-> Unit,
                 onTempIncClicked:() -> Unit,
                 onTempDecClicked:() -> Unit,
                 onModeChange:() -> Unit,
                 onSwingClicked:() -> Unit,
                 onTimerModeChange: (Int) -> Unit,

                 ){
    Box(modifier= Modifier){
        Row() {
            Column(modifier = Modifier.weight(1f).fillMaxSize()) {
                PowerButton(modifier = Modifier.weight(1f).fillMaxSize(), currentAcState = currentAcState,onPowerClicked = onPowerClicked)
                Spacer(modifier = Modifier.height(10.dp))
                ButtonItem(text = "Mascot", modifier = Modifier.weight(2f).fillMaxSize())
            }
            Spacer(modifier= Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f).fillMaxWidth()) {
                TemperatureButton(modifier = Modifier.weight(1f).fillMaxSize(),currentAcState = currentAcState, onTempIncClicked = onTempIncClicked, onTempDecClicked = onTempDecClicked)
                Spacer(modifier = Modifier.height(10.dp))
                ModeButton(modifier = Modifier.weight(1f).fillMaxSize(),currentAcState = currentAcState, onModeChange = onModeChange)
            }
            Spacer(modifier= Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f).fillMaxWidth()) {
                SwingButton(modifier = Modifier.weight(1f).fillMaxSize(), currentAcState = currentAcState,onSwingClicked = onSwingClicked)
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
            modifier = Modifier.background(MaterialTheme.colorScheme.surface).fillMaxSize(),
            contentAlignment = Alignment.Center,

            ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(10.dp).fillMaxSize()
            ) {



                Text(text = text,
                    modifier = Modifier,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

        }


    }
}

@Composable
fun PowerButton(
    modifier: Modifier,
    currentAcState: AcState,
    onPowerClicked: () -> Unit,
    ){
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(10.dp),
        onClick = {
           onPowerClicked()
        }
    ) {

        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.surface).fillMaxSize(),
            contentAlignment = Alignment.Center,

            ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(10.dp).fillMaxSize()
            ) {


                Icon(
                    painter = painterResource(if(currentAcState.isPowerON) R.drawable.ac_power_button else R.drawable.ac_power_button_off),
                    contentDescription = "Power",
                    modifier = Modifier.size(48.dp),
                    tint = Color.Unspecified
                )
                Text(text = "Power",
                    modifier = Modifier,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

        }


    }
}

@Composable
fun TemperatureButton(
    modifier: Modifier,
    currentAcState: AcState,
    onTempIncClicked: () -> Unit,
    onTempDecClicked: () -> Unit,
){
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(10.dp),

        ) {
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.surface).fillMaxSize()
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                IconButton (
                    onClick = {
                       onTempIncClicked()
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
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )
                IconButton (
                    onClick = {
                       onTempDecClicked()
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
    currentAcState: AcState,
    onSwingClicked: () -> Unit,
){
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(10.dp),
        onClick = {
            onSwingClicked()
        }
    ) {

        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.surface).fillMaxSize(),
            contentAlignment = Alignment.Center,

            ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(10.dp).fillMaxSize()
            ) {


                Icon(
                    painter = painterResource(if(currentAcState.isSwingON) R.drawable.swing_icon else R.drawable.swing_icon_off),
                    contentDescription = "Swing",
                    modifier = Modifier.size(48.dp),
                    tint = Color.Unspecified,

                    )
                Text(text = "Swing",
                    modifier = Modifier,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

        }


    }
}

@Composable
fun ModeButton(
    modifier:Modifier,
    currentAcState: AcState,
    onModeChange: () -> Unit,
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
            onModeChange()
        }
    ) {

        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.surface).fillMaxSize(),
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
                    tint = Color.Unspecified
                )
                Text(text = modeText,
                    modifier = Modifier,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
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
            modifier = Modifier.background(MaterialTheme.colorScheme.surface).fillMaxSize(),
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
                    color = MaterialTheme.colorScheme.onSurface
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
            modifier = Modifier.background(MaterialTheme.colorScheme.surface).fillMaxSize(),
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
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

        }


    }
}


