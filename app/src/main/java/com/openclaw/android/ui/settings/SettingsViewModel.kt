package com.openclaw.android.ui.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    
    private val gatewayUrlKey = stringPreferencesKey("gateway_url")
    private val authTokenKey = stringPreferencesKey("auth_token")
    private val deviceIdKey = stringPreferencesKey("device_id")
    
    val gatewayUrl = context.dataStore.data.map { it[gatewayUrlKey] ?: "ws://127.0.0.1:18789" }
        .stateIn(viewModelScope, SharingStarted.Eagerly, "ws://127.0.0.1:18789")
    
    val authToken = context.dataStore.data.map { it[authTokenKey] ?: "" }
        .stateIn(viewModelScope, SharingStarted.Eagerly, "")
    
    val deviceId = context.dataStore.data.map { it[deviceIdKey] ?: "android-${System.currentTimeMillis()}" }
        .stateIn(viewModelScope, SharingStarted.Eagerly, "android-${System.currentTimeMillis()}")
    
    fun setGatewayUrl(url: String) {
        viewModelScope.launch {
            context.dataStore.edit { it[gatewayUrlKey] = url }
        }
    }
    
    fun setAuthToken(token: String) {
        viewModelScope.launch {
            context.dataStore.edit { it[authTokenKey] = token }
        }
    }
    
    fun setDeviceId(id: String) {
        viewModelScope.launch {
            context.dataStore.edit { it[deviceIdKey] = id }
        }
    }
}
