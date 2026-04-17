package com.company.internalapp.ui.navigation

import com.company.internalapp.model.Role

data class MenuItem(
    val destination: AppDestination,
    val roles: Set<Role>
)

val sidebarItems = listOf(
    MenuItem(AppDestination.Home, setOf(Role.ADMIN, Role.MANAGER, Role.ASSOCIATE)),
    MenuItem(AppDestination.AssociateView, setOf(Role.ADMIN, Role.MANAGER)),
    MenuItem(AppDestination.AssociateProfile, setOf(Role.ADMIN, Role.MANAGER, Role.ASSOCIATE)),
    MenuItem(AppDestination.CreateLead, setOf(Role.ADMIN, Role.MANAGER, Role.ASSOCIATE)),
    MenuItem(AppDestination.GetAgentLeads, setOf(Role.ADMIN, Role.MANAGER, Role.ASSOCIATE)),
    MenuItem(AppDestination.ChangePassword, setOf(Role.ADMIN, Role.MANAGER, Role.ASSOCIATE)),
    MenuItem(AppDestination.PrivacyPolicy, setOf(Role.ADMIN, Role.MANAGER, Role.ASSOCIATE))
)
