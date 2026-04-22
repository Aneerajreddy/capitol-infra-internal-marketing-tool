package com.company.internalapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.company.internalapp.theme.InternalSalesOpsTheme
import com.company.internalapp.ui.screens.AppRoot
import com.company.internalapp.viewmodel.AppViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InternalSalesOpsTheme {
                val viewModel: AppViewModel = viewModel()
                val uiState by viewModel.uiState.collectAsState()
                AppRoot(uiState = uiState, viewModel = viewModel)
            }
        }
    }
}
