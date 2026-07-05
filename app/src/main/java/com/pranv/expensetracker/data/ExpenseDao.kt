package com.pranv.expensetracker.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.pranv.expensetracker.model.Expense

@Dao
interface ExpenseDao {
    @Insert
    fun insertExpense(expense: Expense)
    @Delete
    fun deleteExpense(expense: Expense)
    @Query("SELECT * from Expense")
    fun getAll() : List<Expense>
}