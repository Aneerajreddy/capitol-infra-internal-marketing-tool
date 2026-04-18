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
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
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
import com.company.internalapp.model.DashboardModule
import com.company.internalapp.model.Lead
import com.company.internalapp.model.LeadStatus
import com.company.internalapp.ui.navigation.AppDestination
import com.company.internalapp.ui.navigation.sidebarItems
import com.company.internalapp.viewmodel.AppUiState
import com.company.internalapp.viewmodel.AppViewModel
import kotlinx.coroutines.launch

@Composable
fun AppRoot(uiState: AppUiState, viewModel: AppViewModel) {
    if (!uiState.isLoggedIn) {
        LoginScreen(error = uiState.loginError, onLogin = viewModel::login)
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
                    text = "Capitol Infra",
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
            val modifier = Modifier.padding(padding)
            when (destination) {
                AppDestination.Home -> DashboardScreen(
                    uiState = uiState,
                    viewModel = viewModel,
                    onModuleClick = {
                        destination = when (it.id) {
                            "projects" -> AppDestination.Projects
                            "direct_team" -> AppDestination.DirectTeam
                            "total_team" -> AppDestination.TotalTeam
                            "wallet_withdraw" -> AppDestination.Wallet
                            "material_request" -> AppDestination.MaterialRequest
                            "bonanza" -> AppDestination.Bonanza
                            "mela" -> AppDestination.Mela
                            "sales_history" -> AppDestination.SalesHistory
                            "add_associate" -> AppDestination.AddAssociate
                            "creatives" -> AppDestination.Creatives
                            "site_incharge" -> AppDestination.SiteIncharge
                            "vehicle_request" -> AppDestination.VehicleRequest
                            else -> AppDestination.Notifications
                        }
                    },
                    modifier = modifier
                )

                AppDestination.GetAgentLeads -> LeadListScreen(
                    uiState = uiState,
                    onSearchChanged = viewModel::updateLeadSearch,
                    onUpdateStatus = viewModel::updateLeadStatus,
                    filteredLeads = viewModel.filteredLeads(),
                    modifier = modifier
                )

                AppDestination.CreateLead -> CreateLeadScreen(
                    onCreate = viewModel::createLead,
                    modifier = modifier
                )

                AppDestination.AssociateProfile -> AssociateProfileScreen(uiState = uiState, modifier = modifier)
                AppDestination.AssociateView -> TeamScreen("Associates", uiState.directTeam.map { "${it.name} • ${it.role}" }, modifier)
                AppDestination.DirectTeam -> TeamScreen("Direct Team", uiState.directTeam.map { "${it.name} • ${it.role}" }, modifier)
                AppDestination.TotalTeam -> TeamScreen("Total Team", uiState.totalTeam.map { "${it.name} • ${it.role}" }, modifier)
                AppDestination.AddAssociate -> AddAssociateScreen(onAdd = viewModel::addAssociate, modifier = modifier)
                AppDestination.Projects -> ProjectsScreen(uiState = uiState, modifier = modifier)
                AppDestination.MaterialRequest -> MaterialRequestScreen(uiState = uiState, onSubmit = viewModel::submitMaterialRequest, modifier = modifier)
                AppDestination.VehicleRequest -> VehicleRequestScreen(uiState = uiState, onSubmit = viewModel::submitVehicleRequest, modifier = modifier)
                AppDestination.Wallet -> WalletScreen(uiState = uiState, onWithdraw = viewModel::submitWalletWithdrawal, modifier = modifier)
                AppDestination.Bonanza -> BonanzaScreen(uiState = uiState, modifier = modifier)
                AppDestination.Mela -> MelaScreen(uiState = uiState, modifier = modifier)
                AppDestination.SalesHistory -> SalesHistoryScreen(uiState = uiState, modifier = modifier)
                AppDestination.SiteIncharge -> SiteInchargeScreen(uiState = uiState, modifier = modifier)
                AppDestination.Creatives -> CreativesScreen(uiState = uiState, modifier = modifier)
                AppDestination.Notifications -> NotificationsScreen(uiState = uiState, modifier = modifier)
                AppDestination.ChangePassword -> ChangePasswordScreen(onChangePassword = viewModel::changePassword, modifier = modifier)
                AppDestination.PrivacyPolicy -> PrivacyPolicyScreen(modifier = modifier)
                AppDestination.Login -> Unit
            }
        }
    }

    LaunchedEffect(uiState.statusMessage) {
        uiState.statusMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearStatusMessage()
        }
    }
}

@Composable
private fun DashboardScreen(
    uiState: AppUiState,
    viewModel: AppViewModel,
    onModuleClick: (DashboardModule) -> Unit,
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
            Text("Welcome, ${uiState.user?.name}", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(16.dp))
            Text("${uiState.user?.role} • ${uiState.user?.employeeId}", modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp))
            Text(uiState.user?.mobile.orEmpty(), modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp))
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
                        Text(module.title, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 10.dp))
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

    Column(modifier = modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text("Create Lead", style = MaterialTheme.typography.titleLarge)
        OutlinedTextField(customer, { customer = it }, label = { Text("Customer Name") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(phone, { phone = it }, label = { Text("Phone") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(project, { project = it }, label = { Text("Project") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(assignee, { assignee = it }, label = { Text("Assign To") }, modifier = Modifier.fillMaxWidth())
        TextButton(onClick = { if (customer.isNotBlank() && phone.length >= 10) showConfirm = true }, modifier = Modifier.align(Alignment.End)) { Text("Submit") }
    }

    if (showConfirm) {
        AlertDialog(
            onDismissRequest = { showConfirm = false },
            title = { Text("Lead submitted") },
            text = { Text("Lead created and assigned.") },
            confirmButton = {
                TextButton(onClick = {
                    onCreate(customer, phone, project.ifBlank { "General" }, assignee.ifBlank { "Unassigned" })
                    customer = ""
                    phone = ""
                    project = ""
                    assignee = ""
                    showConfirm = false
                }) { Text("OK") }
            }
        )
    }
}

@Composable
private fun LeadListScreen(
    uiState: AppUiState,
    onSearchChanged: (String) -> Unit,
    onUpdateStatus: (String, LeadStatus) -> Unit,
    filteredLeads: List<Lead>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = uiState.leadSearch,
            onValueChange = onSearchChanged,
            label = { Text("Search leads") },
            modifier = Modifier.fillMaxWidth()
        )

        LazyColumn(modifier = Modifier.padding(top = 12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(filteredLeads) { lead ->
                Card {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("${lead.customerName} (${lead.id})", fontWeight = FontWeight.Bold)
                        Text("${lead.project} • ${lead.phone}")
                        Text("Assigned: ${lead.assignedTo}")
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            LeadStatus.entries.forEach { status ->
                                AssistChip(onClick = { onUpdateStatus(lead.id, status) }, label = { Text(status.name.replace('_', ' ')) })
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TeamScreen(title: String, members: List<String>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        item { Text(title, style = MaterialTheme.typography.titleLarge) }
        items(members) { member -> Card(modifier = Modifier.fillMaxWidth()) { Text(member, modifier = Modifier.padding(12.dp)) } }
    }
}

@Composable
private fun AddAssociateScreen(onAdd: (String, String) -> Unit, modifier: Modifier = Modifier) {
    var name by rememberSaveable { mutableStateOf("") }
    var role by rememberSaveable { mutableStateOf("Associate") }
    Column(modifier = modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Add Associate", style = MaterialTheme.typography.titleLarge)
        OutlinedTextField(name, { name = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(role, { role = it }, label = { Text("Role") }, modifier = Modifier.fillMaxWidth())
        TextButton(onClick = { if (name.isNotBlank()) onAdd(name, role) }, modifier = Modifier.align(Alignment.End)) { Text("Add") }
    }
}

@Composable
private fun AssociateProfileScreen(uiState: AppUiState, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text("Associate Profile", style = MaterialTheme.typography.titleLarge)
        Text("Name: ${uiState.user?.name.orEmpty()}")
        Text("Role: ${uiState.user?.role}")
        Text("Employee ID: ${uiState.user?.employeeId}")
        Text("Contact: ${uiState.user?.mobile}")
    }
}

@Composable
private fun ProjectsScreen(uiState: AppUiState, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(uiState.projects) { p ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(p.name, fontWeight = FontWeight.Bold)
                    Text("Location: ${p.location}")
                    Text("Price from: ${p.priceFrom}")
                    Text("Availability: ${p.availability}")
                }
            }
        }
    }
}

@Composable
private fun MaterialRequestScreen(uiState: AppUiState, onSubmit: (String, Int) -> Unit, modifier: Modifier = Modifier) {
    var item by rememberSaveable { mutableStateOf("") }
    var qty by rememberSaveable { mutableStateOf("1") }
    Column(modifier = modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Material Request", style = MaterialTheme.typography.titleLarge)
        OutlinedTextField(item, { item = it }, label = { Text("Item") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(qty, { qty = it }, label = { Text("Quantity") }, modifier = Modifier.fillMaxWidth())
        TextButton(onClick = { onSubmit(item, qty.toIntOrNull() ?: 1) }, modifier = Modifier.align(Alignment.End)) { Text("Submit Request") }
        Divider()
        uiState.materialRequests.forEach { r -> Text("${r.id}: ${r.item} x${r.quantity} - ${r.status}") }
    }
}

@Composable
private fun VehicleRequestScreen(uiState: AppUiState, onSubmit: (String, String) -> Unit, modifier: Modifier = Modifier) {
    var purpose by rememberSaveable { mutableStateOf("") }
    var date by rememberSaveable { mutableStateOf("2026-04-18") }
    Column(modifier = modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Vehicle Request", style = MaterialTheme.typography.titleLarge)
        OutlinedTextField(purpose, { purpose = it }, label = { Text("Purpose") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(date, { date = it }, label = { Text("Date (YYYY-MM-DD)") }, modifier = Modifier.fillMaxWidth())
        TextButton(onClick = { onSubmit(purpose, date) }, modifier = Modifier.align(Alignment.End)) { Text("Submit Request") }
        Divider()
        uiState.vehicleRequests.forEach { r -> Text("${r.id}: ${r.purpose} (${r.date}) - ${r.status}") }
    }
}

@Composable
private fun WalletScreen(uiState: AppUiState, onWithdraw: (Double) -> Unit, modifier: Modifier = Modifier) {
    var amount by rememberSaveable { mutableStateOf("1000") }
    val balance = uiState.walletTransactions.sumOf { if (it.type == "Credit") it.amount else -it.amount }
    Column(modifier = modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Wallet", style = MaterialTheme.typography.titleLarge)
        Text("Balance: ₹${"%.2f".format(balance)}")
        OutlinedTextField(amount, { amount = it }, label = { Text("Withdraw Amount") }, modifier = Modifier.fillMaxWidth())
        TextButton(onClick = { onWithdraw(amount.toDoubleOrNull() ?: 0.0) }, modifier = Modifier.align(Alignment.End)) { Text("Request Withdrawal") }
        Divider()
        uiState.walletTransactions.forEach { t -> Text("${t.date} ${t.type} ₹${t.amount}") }
    }
}

@Composable
private fun BonanzaScreen(uiState: AppUiState, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(uiState.bonanzaPrograms) { b ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(b.name, fontWeight = FontWeight.Bold)
                    Text("Eligibility: ${b.criteria}")
                }
            }
        }
    }
}

@Composable
private fun MelaScreen(uiState: AppUiState, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(uiState.melaUpdates) { e ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(e.title, fontWeight = FontWeight.Bold)
                    Text(e.description)
                    Text("Date: ${e.date}")
                }
            }
        }
    }
}

@Composable
private fun SalesHistoryScreen(uiState: AppUiState, modifier: Modifier = Modifier) {
    val teamTotal = uiState.salesRecords.sumOf { it.amount }
    Column(modifier = modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Team Sales Total: ₹${"%.2f".format(teamTotal)}", style = MaterialTheme.typography.titleMedium)
        uiState.salesRecords.forEach { s ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("${s.owner} • ${s.project}")
                    Text("Amount: ₹${s.amount}")
                    Text("Date: ${s.date}")
                }
            }
        }
    }
}

@Composable
private fun SiteInchargeScreen(uiState: AppUiState, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        uiState.siteIncharges.forEach { s ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(s.name, fontWeight = FontWeight.Bold)
                    Text("Site: ${s.site}")
                    Text("Contact: ${s.phone}")
                }
            }
        }
    }
}

@Composable
private fun CreativesScreen(uiState: AppUiState, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        uiState.creatives.forEach { c ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(c.title, fontWeight = FontWeight.Bold)
                    Text("Download/Share URL: ${c.url}")
                }
            }
        }
    }
}

@Composable
private fun NotificationsScreen(uiState: AppUiState, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(uiState.notifications) { n -> Card(modifier = Modifier.fillMaxWidth()) { Text(n, modifier = Modifier.padding(12.dp)) } }
    }
}

@Composable
private fun ChangePasswordScreen(onChangePassword: (String, String) -> Unit, modifier: Modifier = Modifier) {
    var current by rememberSaveable { mutableStateOf("") }
    var new by rememberSaveable { mutableStateOf("") }
    Column(modifier = modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Change Password", style = MaterialTheme.typography.titleLarge)
        OutlinedTextField(current, { current = it }, label = { Text("Current Password") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(new, { new = it }, label = { Text("New Password") }, modifier = Modifier.fillMaxWidth())
        TextButton(onClick = { onChangePassword(current, new) }, modifier = Modifier.align(Alignment.End)) { Text("Update") }
    }
}

@Composable
private fun PrivacyPolicyScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Privacy Policy", style = MaterialTheme.typography.titleLarge)
        Text("This internal app stores operational data securely and shares information only with authorized company systems.")
        Text("All API calls should be protected by HTTPS and token-based authentication in production.")
    }
}
