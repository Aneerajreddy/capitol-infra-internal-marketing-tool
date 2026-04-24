package com.company.internalapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "leads")
data class LeadEntity(
    @PrimaryKey val id: String,
    val name: String,
    val phone: String,
    val status: String,
    val assignedTo: String,
    val updatedAt: String
)
