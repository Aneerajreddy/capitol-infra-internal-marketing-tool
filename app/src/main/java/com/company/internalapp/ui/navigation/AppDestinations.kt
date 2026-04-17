package com.company.internalapp.ui.navigation

sealed class AppDestination(val route: String, val title: String) {
    data object Login : AppDestination("login", "Login")
    data object Home : AppDestination("home", "Home")
    data object AssociateView : AppDestination("associate_view", "Associate View")
    data object AssociateProfile : AppDestination("associate_profile", "Associate Profile")
    data object CreateLead : AppDestination("create_lead", "Create Lead")
    data object GetAgentLeads : AppDestination("get_agent_leads", "Get Agent Leads")
    data object ChangePassword : AppDestination("change_password", "Change Password")
    data object PrivacyPolicy : AppDestination("privacy_policy", "Privacy Policy")
    data object Projects : AppDestination("projects", "Projects")
    data object Requests : AppDestination("requests", "Requests")
    data object Notifications : AppDestination("notifications", "Notifications")
}
