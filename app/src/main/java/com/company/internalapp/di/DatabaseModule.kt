package com.company.internalapp.di

import android.content.Context
import androidx.room.Room
import com.company.internalapp.data.local.AppDatabase
import com.company.internalapp.data.local.LeadDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "crm.db").build()

    @Provides
    fun provideLeadDao(db: AppDatabase): LeadDao = db.leadDao()
}
