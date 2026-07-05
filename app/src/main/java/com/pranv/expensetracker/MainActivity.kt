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

    var selectedCategory by remember {
        mutableStateOf("")
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    Column(modifier = Modifier.fillMaxSize().padding(vertical = 55.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(
            modifier = Modifier.size(165.dp)
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
            modifier = Modifier.size(20.dp)
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
        Button(
            onClick = {
                Log.d("Check","Merchant: $merchant")
                Log.d("Check","Amount: $amount")
            }
        ) {
            Text(
                text = "Add Expense"
            )
        }
    }
}