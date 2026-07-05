package com.pranv.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
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

@Composable
fun ExpenseEntryScreen() {

    var merchant by remember {
        mutableStateOf("")
    }

    var amount by remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier.fillMaxSize().padding(vertical = 55.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Expense Tracker",
            modifier = Modifier
        )
        TextField(
            value = merchant,
            onValueChange = { newText ->
                merchant = newText
            }
        )
        TextField(
            value = amount,
            onValueChange = { newText ->
                amount = newText
            }
        )
    }
}