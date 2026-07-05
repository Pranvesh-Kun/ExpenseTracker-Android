package com.pranv.expensetracker.utils

import android.content.Context
import com.pranv.expensetracker.model.Expense
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream

object ExcelExporter {
    fun exportExpenses(
        context: Context,
        expenses: List<Expense>
    ) : File {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Expenses")
        val header = sheet.createRow(0)

        header.createCell(0).setCellValue("Merchant")
        header.createCell(1).setCellValue("Amount")
        header.createCell(2).setCellValue("Category")
        header.createCell(3).setCellValue("Payment-Type")
        header.createCell(4).setCellValue("Date")
        expenses.forEachIndexed { index, expense ->
            val row = sheet.createRow(index+1)
            row.createCell(0).setCellValue(expense.merchant)
            row.createCell(1).setCellValue(expense.amount)
            row.createCell(2).setCellValue(expense.category)
            row.createCell(3).setCellValue(expense.paymentType)
            row.createCell(4).setCellValue(DateUtils.formatTimestamp(expense.timestamp))
        }
        val file = File(
            context.cacheDir,
            "expenses.xlsx"
        )
        FileOutputStream(file).use { output ->
            workbook.write(output)
        }
        workbook.close()
        return file
    }
}