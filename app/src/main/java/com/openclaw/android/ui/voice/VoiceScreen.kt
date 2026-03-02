package com.openclaw.android.ui.voice

import android.Manifest
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun VoiceScreen(
    viewModel: VoiceViewModel = hiltViewModel()
) {
    val isListening by viewModel.isListening.collectAsState()
    val isSpeaking by viewModel.isSpeaking.collectAsState()
    val transcript by viewModel.transcript.collectAsState()
    val audioLevel by viewModel.audioLevel.collectAsState()
    
    val micPermission = rememberPermissionState(Manifest.permission.RECORD_AUDIO)
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Voice Mode",
            style = MaterialTheme.typography.headlineMedium
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Status
        Text(
            text = when {
                isListening -> "🎙️ Listening..."
                isSpeaking -> "🔊 Speaking..."
                else -> "Tap to talk"
            },
            style = MaterialTheme.typography.titleMedium,
            color = if (isListening) MaterialTheme.colorScheme.primary 
                    else if (isSpeaking) MaterialTheme.colorScheme.secondary
                    else MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Audio level indicator
        if (isListening) {
            LinearProgressIndicator(
                progress = { audioLevel },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        
        // Mic button
        val infiniteTransition = rememberInfiniteTransition(label = "pulse")
        val scale by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = if (isListening) 1.2f else 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(600),
                repeatMode = RepeatMode.Reverse
            ),
            label = "scale"
        )
        
        FloatingActionButton(
            onClick = {
                if (micPermission.status.isGranted) {
                    viewModel.toggleListening()
                } else {
                    micPermission.launchPermissionRequest()
                }
            },
            modifier = Modifier
                .size(80.dp)
                .scale(if (isListening) scale else 1f),
            containerColor = if (isListening) MaterialTheme.colorScheme.primary 
                            else MaterialTheme.colorScheme.secondaryContainer
        ) {
            Icon(
                imageVector = if (isListening) Icons.Default.Mic else Icons.Default.MicOff,
                contentDescription = "Toggle microphone",
                modifier = Modifier.size(36.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Transcript
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Transcript",
                    style = MaterialTheme.typography.labelMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = transcript.ifBlank { "No speech detected yet" },
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Speak button
        if (transcript.isNotBlank()) {
            OutlinedButton(
                onClick = { viewModel.speak(transcript) }
            ) {
                Icon(Icons.Default.VolumeUp, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Speak response")
            }
        }
    }
}
