package com.farhan.calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {

    var display by mutableStateOf("0")
        private set

    var infoText by mutableStateOf("")
        private set

    private var firstOperand: Double? = null
    private var pendingOp: String? = null
    private var isEnteringSecond = false

    fun onDigitClick(d: String) {
        if (isEnteringSecond) {
            display = if (display == "0") d else display + d
        } else {
            display = if (display == "0") d else display + d
        }
    }

    fun onOperatorClick(op: String) {
        if (firstOperand != null && pendingOp != null && isEnteringSecond) {
            val result = calculate(firstOperand!!, pendingOp!!, display.toDoubleOrNull() ?: 0.0)
            firstOperand = result
            display = formatNumber(result)
            infoText = "${formatNumber(firstOperand!!)} $op"
        } else {
            firstOperand = display.toDoubleOrNull() ?: 0.0
            infoText = "${formatNumber(firstOperand!!)} $op"
        }
        pendingOp = op
        isEnteringSecond = true
        display = "0"
    }

    fun onEqualsClick() {
        val a = firstOperand ?: return
        val op = pendingOp ?: return
        val b = display.toDoubleOrNull() ?: 0.0
        val result = calculate(a, op, b)
        infoText = "${formatNumber(a)} $op ${formatNumber(b)} ="
        display = formatNumber(result)
        firstOperand = null
        pendingOp = null
        isEnteringSecond = false
    }

    fun onClearClick() {
        display = "0"
        infoText = ""
        firstOperand = null
        pendingOp = null
        isEnteringSecond = false
    }

    private fun calculate(a: Double, op: String, b: Double): Double {
        return when (op) {
            "+" -> a + b
            "−" -> a - b
            "×" -> a * b
            "÷" -> if (b != 0.0) a / b else Double.NaN
            else -> b
        }
    }

    private fun formatNumber(value: Double): String {
        return if (value == value.toLong().toDouble()) {
            value.toLong().toString()
        } else {
            value.toString()
        }
    }
}
