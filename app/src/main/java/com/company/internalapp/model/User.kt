package com.company.internalapp.model

data class User(
    val id: String,
    val name: String,
    val role: Role,
    val employeeId: String,
    val mobile: String
)
