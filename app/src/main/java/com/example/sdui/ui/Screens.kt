package com.example.sdui.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sdui.sdui.SduiPage

@Composable
fun HomeScreen(
    onNavigate: (route: String, params: Map<String, Any?>?) -> Unit,
    vm: SduiViewModel = viewModel()
) {
    val state by vm.state.collectAsState()

    LaunchedEffect(Unit) { vm.loadHome("default") }

    PageScaffold(state = state, onNavigate = onNavigate)
}

@Composable
fun DetailScreen(
    id: String,
    onNavigate: (route: String, params: Map<String, Any?>?) -> Unit,
    vm: SduiViewModel = viewModel()
) {
    val state by vm.state.collectAsState()

    LaunchedEffect(id) { vm.loadDetail(id) }

    PageScaffold(state = state, onNavigate = onNavigate)
}

@Composable
private fun PageScaffold(
    state: UiState,
    onNavigate: (route: String, params: Map<String, Any?>?) -> Unit
) {
    val snackbar = remember { SnackbarHostState() }
    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbar) }) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (state) {
                is UiState.Loading -> {
                    Box(
                        Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is UiState.Error -> {
                    Box(
                        Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = state.message)
                    }
                }
                is UiState.Success -> {
                    RenderPage(page = state.page, onNavigate = onNavigate)
                }
            }
        }
    }
}

@Composable
private fun RenderPage(
    page: SduiPage,
    onNavigate: (route: String, params: Map<String, Any?>?) -> Unit
) {
    Renderer(nodes = page.components, onNavigate = onNavigate)
}
