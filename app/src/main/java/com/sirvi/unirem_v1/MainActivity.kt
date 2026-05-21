package com.sirvi.unirem_v1

import android.content.Context
import android.hardware.ConsumerIrManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.sirvi.unirem_v1.ui.theme.UNIREM_V1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val irManager =
            getSystemService(Context.CONSUMER_IR_SERVICE)
                    as? ConsumerIrManager

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

