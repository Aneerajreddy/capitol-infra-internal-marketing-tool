package com.company.internalapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LeadEntity::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun leadDao(): LeadDao
}
