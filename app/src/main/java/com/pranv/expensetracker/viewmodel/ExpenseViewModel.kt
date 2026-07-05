package com.pranv.expensetracker.viewmodel

import androidx.lifecycle.ViewModel
import com.pranv.expensetracker.data.ExpenseRepository
import com.pranv.expensetracker.model.Expense

class ExpenseViewModel (
    private val repository: ExpenseRepository
) : ViewModel() {
    fun addExpense(expense: Expense) {
        repository.insertExpense(expense)
    }
    fun deleteExpense(expense: Expense) {
        repository.deleteExpense(expense)
    }
    fun getAllExpenses() : List<Expense> {
        return repository.getAllExpenses()
    }
}