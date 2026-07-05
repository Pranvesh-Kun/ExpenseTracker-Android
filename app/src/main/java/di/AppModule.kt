package com.pranv.expensetracker.di

import android.content.Context
import com.pranv.expensetracker.data.ExpenseDao
import com.pranv.expensetracker.data.ExpenseDatabase
import com.pranv.expensetracker.data.ExpenseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideExpenseDatabase(
        @ApplicationContext context: Context
    ): ExpenseDatabase {
        return ExpenseDatabase.getDatabase(context)
    }

    @Provides
    fun provideExpenseDao(
        database: ExpenseDatabase
    ): ExpenseDao {
        return database.expenseDao()
    }

    @Provides
    @Singleton
    fun provideExpenseRepository(
        dao: ExpenseDao
    ): ExpenseRepository {
        return ExpenseRepository(dao)
    }
}