package com.openclaw.android.ui

import android.os.Bundle
import
import androidx.activity.compose.setContent
 androidx.activity.ComponentActivityimport androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.openclaw.android.ui.navigation.OpenClawNavHost
import com.openclaw.android.ui.theme.OpenClawTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OpenClawTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    OpenClawNavHost()
                }
            }
        }
    }
}
