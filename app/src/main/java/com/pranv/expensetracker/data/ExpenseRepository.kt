package com.pranv.expensetracker.data

import com.pranv.expensetracker.model.Expense

class ExpenseRepository(
    private val expenseDao: ExpenseDao
) {
    fun insertExpense(expense: Expense) {
        expenseDao.insertExpense(expense)
    }
    fun deleteExpense(expense: Expense) {
        expenseDao.deleteExpense(expense)
    }
    fun getAllExpenses() : List<Expense> {
        return expenseDao.getAll()
    }
}