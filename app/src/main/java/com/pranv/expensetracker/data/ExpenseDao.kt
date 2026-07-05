package com.pranv.expensetracker.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.pranv.expensetracker.model.Expense
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Insert
    suspend fun insertExpense(expense: Expense)
    @Delete
    suspend fun deleteExpense(expense: Expense)
    @Query("SELECT * from Expense ORDER BY timestamp DESC")
    fun getAll() : Flow<List<Expense>>

    @Query("SELECT COALESCE(SUM(amount), 0) FROM Expense")
    fun getTotalExpense() : Flow<Double>
}