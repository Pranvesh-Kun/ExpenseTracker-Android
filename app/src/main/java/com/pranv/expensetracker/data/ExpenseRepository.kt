package com.pranv.expensetracker.data

import com.pranv.expensetracker.model.Expense
import kotlinx.coroutines.flow.Flow
import kotlin.math.exp

class ExpenseRepository(
    private val expenseDao: ExpenseDao
) {
    suspend fun insertExpense(expense: Expense) {
        expenseDao.insertExpense(expense)
    }
    suspend fun deleteExpense(expense: Expense) {
        expenseDao.deleteExpense(expense)
    }
    fun getAllExpenses() : Flow<List<Expense>> {
        return expenseDao.getAll()
    }

    fun getTotalExpense() : Flow<Double> {
        return expenseDao.getTotalExpense()
    }
}