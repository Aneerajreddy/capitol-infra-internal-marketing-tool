package com.company.internalapp.model

data class Project(
    val id: String,
    val name: String,
    val location: String,
    val priceFrom: String,
    val availability: String
)

data class TeamMember(
    val id: String,
    val name: String,
    val role: String,
    val managerId: String?
)

enum class RequestStatus { PENDING, APPROVED, REJECTED }

data class MaterialRequest(
    val id: String,
    val item: String,
    val quantity: Int,
    val status: RequestStatus
)

data class VehicleRequest(
    val id: String,
    val purpose: String,
    val date: String,
    val status: RequestStatus
)

data class WalletTransaction(
    val id: String,
    val amount: Double,
    val type: String,
    val date: String
)

data class BonanzaProgram(
    val id: String,
    val name: String,
    val criteria: String
)

data class MelaUpdate(
    val id: String,
    val title: String,
    val description: String,
    val date: String
)

data class SalesRecord(
    val id: String,
    val owner: String,
    val project: String,
    val amount: Double,
    val date: String
)

data class SiteIncharge(
    val name: String,
    val phone: String,
    val site: String
)

data class CreativeAsset(
    val id: String,
    val title: String,
    val url: String
)
