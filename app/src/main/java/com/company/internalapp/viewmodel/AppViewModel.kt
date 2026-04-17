package com.company.internalapp.viewmodel

import androidx.lifecycle.ViewModel
import com.company.internalapp.data.FakeBackendRepository
import com.company.internalapp.model.DashboardModule
import com.company.internalapp.model.Lead
import com.company.internalapp.model.LeadStatus
import com.company.internalapp.model.User
import com.company.internalapp.model.dashboardModules
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class AppUiState(
    val user: User? = null,
    val isLoggedIn: Boolean = false,
    val loginError: String? = null,
    val leads: List<Lead> = emptyList(),
    val leadSearch: String = "",
    val notifications: List<String> = listOf(
        "Lead L-102 moved to Interested",
        "Vehicle request VR-92 approved",
        "Mela update: Weekend campaign starts Saturday"
    )
)

class AppViewModel(
    private val repository: FakeBackendRepository = FakeBackendRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppUiState(leads = repository.listLeads()))
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    fun login(mobile: String, credential: String) {
        val result = repository.login(mobile, credential)
        result.fold(
            onSuccess = { user ->
                _uiState.update {
                    it.copy(
                        user = user,
                        isLoggedIn = true,
                        loginError = null,
                        leads = repository.listLeads()
                    )
                }
            },
            onFailure = {
                _uiState.update { state -> state.copy(loginError = it.message) }
            }
        )
    }

    fun logout() {
        _uiState.value = AppUiState(leads = repository.listLeads())
    }

    fun visibleDashboardModules(): List<DashboardModule> {
        val role = _uiState.value.user?.role ?: return emptyList()
        return dashboardModules.filter { role in it.roles }
    }

    fun createLead(name: String, phone: String, project: String, assignee: String) {
        repository.createLead(name, phone, project, assignee)
        _uiState.update { it.copy(leads = repository.listLeads()) }
    }

    fun updateLeadStatus(leadId: String, status: LeadStatus) {
        repository.updateLeadStatus(leadId, status)
        _uiState.update { it.copy(leads = repository.listLeads()) }
    }

    fun updateLeadSearch(term: String) {
        _uiState.update { it.copy(leadSearch = term) }
    }

    fun filteredLeads(): List<Lead> {
        val term = _uiState.value.leadSearch.trim().lowercase()
        if (term.isBlank()) return _uiState.value.leads
        return _uiState.value.leads.filter {
            it.customerName.lowercase().contains(term) ||
                it.phone.contains(term) ||
                it.project.lowercase().contains(term) ||
                it.status.name.lowercase().contains(term)
        }
    }
}
