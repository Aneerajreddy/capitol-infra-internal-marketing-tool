package com.company.internalapp.model

enum class LeadStatus {
    NEW,
    INTERESTED,
    NOT_INTERESTED,
    SITE_VISIT,
    CONVERTED
}

data class Lead(
    val id: String,
    val customerName: String,
    val phone: String,
    val project: String,
    val assignedTo: String,
    val status: LeadStatus
)
