package com.company.internalapp.data

import com.company.internalapp.model.Lead
import com.company.internalapp.model.LeadStatus
import com.company.internalapp.model.Role
import com.company.internalapp.model.User

class FakeBackendRepository {
    private val demoUsers = listOf(
        User("1", "Admin User", Role.ADMIN, "EMP-1001", "9000000001"),
        User("2", "Meera Manager", Role.MANAGER, "EMP-2001", "9000000002"),
        User("3", "Arun Associate", Role.ASSOCIATE, "EMP-3001", "9000000003")
    )

    private val leads = mutableListOf(
        Lead("L-101", "Ravi Kumar", "9811111111", "Green Valley", "Arun Associate", LeadStatus.NEW),
        Lead("L-102", "Nisha Verma", "9822222222", "Skyline Residency", "Arun Associate", LeadStatus.INTERESTED),
        Lead("L-103", "Farhan Ali", "9833333333", "Green Valley", "Meera Manager", LeadStatus.SITE_VISIT)
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
}
