package com.sirvi.unirem_v1


import android.graphics.Rect
import android.health.connect.datatypes.units.Power
import android.widget.GridLayout
import android.widget.Space
import android.widget.ToggleButton
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
    var tempText = if(isPowerON) "$temp 'C" else " --'C"
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
            tempValue = temp, onTemperatureChangePressed = {temp = it}
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
                ButtonItem(text = "Mode", modifier = Modifier.weight(1f).fillMaxSize())
            }
            Spacer(modifier= Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f).fillMaxWidth()) {
                ButtonItem(text = "Swing", modifier = Modifier.weight(1f).fillMaxSize())
                Spacer(modifier = Modifier.height(10.dp))
                ButtonItem(text = "Timer", modifier = Modifier.weight(1f).fillMaxSize())
                Spacer(modifier = Modifier.height(10.dp))
                ButtonItem(text = "Exit", modifier = Modifier.weight(1f).fillMaxSize())

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
