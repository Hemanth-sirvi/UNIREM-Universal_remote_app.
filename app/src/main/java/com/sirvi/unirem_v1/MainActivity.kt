package com.sirvi.unirem_v1

import android.content.Context
import android.hardware.ConsumerIrManager
import android.os.Bundle
import android.webkit.ConsoleMessage
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.sirvi.unirem_v1.ui.theme.UNIREM_V1Theme
import android.util.Log
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val irManager =
            getSystemService(Context.CONSUMER_IR_SERVICE)
                    as ConsumerIrManager

        //val irManager =
        //    getSystemService(Context.CONSUMER_IR_SERVICE) as ConsumerIrManager

        //val hasemiter = irManager.hasIrEmitter()

        //val ranges = irManager.carrierFrequencies

        //ranges?.forEach {
        //    Log.d("IR", "${it.minFrequency} - ${it.maxFrequency}")
        //}

        //Log.i("Emitter check","$hasemiter")

        enableEdgeToEdge()
        setContent {
            UNIREM_V1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   MainScreen(text = "24'c",
                       Modifier.statusBarsPadding()
                           .navigationBarsPadding(),
                       irManager
                   )
                }
            }
        }
    }

}

