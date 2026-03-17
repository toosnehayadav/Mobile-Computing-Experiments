package com.example.snehaemicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlin.math.pow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SnehaEMICalculatorApp()
        }
    }
}

@Composable
fun SnehaEMICalculatorApp() {

    var selectedScreen by remember { mutableStateOf("home") }

    when (selectedScreen) {
        "home" -> HomeScreen { selectedScreen = it }
        "calculator" -> NormalCalculator { selectedScreen = "home" }
        "emi" -> EMICalculator { selectedScreen = "home" }
        "tax" -> TaxCalculator { selectedScreen = "home" }
    }
}

@Composable
fun HomeScreen(onNavigate: (String) -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text("EMI/Income Tax Calculator", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(30.dp))

        Button(onClick = { onNavigate("calculator") }, modifier = Modifier.fillMaxWidth()) {
            Text("Normal Calculator")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = { onNavigate("emi") }, modifier = Modifier.fillMaxWidth()) {
            Text("EMI Calculator")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = { onNavigate("tax") }, modifier = Modifier.fillMaxWidth()) {
            Text("Income Tax Calculator")
        }
    }
}

@Composable
fun NormalCalculator(onBack: () -> Unit) {

    var num1 by remember { mutableStateOf("") }
    var num2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Text("Normal Calculator", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = num1,
            onValueChange = { num1 = it },
            label = { Text("Enter First Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = num2,
            onValueChange = { num2 = it },
            label = { Text("Enter Second Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(horizontalArrangement = Arrangement.SpaceEvenly) {

            Button(onClick = {
                result = (num1.toDouble() + num2.toDouble()).toString()
            }) { Text("+") }

            Button(onClick = {
                result = (num1.toDouble() - num2.toDouble()).toString()
            }) { Text("-") }

            Button(onClick = {
                result = (num1.toDouble() * num2.toDouble()).toString()
            }) { Text("×") }

            Button(onClick = {
                result = (num1.toDouble() / num2.toDouble()).toString()
            }) { Text("÷") }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Result: $result")

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = onBack) {
            Text("Back")
        }
    }
}

@Composable
fun EMICalculator(onBack: () -> Unit) {

    var principal by remember { mutableStateOf("") }
    var rate by remember { mutableStateOf("") }
    var tenure by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Text("EMI Calculator", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = principal,
            onValueChange = { principal = it },
            label = { Text("Loan Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = rate,
            onValueChange = { rate = it },
            label = { Text("Interest Rate (%)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = tenure,
            onValueChange = { tenure = it },
            label = { Text("Tenure (Years)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {

            val p = principal.toDouble()
            val r = rate.toDouble() / (12 * 100)
            val n = tenure.toInt() * 12

            val emi = (p * r * (1 + r).pow(n)) /
                    ((1 + r).pow(n) - 1)

            result = "Monthly EMI: ₹ %.2f".format(emi)

        }, modifier = Modifier.fillMaxWidth()) {
            Text("Calculate EMI")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(result)

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = onBack) {
            Text("Back")
        }
    }
}

@Composable
fun TaxCalculator(onBack: () -> Unit) {

    var income by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Text("Income Tax Calculator", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = income,
            onValueChange = { income = it },
            label = { Text("Annual Income") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {

            val inc = income.toDouble()

            val tax = when {
                inc <= 250000 -> 0.0
                inc <= 500000 -> (inc - 250000) * 0.05
                inc <= 1000000 -> (250000 * 0.05) + (inc - 500000) * 0.20
                else -> (250000 * 0.05) + (500000 * 0.20) + (inc - 1000000) * 0.30
            }

            result = "Total Tax: ₹ %.2f".format(tax)

        }, modifier = Modifier.fillMaxWidth()) {
            Text("Calculate Tax")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(result)

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = onBack) {
            Text("Back")
        }
    }
}
