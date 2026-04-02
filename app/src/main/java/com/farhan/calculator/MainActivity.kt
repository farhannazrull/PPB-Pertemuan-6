package com.farhan.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.farhan.calculator.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CalculatorScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CalculatorScreen(
    modifier: Modifier = Modifier,
    vm: CalculatorViewModel = viewModel()
) {
    val buttonRows = listOf(
        listOf("7", "8", "9", "÷"),
        listOf("4", "5", "6", "×"),
        listOf("1", "2", "3", "−"),
        listOf("C", "0", "=", "+")
    )
    val operators = setOf("+", "−", "×", "÷", "=", "C")

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        CalculatorCard(display = vm.display, infoText = vm.infoText)
        Spacer(modifier = Modifier.height(16.dp))
        ButtonGrid(
            rows = buttonRows,
            operators = operators,
            onDigit = { vm.onDigitClick(it) },
            onOperator = { vm.onOperatorClick(it) },
            onEquals = { vm.onEqualsClick() },
            onClear = { vm.onClearClick() }
        )
    }
}

@Composable
fun CalculatorCard(display: String, infoText: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2C2C2E))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = infoText,
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = display,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.End,
                maxLines = 1
            )
        }
    }
}

@Composable
fun ButtonGrid(
    rows: List<List<String>>,
    operators: Set<String>,
    onDigit: (String) -> Unit,
    onOperator: (String) -> Unit,
    onEquals: () -> Unit,
    onClear: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        rows.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { label ->
                    CalculatorButton(
                        label = label,
                        isOperator = label in operators,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            when (label) {
                                "=" -> onEquals()
                                "C" -> onClear()
                                in operators -> onOperator(label)
                                else -> onDigit(label)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(
    label: String,
    isOperator: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    if (isOperator) {
        Button(
            onClick = onClick,
            modifier = modifier.height(72.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF9F0A),
                contentColor = Color.White
            )
        ) {
            Text(text = label, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
    } else {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier.height(72.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.White
            ),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF3A3A3C))
        ) {
            Text(text = label, fontSize = 24.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Calculator Preview")
@Composable
fun CalculatorPreview() {
    CalculatorTheme {
        CalculatorScreen()
    }
}
