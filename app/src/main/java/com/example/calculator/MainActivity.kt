package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var txtDisplay: TextView
    private var expression = ""  // Chứa toàn bộ phép tính
    private var firstValue: Double? = null
    private var operator: String? = null
    private var isNewOperation = true
    private var lastResult: Double? = null  // Lưu kết quả cuối cùng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtDisplay = findViewById(R.id.txtDisplay)
        setupButtons()
    }

    private fun setupButtons() {
        val numberButtons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )

        // Gán sự kiện click cho các nút số
        numberButtons.forEach { id ->
            findViewById<Button>(id).setOnClickListener {
                val text = (it as Button).text.toString()
                onNumberClick(text)
            }
        }

        // Gán sự kiện click cho các phép toán
        findViewById<Button>(R.id.btnAdd).setOnClickListener { onOperatorClick("+") }
        findViewById<Button>(R.id.btnSub).setOnClickListener { onOperatorClick("-") }
        findViewById<Button>(R.id.btnMul).setOnClickListener { onOperatorClick("*") }
        findViewById<Button>(R.id.btnDiv).setOnClickListener { onOperatorClick("/") }
        findViewById<Button>(R.id.btnEqual).setOnClickListener { onEqualClick() }
        findViewById<Button>(R.id.btnClear).setOnClickListener { onClearClick() }
        findViewById<Button>(R.id.btnBack).setOnClickListener { onBackspaceClick() }
    }

    private fun onNumberClick(value: String) {
        if (isNewOperation) {
            expression = value
            isNewOperation = false
        } else {
            expression += value
        }
        txtDisplay.text = expression
    }

    private fun onOperatorClick(op: String) {
        if (expression.isNotEmpty() && operator == null) {
            firstValue = expression.toDoubleOrNull()
            operator = op
            expression += " $op "
            txtDisplay.text = expression
            isNewOperation = false
        }
    }

    private fun onEqualClick() {
        val parts = expression.split(" ")
        if (parts.size == 3) {
            val firstValue = parts[0].toDoubleOrNull()
            val secondValue = parts[2].toDoubleOrNull()
            val operator = parts[1]

            if (firstValue != null && secondValue != null) {
                val result = when (operator) {
                    "+" -> firstValue + secondValue
                    "-" -> firstValue - secondValue
                    "*" -> firstValue * secondValue
                    "/" -> if (secondValue != 0.0) firstValue / secondValue else "Error"
                    else -> "Error"
                }

                lastResult = result.toString().toDoubleOrNull()
                expression += " = $result"
                txtDisplay.text = expression
                isNewOperation = true
            }
        }
    }

    private fun onClearClick() {
        expression = "0"
        txtDisplay.text = expression
        firstValue = null
        operator = null
        isNewOperation = true
    }

    private fun onBackspaceClick() {
        if (expression.isNotEmpty()) {
            expression = expression.dropLast(1)
        }
        if (expression.isEmpty()) {
            expression = "0"
        }
        txtDisplay.text = expression
    }
}
