package com.company.internalapp.data.repository

import com.company.internalapp.data.local.LeadDao
import com.company.internalapp.data.local.LeadEntity
import com.company.internalapp.data.remote.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LeadRepository @Inject constructor(
    private val api: ApiService,
    private val dao: LeadDao
) {
    fun observeLeads(): Flow<List<LeadEntity>> = dao.observeAll()

    suspend fun syncLeads(page: Int = 1, pageSize: Int = 50) {
        val response = api.getLeads(page, pageSize)
        dao.upsertAll(response.items.map {
            LeadEntity(
                id = it.id,
                name = it.name,
                phone = it.phone,
                status = it.status,
                assignedTo = it.assignedTo,
                updatedAt = it.updatedAt
            )
        })
    }
}
