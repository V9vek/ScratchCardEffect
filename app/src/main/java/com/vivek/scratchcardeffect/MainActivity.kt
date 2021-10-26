package com.vivek.scratchcardeffect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import com.vivek.scratchcardeffect.ui.screens.ScratchCardScreen
import com.vivek.scratchcardeffect.ui.theme.ScratchCardEffectTheme

@ExperimentalComposeUiApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScratchCardEffectTheme {
                Surface(color = MaterialTheme.colors.background) {
                    ScratchCardScreen()
                }
            }
        }
    }
}