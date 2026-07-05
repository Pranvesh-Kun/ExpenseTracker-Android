package com.pranv.expensetracker

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pranv.expensetracker.ui.theme.ExpenseTrackerTheme
import com.pranv.expensetracker.viewmodel.ExpenseViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.pranv.expensetracker.model.Expense
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.lazy.items

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            ExpenseTrackerTheme {
                ExpenseEntryScreen(

                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseEntryScreen() {

    var merchant by remember {
        mutableStateOf("")
    }

    var amount by remember {
        mutableStateOf("")
    }

    val categories = listOf("Food", "Travel", "Shopping", "Medical", "Education", "Utilities", "Others")
    val paymenttypes = listOf("Cash", "UPI", "Credit Card", "Debit Card", "Netbanking", "Wallet", "Other")

    var selectedCategory by remember {
        mutableStateOf("")
    }

    var selectedPaymentType by remember {
        mutableStateOf("")
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    var expanded2 by remember {
        mutableStateOf(false)
    }

    val viewmodel: ExpenseViewModel = hiltViewModel()

    val expenses by viewmodel.expenses.collectAsState(initial = emptyList())

    val totalExpense by viewmodel.totalExpense.collectAsState(initial = 0.0)

    val isFormValid =
                merchant.isNotBlank() &&
                amount.isNotBlank() &&
                selectedPaymentType.isNotBlank() &&
                selectedCategory.isNotBlank()

    Column(modifier = Modifier.fillMaxSize().padding(vertical = 55.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(
            modifier = Modifier.size(10.dp)
        )
        Text(
            text = "Total Spent: ₹%.2f".format(totalExpense)
        )
        Spacer(
            modifier = Modifier.size(10.dp)
        )
        Text(
            text = "Expense Tracker",
            modifier = Modifier
        )
        Spacer(
            Modifier.size(16.dp)
        )
        TextField(
            value = merchant,
            onValueChange = { newText ->
                merchant = newText
            },
            label = { Text(text = "Merchant: ", modifier = Modifier) }
        )
        Spacer(
            modifier = Modifier.size(20.dp)
        )
        TextField(
            value = amount,
            onValueChange = { newText ->
                amount = newText
            },
            label = { Text(text = "Amount: ", modifier = Modifier) }
        )
        Spacer(
            modifier = Modifier.size(16.dp)
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                onValueChange = {},
                value = selectedCategory,
                modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
                label = {
                    Text(text = "Select a Category:")
                },
                readOnly = true
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                categories.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(text = item)
                        },
                        onClick = {
                          selectedCategory = item
                            expanded = false
                        }
                    )
                }
            }
        }
        Spacer(
            Modifier.size(16.dp)
        )
        ExposedDropdownMenuBox(
            expanded = expanded2,
            onExpandedChange = { expanded2 = !expanded2 }
        ) {
            OutlinedTextField(
                onValueChange = {},
                value = selectedPaymentType,
                modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
                label = {
                    Text(text = "Select Payment Type:")
                },
                readOnly = true
            )
            ExposedDropdownMenu(
                expanded = expanded2,
                onDismissRequest = {
                    expanded2 = false
                }
            ) {
                paymenttypes.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(text = item)
                        },
                        onClick = {
                            selectedPaymentType = item
                            expanded2 = false
                        }
                    )
                }
            }
        }
        Spacer(
            Modifier.size(16.dp)
        ) //
        Button(
            enabled = isFormValid,
            onClick = {
                viewmodel.addExpense(
                    Expense(
                        amount = amount.toDouble(),
                        merchant = merchant,
                        category = selectedCategory,
                        paymentType = selectedPaymentType,
                        timestamp = System.currentTimeMillis()
                    )
                )
            }
        ) {
            Text(
                text = "Add Expense"
            )
        }
        LazyColumn(
//            modifier = Modifier.weight(
        ) {
            items(expenses) { expense ->
                ExpenseItem(expense)
            }
        }
    }
}

@Composable
fun ExpenseItem(exp: Expense) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = exp.merchant)
        Text("₹%.2f".format(exp.amount))
        Text(text = exp.category)
        Text(text = exp.paymentType)
    }
}