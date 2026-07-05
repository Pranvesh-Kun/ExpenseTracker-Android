package com.pranv.expensetracker
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.Card
import androidx.compose.ui.text.font.FontWeight
import com.pranv.expensetracker.utils.DateUtils
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory
import org.intellij.lang.annotations.JdkConstants
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.exp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            ExpenseTrackerTheme {
                ExpenseEntryScreen(

                )
//                Text(text = "hello")
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

    var expanded3 by remember {
        mutableStateOf(false)
    }

    var editingExpense by remember {
        mutableStateOf<Expense?>(null)
    }

    val viewmodel: ExpenseViewModel = hiltViewModel()

    val expenses by viewmodel.expenses.collectAsState(initial = emptyList())

    val totalExpense by viewmodel.totalExpense.collectAsState(initial = 0.0)

    val isFormValid =
                merchant.isNotBlank() &&
                amount.isNotBlank() &&
                selectedPaymentType.isNotBlank() &&
                selectedCategory.isNotBlank()

    val filterCategories = listOf(
        "All",
        "Food",
        "Travel",
        "Shopping",
        "Medical",
        "Education",
        "Utilities",
        "Others"
    )

    var searchQuery by remember {
        mutableStateOf("")
    }

    var selectedFilterCategory by remember {
        mutableStateOf("All")
    }

    val filteredExpenses = expenses.filter {
        val matchesSearch = it.merchant.contains(searchQuery, ignoreCase = true)
        val matchesCategory = selectedFilterCategory == "All" || it.category == selectedFilterCategory
        matchesSearch && matchesCategory
    }

    val calender = Calendar.getInstance()
    val currentMonth = calender.get(Calendar.MONTH)
    val currentYear = calender.get(Calendar.YEAR)

    val monthlyExpenses = expenses.filter { expense ->
        val cal = Calendar.getInstance()
        cal.timeInMillis = expense.timestamp
        cal.get(Calendar.MONTH) == currentMonth &&
        cal.get(Calendar.YEAR) == currentYear
    }

    val monthlyTotal = monthlyExpenses.sumOf {
        it.amount
    }
    val monthlyTransactions = monthlyExpenses.size
    val biggestExpense = monthlyExpenses.maxOfOrNull {
        it.amount
    }
    val averageExpense =
        if (monthlyTransactions == 0) 0.0
        else monthlyTotal / monthlyTransactions

    var showForm by remember {
        mutableStateOf(true)
    }

    Column(modifier = Modifier.fillMaxSize().padding(vertical = 55.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(
            modifier = Modifier.size(8.dp)
        )
        Text(
            text = "Total Spent: ₹%.2f".format(totalExpense)
        )
        Spacer(
            modifier = Modifier.size(8.dp)
        )
        Text(
            text = "Expense Tracker",
            modifier = Modifier
        )
        Spacer(
            Modifier.size(8.dp)
        )
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                val monthName = SimpleDateFormat(
                    "MMMM yyyy",
                    Locale.getDefault()
                ).format(Date())
                Text(
                    text = "Summary for $monthName",
                    fontWeight = FontWeight.Bold
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total: ₹%.2f".format(monthlyTotal)
                    )
                    Text(
                        text = "Count: $monthlyTransactions"
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Average: ₹%.2f".format(averageExpense)
                    )
                    Text(
                        text = "Max: ₹%.2f".format(biggestExpense)
                    )
                }
            }
        }
        Spacer(Modifier.size(8.dp))
        Button(
            onClick = {
                showForm = !showForm
            }
        ) {
            Text(
                if (showForm)
                    "Hide Form ▲"
                else
                    "Add Expense ▼"
            )
        }
        Spacer(
            Modifier.size(8.dp)
        )
        if (showForm) {
            TextField(
                value = merchant,
                onValueChange = { newText ->
                    merchant = newText
                },
                label = { Text(text = "Merchant: ", modifier = Modifier) }
            )
            Spacer(
                modifier = Modifier.size(8.dp)
            )
            TextField(
                value = amount,
                onValueChange = { newText ->
                    amount = newText
                },
                label = { Text(text = "Amount: ", modifier = Modifier) }
            )
            Spacer(
                modifier = Modifier.size(0.dp)
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
                Modifier.size(0.dp)
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
                Modifier.size(8.dp)
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
                    merchant = ""
                    amount = ""
                    selectedCategory = ""
                    selectedPaymentType = ""
                    showForm = false
                }
            ) {
                Text(
                    text = "Add Expense"
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                modifier = Modifier.weight(1f),
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                },
                label = {
                    Text("Search Merchant:")
                }
            )
            ExposedDropdownMenuBox(
                expanded = expanded3,
                onExpandedChange = { expanded3 = !expanded3 }
            ) {
                OutlinedTextField(
                    onValueChange = {},
                    value = selectedFilterCategory,
                    modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable).width(90.dp),
                    readOnly = true,
                    maxLines = 1
                )
                ExposedDropdownMenu(
                    expanded = expanded3,
                    onDismissRequest = {
                        expanded3 = false
                    }
                ) {
                    filterCategories.forEach { item ->
                        DropdownMenuItem(
                            text = {
                                Text(text = item)
                            },
                            onClick = {
                                selectedFilterCategory= item
                                expanded3 = false
                            }
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(1f)
        ) {
            items(filteredExpenses, key = {it.id}) { expense ->
                val dismissState = rememberSwipeToDismissBoxState()
                SwipeToDismissBox(
                    state = dismissState,
                    modifier = Modifier,
                    enableDismissFromStartToEnd = false,
                    onDismiss = { direction ->
                        if (direction == SwipeToDismissBoxValue.EndToStart) {
                            viewmodel.deleteExpense(expense)
                        }
                    },
                    backgroundContent = {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(MaterialTheme.colorScheme.error)
                                .padding(horizontal = 20.dp)
                                .fillMaxSize(),

                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = MaterialTheme.colorScheme.onError
                            )
                        }
                    }
                ) {
                    ExpenseItem(
                        exp = expense,
                        onEdit = {
                            editingExpense = it
                        }
                    )
                }
                Spacer(Modifier.size(8.dp))
            }
        }
    }
    if (editingExpense != null) {
        var merchant by remember(editingExpense) {
            mutableStateOf(editingExpense!!.merchant)
        }

        var amount by remember(editingExpense) {
            mutableStateOf(editingExpense!!.amount.toString())
        }

        var category by remember(editingExpense) {
            mutableStateOf(editingExpense!!.category)
        }

        var paymentType by remember(editingExpense) {
            mutableStateOf(editingExpense!!.paymentType)
        }
        var expanded by remember () {
            mutableStateOf(false)
        }

        var expanded2 by remember {
            mutableStateOf(false)
        }

        AlertDialog(
            onDismissRequest = {
                editingExpense = null
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewmodel.updateExpense(
                            editingExpense!!.copy(
                                merchant = merchant,
                                amount = amount.toDouble(),
                                category = category,
                                paymentType = paymentType
                            )
                        )
                        editingExpense = null
                    }
                ) {
                    Text("Submit")
                }
            },
            title = {
                Text("Edit Expense")
            },
            text = {
                Column() {
                    TextField(
                        value = merchant,
                        onValueChange = {
                            merchant = it
                        },
                        label = {
                            Text("Merchant")
                        }
                    )
                    Spacer(
                        modifier = Modifier.size(10.dp)
                    )
                    TextField(
                        value = amount,
                        onValueChange = { newText ->
                            amount = newText
                        },
                        label = { Text(text = "Amount: ", modifier = Modifier) }
                    )
                    Spacer(
                        modifier = Modifier.size(8.dp)
                    )
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            onValueChange = {},
                            value = category,
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
                                        category = item
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
                            value = paymentType,
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
                                        paymentType = item
                                        expanded2 = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun ExpenseItem(
    exp: Expense,
    onEdit: (Expense) -> Unit
) {
    Card(
        modifier = Modifier.clickable {
            onEdit(exp)
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = exp.merchant,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "₹%.2f".format(exp.amount),
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column() {
                    Text("${exp.category} • ${exp.paymentType}")
                    Text(text = DateUtils.formatTimestamp(exp.timestamp))
                }
            }

        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}