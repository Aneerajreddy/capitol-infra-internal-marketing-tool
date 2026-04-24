package com.company.internalapp.data

import com.company.internalapp.model.BonanzaProgram
import com.company.internalapp.model.CreativeAsset
import com.company.internalapp.model.Lead
import com.company.internalapp.model.LeadStatus
import com.company.internalapp.model.MaterialRequest
import com.company.internalapp.model.MelaUpdate
import com.company.internalapp.model.Project
import com.company.internalapp.model.RequestStatus
import com.company.internalapp.model.Role
import com.company.internalapp.model.SalesRecord
import com.company.internalapp.model.SiteIncharge
import com.company.internalapp.model.TeamMember
import com.company.internalapp.model.User
import com.company.internalapp.model.VehicleRequest
import com.company.internalapp.model.WalletTransaction

class FakeBackendRepository {
    private val demoUsers = listOf(
        User("1", "Admin User", Role.ADMIN, "EMP-1001", "9000000001"),
        User("2", "Meera Manager", Role.MANAGER, "EMP-2001", "9000000002"),
        User("3", "Arun Associate", Role.ASSOCIATE, "EMP-3001", "9000000003")
    )

    private val team = mutableListOf(
        TeamMember("T1", "Meera Manager", "Manager", null),
        TeamMember("T2", "Arun Associate", "Associate", "T1"),
        TeamMember("T3", "Pooja Associate", "Associate", "T1")
    )

    private val leads = mutableListOf(
        Lead("L-101", "Ravi Kumar", "9811111111", "Green Valley", "Arun Associate", LeadStatus.NEW),
        Lead("L-102", "Nisha Verma", "9822222222", "Skyline Residency", "Arun Associate", LeadStatus.INTERESTED),
        Lead("L-103", "Farhan Ali", "9833333333", "Green Valley", "Meera Manager", LeadStatus.SITE_VISIT)
    )

    private val projects = listOf(
        Project("P1", "Green Valley", "Noida", "₹45L", "23 Units"),
        Project("P2", "Skyline Residency", "Gurugram", "₹60L", "11 Units")
    )

    private val materialRequests = mutableListOf(
        MaterialRequest("MR-11", "Brochure", 50, RequestStatus.PENDING)
    )

    private val vehicleRequests = mutableListOf(
        VehicleRequest("VR-31", "Client site visit", "2026-04-19", RequestStatus.APPROVED)
    )

    private val walletTransactions = mutableListOf(
        WalletTransaction("WT-1", 25000.0, "Credit", "2026-04-01"),
        WalletTransaction("WT-2", 5000.0, "Debit", "2026-04-07")
    )

    private val bonanza = listOf(
        BonanzaProgram("B-1", "Q2 Super Seller", "3 conversions in current month")
    )

    private val melaUpdates = listOf(
        MelaUpdate("M-1", "Weekend Mela", "Special discount drive for premium leads", "2026-04-20")
    )

    private val salesRecords = listOf(
        SalesRecord("S-1", "Arun Associate", "Green Valley", 4_500_000.0, "2026-04-05"),
        SalesRecord("S-2", "Meera Manager", "Skyline Residency", 6_200_000.0, "2026-04-11")
    )

    private val siteIncharges = listOf(
        SiteIncharge("Rajesh Kumar", "+91-9876543210", "Green Valley"),
        SiteIncharge("Anita Singh", "+91-9988776655", "Skyline Residency")
    )

    private val creatives = listOf(
        CreativeAsset("C-1", "April Campaign Poster", "https://example.com/creative-1"),
        CreativeAsset("C-2", "Site Visit Deck", "https://example.com/creative-2")
    )

    fun login(mobile: String, passwordOrOtp: String): Result<User> {
        val user = demoUsers.find { it.mobile == mobile }
        return if (user != null && passwordOrOtp.isNotBlank()) {
            Result.success(user)
        } else {
            Result.failure(IllegalArgumentException("Invalid mobile number or credentials."))
        }
    }

    fun listLeads(): List<Lead> = leads.toList()
    fun listProjects(): List<Project> = projects
    fun listDirectTeam(): List<TeamMember> = team.filter { it.managerId != null }
    fun listTotalTeam(): List<TeamMember> = team.toList()
    fun listMaterialRequests(): List<MaterialRequest> = materialRequests.toList()
    fun listVehicleRequests(): List<VehicleRequest> = vehicleRequests.toList()
    fun listWalletTransactions(): List<WalletTransaction> = walletTransactions.toList()
    fun listBonanza(): List<BonanzaProgram> = bonanza
    fun listMelaUpdates(): List<MelaUpdate> = melaUpdates
    fun listSalesRecords(): List<SalesRecord> = salesRecords
    fun listSiteIncharges(): List<SiteIncharge> = siteIncharges
    fun listCreatives(): List<CreativeAsset> = creatives

    fun createLead(name: String, phone: String, project: String, assignee: String): Lead {
        val lead = Lead(
            id = "L-${100 + leads.size + 1}",
            customerName = name,
            phone = phone,
            project = project,
            assignedTo = assignee,
            status = LeadStatus.NEW
        )
        leads.add(0, lead)
        return lead
    }

    fun updateLeadStatus(leadId: String, status: LeadStatus) {
        val index = leads.indexOfFirst { it.id == leadId }
        if (index != -1) {
            leads[index] = leads[index].copy(status = status)
        }
    }

    fun addAssociate(name: String, role: String): TeamMember {
        val member = TeamMember("T${team.size + 1}", name, role, "T1")
        team.add(member)
        return member
    }

    fun createMaterialRequest(item: String, quantity: Int) {
        materialRequests.add(0, MaterialRequest("MR-${materialRequests.size + 12}", item, quantity, RequestStatus.PENDING))
    }

    fun createVehicleRequest(purpose: String, date: String) {
        vehicleRequests.add(0, VehicleRequest("VR-${vehicleRequests.size + 32}", purpose, date, RequestStatus.PENDING))
    }

    fun requestWalletWithdrawal(amount: Double) {
        walletTransactions.add(0, WalletTransaction("WT-${walletTransactions.size + 1}", amount, "Debit", "2026-04-17"))
    }

    fun changePassword(current: String, new: String): Result<Unit> {
        return if (current.isBlank() || new.length < 6) {
            Result.failure(IllegalArgumentException("Current password invalid or new password too short."))
        } else {
            Result.success(Unit)
        }
    }
}
