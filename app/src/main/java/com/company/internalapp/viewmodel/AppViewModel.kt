package com.company.internalapp.viewmodel

import androidx.lifecycle.ViewModel
import com.company.internalapp.data.FakeBackendRepository
import com.company.internalapp.model.BonanzaProgram
import com.company.internalapp.model.CreativeAsset
import com.company.internalapp.model.DashboardModule
import com.company.internalapp.model.Lead
import com.company.internalapp.model.LeadStatus
import com.company.internalapp.model.MaterialRequest
import com.company.internalapp.model.MelaUpdate
import com.company.internalapp.model.Project
import com.company.internalapp.model.SalesRecord
import com.company.internalapp.model.SiteIncharge
import com.company.internalapp.model.TeamMember
import com.company.internalapp.model.User
import com.company.internalapp.model.VehicleRequest
import com.company.internalapp.model.WalletTransaction
import com.company.internalapp.model.dashboardModules
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class AppUiState(
    val user: User? = null,
    val isLoggedIn: Boolean = false,
    val loginError: String? = null,
    val statusMessage: String? = null,
    val leads: List<Lead> = emptyList(),
    val leadSearch: String = "",
    val projects: List<Project> = emptyList(),
    val directTeam: List<TeamMember> = emptyList(),
    val totalTeam: List<TeamMember> = emptyList(),
    val materialRequests: List<MaterialRequest> = emptyList(),
    val vehicleRequests: List<VehicleRequest> = emptyList(),
    val walletTransactions: List<WalletTransaction> = emptyList(),
    val bonanzaPrograms: List<BonanzaProgram> = emptyList(),
    val melaUpdates: List<MelaUpdate> = emptyList(),
    val salesRecords: List<SalesRecord> = emptyList(),
    val siteIncharges: List<SiteIncharge> = emptyList(),
    val creatives: List<CreativeAsset> = emptyList(),
    val notifications: List<String> = listOf(
        "Lead L-102 moved to Interested",
        "Vehicle request VR-92 approved",
        "Mela update: Weekend campaign starts Saturday"
    )
)

class AppViewModel(
    private val repository: FakeBackendRepository = FakeBackendRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(initialState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    private fun initialState() = AppUiState(
        leads = repository.listLeads(),
        projects = repository.listProjects(),
        directTeam = repository.listDirectTeam(),
        totalTeam = repository.listTotalTeam(),
        materialRequests = repository.listMaterialRequests(),
        vehicleRequests = repository.listVehicleRequests(),
        walletTransactions = repository.listWalletTransactions(),
        bonanzaPrograms = repository.listBonanza(),
        melaUpdates = repository.listMelaUpdates(),
        salesRecords = repository.listSalesRecords(),
        siteIncharges = repository.listSiteIncharges(),
        creatives = repository.listCreatives()
    )

    fun login(mobile: String, credential: String) {
        repository.login(mobile, credential).fold(
            onSuccess = { user -> _uiState.update { it.copy(user = user, isLoggedIn = true, loginError = null) } },
            onFailure = { _uiState.update { state -> state.copy(loginError = it.message) } }
        )
    }

    fun logout() {
        _uiState.value = initialState()
    }

    fun visibleDashboardModules(): List<DashboardModule> {
        val role = _uiState.value.user?.role ?: return emptyList()
        return dashboardModules.filter { role in it.roles }
    }

    fun createLead(name: String, phone: String, project: String, assignee: String) {
        repository.createLead(name, phone, project, assignee)
        _uiState.update { it.copy(leads = repository.listLeads(), statusMessage = "Lead created successfully") }
    }

    fun updateLeadStatus(leadId: String, status: LeadStatus) {
        repository.updateLeadStatus(leadId, status)
        _uiState.update { it.copy(leads = repository.listLeads(), statusMessage = "Lead status updated") }
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

    fun addAssociate(name: String, role: String) {
        repository.addAssociate(name, role)
        _uiState.update {
            it.copy(
                directTeam = repository.listDirectTeam(),
                totalTeam = repository.listTotalTeam(),
                statusMessage = "Associate added"
            )
        }
    }

    fun submitMaterialRequest(item: String, quantity: Int) {
        repository.createMaterialRequest(item, quantity)
        _uiState.update { it.copy(materialRequests = repository.listMaterialRequests(), statusMessage = "Material request submitted") }
    }

    fun submitVehicleRequest(purpose: String, date: String) {
        repository.createVehicleRequest(purpose, date)
        _uiState.update { it.copy(vehicleRequests = repository.listVehicleRequests(), statusMessage = "Vehicle request submitted") }
    }

    fun submitWalletWithdrawal(amount: Double) {
        repository.requestWalletWithdrawal(amount)
        _uiState.update { it.copy(walletTransactions = repository.listWalletTransactions(), statusMessage = "Withdrawal request submitted") }
    }

    fun changePassword(current: String, new: String) {
        repository.changePassword(current, new).fold(
            onSuccess = { _uiState.update { it.copy(statusMessage = "Password changed") } },
            onFailure = { error -> _uiState.update { it.copy(statusMessage = error.message ?: "Unable to change password") } }
        )
    }

    fun clearStatusMessage() {
        _uiState.update { it.copy(statusMessage = null) }
    }
}
