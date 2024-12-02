package com.example.calculatrice

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.widget.Button
import android.widget.EditText

class MainActivity : ComponentActivity() {
    private lateinit var resultEditText: EditText
    private var operand1: Double? = null
    private var pendingOperation = "="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultEditText = findViewById(R.id.editTextResult)

        val numberButtons = listOf(R.id.button0, R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9)
        numberButtons.forEach { buttonId ->
            findViewById<Button>(buttonId).setOnClickListener {
                resultEditText.append((it as Button).text)
            }
        }
        val operationButtons = listOf(R.id.buttonAdd, R.id.buttonSubtract, R.id.buttonMultiply, R.id.buttonDivide)
        operationButtons.forEach { buttonId ->
            findViewById<Button>(buttonId).setOnClickListener {
                val operation = (it as Button).text.toString()
                if (resultEditText.text.isNotEmpty()) {
                    operand1 = resultEditText.text.toString().toDouble()
                    pendingOperation = operation
                    resultEditText.append(operation)
                }
            }
        }

        findViewById<Button>(R.id.buttonEqual).setOnClickListener {
            if (operand1 != null && resultEditText.text.isNotEmpty()) {
                val value = resultEditText.text.toString().substringAfterLast(pendingOperation).toDouble()
                resultEditText.text.clear()
                performOperation(value, pendingOperation)
                pendingOperation = "="
            }
        }

        findViewById<Button>(R.id.buttonPoint).setOnClickListener {
            if (!resultEditText.text.toString().contains(".") ||
                resultEditText.text.toString().last() in listOf('+', '-', '*', '/')) {
                resultEditText.append(".")
            }
        }

        findViewById<Button>(R.id.buttonDelete).setOnClickListener {
            resultEditText.text.clear()
            operand1 = null
            pendingOperation = "="
        }
    }

    private fun performOperation(value: Double, operation: String) {
        when (operation) {
            "/" -> operand1 = if (value == 0.0) Double.NaN else operand1!! / value
            "*" -> operand1 = operand1!! * value
            "-" -> operand1 = operand1!! - value
            "+" -> operand1 = operand1!! + value
        }
        resultEditText.setText(operand1.toString())
        operand1 = null
    }
}