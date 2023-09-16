package com.example.expensetracker

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseHelper: DatabaseHelper
    private  lateinit var recyclerExpenses: RecyclerView
    private lateinit var txtTotalExpense: TextView
    private lateinit var txtTotalIncome: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#ED5050")))

        databaseHelper = DatabaseHelper.getInstance(this)
        recyclerExpenses = binding.recyclerView
        recyclerExpenses.layoutManager = GridLayoutManager(this, 1)
        txtTotalExpense = binding.txtAmountExpent
        txtTotalIncome = binding.txtAmount
        fabAdd = binding.fab

        showExpenses()

        fabAdd.setOnClickListener {
            val dialog = Dialog(this@MainActivity)
            dialog.setContentView(R.layout.add_expense_layout)
            val edtTitle: EditText = dialog.findViewById(R.id.edtTitle)
            val edtAmount: EditText = dialog.findViewById(R.id.edtAmount)
            val edtRadioExpense: RadioButton = dialog.findViewById(R.id.radioExpense)
            val btnAdd: Button = dialog.findViewById(R.id.btnAdd)

            btnAdd.setOnClickListener{
                val radioButtonValue = if (edtRadioExpense.isChecked) "Expense" else "Income"
                val title = edtTitle.text.toString()
                val amountStr = edtAmount.text.toString()

                if(title.isNotBlank() && amountStr.isNotBlank()){
                    try {
                        val amount = amountStr.toInt()
                        val newExpense = Expense(title, amount, radioButtonValue)
                        databaseHelper.expenseDao().addExpense(newExpense)
                        Toast.makeText(this@MainActivity, "Saved", Toast.LENGTH_SHORT).show()

                        showExpenses()
                        dialog.dismiss()
                    } catch (e: NumberFormatException) {
                        Toast.makeText(this@MainActivity, "Invalid amount format", Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(this@MainActivity, "Fields can't be empty", Toast.LENGTH_SHORT).show()
                }
            }

            dialog.show()
        }

    }

    fun showExpenses() {
        val arrExpense = databaseHelper.expenseDao().getExpense()
        if (arrExpense.isNotEmpty()) {
            recyclerExpenses.visibility = View.VISIBLE

            recyclerExpenses.adapter = RecyclerExpenseAdapter(this ,
                arrExpense as  ArrayList<Expense>, databaseHelper
            )

            var totalExpense = 0
            var totalIncome = 0

            for (expense in arrExpense) {
                if (expense.expenseType == "Expense") {
                    totalExpense += expense.amount
                } else {
                    totalIncome += expense.amount
                }
            }

            txtTotalExpense.text = "- $totalExpense"
            txtTotalIncome.text = "+ $totalIncome"

        } else {
            println("DB IS EMPTY")
        }
    }
}