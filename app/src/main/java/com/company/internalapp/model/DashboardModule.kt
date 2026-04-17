package com.company.internalapp.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.RealEstateAgent
import androidx.compose.material.icons.filled.Warehouse
import androidx.compose.ui.graphics.vector.ImageVector

data class DashboardModule(
    val id: String,
    val title: String,
    val icon: ImageVector,
    val roles: Set<Role>
)

val dashboardModules = listOf(
    DashboardModule("projects", "Projects", Icons.Default.RealEstateAgent, setOf(Role.ADMIN, Role.MANAGER, Role.ASSOCIATE)),
    DashboardModule("direct_team", "Direct Team", Icons.Default.People, setOf(Role.ADMIN, Role.MANAGER)),
    DashboardModule("total_team", "Total Team", Icons.Default.Groups, setOf(Role.ADMIN, Role.MANAGER)),
    DashboardModule("wallet_withdraw", "Wallet Withdraw Request", Icons.Default.AccountBalanceWallet, setOf(Role.ADMIN, Role.MANAGER, Role.ASSOCIATE)),
    DashboardModule("material_request", "Material Request", Icons.Default.Inventory2, setOf(Role.ADMIN, Role.MANAGER, Role.ASSOCIATE)),
    DashboardModule("bonanza", "Bonanza", Icons.Default.Campaign, setOf(Role.ADMIN, Role.MANAGER, Role.ASSOCIATE)),
    DashboardModule("mela", "Mela Update", Icons.Default.Event, setOf(Role.ADMIN, Role.MANAGER, Role.ASSOCIATE)),
    DashboardModule("availability", "Availability", Icons.Default.Warehouse, setOf(Role.ADMIN, Role.MANAGER, Role.ASSOCIATE)),
    DashboardModule("sales_history", "Sales History", Icons.Default.History, setOf(Role.ADMIN, Role.MANAGER, Role.ASSOCIATE)),
    DashboardModule("add_associate", "Add Associate", Icons.Default.PersonAdd, setOf(Role.ADMIN, Role.MANAGER)),
    DashboardModule("creatives", "Creatives", Icons.Default.Palette, setOf(Role.ADMIN, Role.MANAGER, Role.ASSOCIATE)),
    DashboardModule("site_incharge", "Site Incharge", Icons.Default.LocationOn, setOf(Role.ADMIN, Role.MANAGER, Role.ASSOCIATE)),
    DashboardModule("vehicle_request", "Vehicle Request", Icons.Default.DirectionsCar, setOf(Role.ADMIN, Role.MANAGER, Role.ASSOCIATE))
)
