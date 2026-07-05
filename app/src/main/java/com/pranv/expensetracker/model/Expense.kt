package com.pranv.expensetracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: Double,
    val merchant: String,
    val category: String,
    val paymentType: String,
    val timestamp: Long
)