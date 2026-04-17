package com.company.internalapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.company.internalapp.model.LeadStatus
import com.company.internalapp.ui.navigation.AppDestination
import com.company.internalapp.ui.navigation.sidebarItems
import com.company.internalapp.viewmodel.AppUiState
import com.company.internalapp.viewmodel.AppViewModel
import kotlinx.coroutines.launch

@Composable
fun AppRoot(uiState: AppUiState, viewModel: AppViewModel) {
    if (!uiState.isLoggedIn) {
        LoginScreen(
            error = uiState.loginError,
            onLogin = viewModel::login
        )
        return
    }
    HomeContainer(uiState = uiState, viewModel = viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeContainer(uiState: AppUiState, viewModel: AppViewModel) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var destination by rememberSaveable { mutableStateOf<AppDestination>(AppDestination.Home) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "Internal Sales & Ops",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )
                val role = uiState.user?.role
                sidebarItems.filter { role in it.roles }.forEach { item ->
                    Text(
                        text = item.destination.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                destination = item.destination
                                coroutineScope.launch { drawerState.close() }
                            }
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    )
                }
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                Text(
                    text = "Logout",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.logout()
                            coroutineScope.launch { drawerState.close() }
                        }
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(destination.title) },
                    navigationIcon = {
                        IconButton(onClick = { coroutineScope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "menu")
                        }
                    }
                )
            },
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { padding ->
            when (destination) {
                AppDestination.Home -> DashboardScreen(
                    uiState = uiState,
                    viewModel = viewModel,
                    onModuleClick = {
                        destination = when (it.id) {
                            "projects" -> AppDestination.Projects
                            "material_request", "wallet_withdraw", "vehicle_request" -> AppDestination.Requests
                            else -> AppDestination.Notifications
                        }
                    },
                    modifier = Modifier.padding(padding)
                )

                AppDestination.GetAgentLeads -> LeadListScreen(
                    uiState = uiState,
                    onSearchChanged = viewModel::updateLeadSearch,
                    onUpdateStatus = viewModel::updateLeadStatus,
                    filteredLeads = viewModel.filteredLeads(),
                    modifier = Modifier.padding(padding)
                )

                AppDestination.CreateLead -> CreateLeadScreen(
                    onCreate = viewModel::createLead,
                    modifier = Modifier.padding(padding)
                )

                else -> PlaceholderScreen(
                    title = destination.title,
                    description = "This module scaffold is available and ready for backend integration.",
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }

    LaunchedEffect(destination) {
        if (destination != AppDestination.Home) {
            snackbarHostState.showSnackbar("Loaded ${destination.title}")
        }
    }
}

@Composable
private fun DashboardScreen(
    uiState: AppUiState,
    viewModel: AppViewModel,
    onModuleClick: (com.company.internalapp.model.DashboardModule) -> Unit,
    modifier: Modifier = Modifier
) {
    val modules = viewModel.visibleDashboardModules()

    Column(modifier = modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Text(
                text = "Welcome, ${uiState.user?.name}",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "${uiState.user?.role} • ${uiState.user?.employeeId}",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
            )
            Text(
                text = uiState.user?.mobile.orEmpty(),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
            )
            Text(
                text = "Dashboard target load: 2–3s | API target <1s",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(16.dp)
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(modules) { module ->
                Card(modifier = Modifier.clickable { onModuleClick(module) }) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Icon(module.icon, contentDescription = module.title)
                        Text(
                            text = module.title,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(top = 10.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CreateLeadScreen(onCreate: (String, String, String, String) -> Unit, modifier: Modifier = Modifier) {
    var customer by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var project by rememberSaveable { mutableStateOf("") }
    var assignee by rememberSaveable { mutableStateOf("") }
    var showConfirm by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("Create Lead", style = MaterialTheme.typography.titleLarge)
        OutlinedTextField(value = customer, onValueChange = { customer = it }, label = { Text("Customer Name") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Phone") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = project, onValueChange = { project = it }, label = { Text("Project") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = assignee, onValueChange = { assignee = it }, label = { Text("Assign To") }, modifier = Modifier.fillMaxWidth())
        TextButton(
            onClick = { if (customer.isNotBlank() && phone.length >= 10) showConfirm = true },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Submit")
        }
    }

    if (showConfirm) {
        AlertDialog(
            onDismissRequest = { showConfirm = false },
            title = { Text("Lead submitted") },
            text = { Text("Lead will be assigned and notification will be triggered.") },
            confirmButton = {
                TextButton(onClick = {
                    onCreate(customer, phone, project.ifBlank { "General" }, assignee.ifBlank { "Unassigned" })
                    customer = ""
                    phone = ""
                    project = ""
                    assignee = ""
                    showConfirm = false
                }) {
                    Text("OK")
                }
            }
        )
    }
}

@Composable
private fun LeadListScreen(
    uiState: AppUiState,
    onSearchChanged: (String) -> Unit,
    onUpdateStatus: (String, LeadStatus) -> Unit,
    filteredLeads: List<com.company.internalapp.model.Lead>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = uiState.leadSearch,
            onValueChange = onSearchChanged,
            label = { Text("Search leads") },
            modifier = Modifier.fillMaxWidth()
        )

        LazyColumn(
            modifier = Modifier.padding(top = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredLeads) { lead ->
                Card {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("${lead.customerName} (${lead.id})", fontWeight = FontWeight.Bold)
                        Text("${lead.project} • ${lead.phone}")
                        Text("Assigned: ${lead.assignedTo}")
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            LeadStatus.entries.forEach { status ->
                                AssistChip(
                                    onClick = { onUpdateStatus(lead.id, status) },
                                    label = { Text(status.name.replace('_', ' ')) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PlaceholderScreen(title: String, description: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(title, style = MaterialTheme.typography.headlineSmall)
        Text(description)
        Text("Includes role-based access, workflow hooks, and API-ready architecture.")
    }
}
