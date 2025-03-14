package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var txtDisplay: TextView
    private var currentInput = ""
    private var operator: String? = null
    private var firstOperand: Double? = null
    private var isNewOperation = true

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

        numberButtons.forEach { id ->
            findViewById<Button>(id).setOnClickListener {
                val text = (it as Button).text.toString()
                onNumberClick(text)
            }
        }

        findViewById<Button>(R.id.btnAdd).setOnClickListener { onOperatorClick("+") }
        findViewById<Button>(R.id.btnSub).setOnClickListener { onOperatorClick("-") }
        findViewById<Button>(R.id.btnMul).setOnClickListener { onOperatorClick("*") }
        findViewById<Button>(R.id.btnDiv).setOnClickListener { onOperatorClick("/") }
        findViewById<Button>(R.id.btnEqual).setOnClickListener { onEqualClick() }
        findViewById<Button>(R.id.btnClear).setOnClickListener { onClearClick() }
        findViewById<Button>(R.id.btnBack).setOnClickListener { onBackspaceClick() }
        findViewById<Button>(R.id.btnDot).setOnClickListener { onDotClick() }
        findViewById<Button>(R.id.btnNegate).setOnClickListener { onNegateClick() }
    }

    private fun onNumberClick(value: String) {
        if (isNewOperation) {
            currentInput = value
            isNewOperation = false
        } else {
            currentInput += value
        }
        txtDisplay.text = currentInput
    }

    private fun onOperatorClick(op: String) {
        if (currentInput.isNotEmpty() && firstOperand == null) {
            firstOperand = currentInput.toDouble()
            operator = op
            currentInput = ""
            txtDisplay.text = "$firstOperand $operator"
        }
    }

    private fun onEqualClick() {
        if (firstOperand != null && operator != null && currentInput.isNotEmpty()) {
            val secondOperand = currentInput.toDouble()
            val result = when (operator) {
                "+" -> firstOperand!! + secondOperand
                "-" -> firstOperand!! - secondOperand
                "*" -> firstOperand!! * secondOperand
                "/" -> if (secondOperand != 0.0) firstOperand!! / secondOperand else "Error"
                else -> "Error"
            }
            txtDisplay.text = result.toString()
            currentInput = result.toString()
            firstOperand = null
            operator = null
            isNewOperation = true
        }
    }

    private fun onClearClick() {
        currentInput = "0"
        txtDisplay.text = currentInput
        firstOperand = null
        operator = null
        isNewOperation = true
    }

    private fun onBackspaceClick() {
        if (currentInput.isNotEmpty()) {
            currentInput = currentInput.dropLast(1)
        }
        if (currentInput.isEmpty()) {
            currentInput = "0"
        }
        txtDisplay.text = currentInput
    }

    private fun onDotClick() {
        if (!currentInput.contains(".")) {
            currentInput += "."
            txtDisplay.text = currentInput
        }
    }

    private fun onNegateClick() {
        if (currentInput.isNotEmpty() && currentInput != "0") {
            currentInput = if (currentInput.startsWith("-")) {
                currentInput.substring(1)
            } else {
                "-$currentInput"
            }
            txtDisplay.text = currentInput
        }
    }
}