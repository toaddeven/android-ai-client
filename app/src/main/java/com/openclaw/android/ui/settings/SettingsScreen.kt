package com.openclaw.android.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val gatewayUrl by viewModel.gatewayUrl.collectAsState()
    val authToken by viewModel.authToken.collectAsState()
    val deviceId by viewModel.deviceId.collectAsState()
    
    var showTokenDialog by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Connection Settings
        Text(
            text = "Connection",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = gatewayUrl,
                    onValueChange = viewModel::setGatewayUrl,
                    label = { Text("Gateway URL") },
                    placeholder = { Text("ws://127.0.0.1:18789") },
                    leadingIcon = { Icon(Icons.Default.Link, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                OutlinedTextField(
                    value = deviceId,
                    onValueChange = viewModel::setDeviceId,
                    label = { Text("Device ID") },
                    placeholder = { Text("android-001") },
                    leadingIcon = { Icon(Icons.Default.PhoneAndroid, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                OutlinedTextField(
                    value = authToken.take(8) + "...",
                    onValueChange = { },
                    label = { Text("Auth Token") },
                    leadingIcon = { Icon(Icons.Default.Key, contentDescription = null) },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        IconButton(onClick = { showTokenDialog = true }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit")
                        }
                    }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Permissions
        Text(
            text = "Permissions",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                PermissionItem(icon = Icons.Default.Camera, title = "Camera", description = "Take photos and videos")
                PermissionItem(icon = Icons.Default.Mic, title = "Microphone", description = "Voice mode")
                PermissionItem(icon = Icons.Default.LocationOn, title = "Location", description = "Get device location")
                PermissionItem(icon = Icons.Default.Notifications, title = "Notifications", description = "Push notifications")
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // About
        Text(
            text = "About",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("OpenClaw Android", style = MaterialTheme.typography.titleSmall)
                Text("Version 1.0.0", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Personal AI assistant for Android. Connect to OpenClaw Gateway to start.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
    
    if (showTokenDialog) {
        var tokenInput by remember { mutableStateOf(authToken) }
        AlertDialog(
            onDismissRequest = { showTokenDialog = false },
            title = { Text("Auth Token") },
            text = {
                OutlinedTextField(
                    value = tokenInput,
                    onValueChange = { tokenInput = it },
                    label = { Text("Token") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.setAuthToken(tokenInput)
                    showTokenDialog = false
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showTokenDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun PermissionItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyMedium)
            Text(description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
