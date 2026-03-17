package com.example.cellularfrequencyreuse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FrequencyReuseScreen()
        }
    }
}

@Composable
fun FrequencyReuseScreen() {

    var iValue by remember { mutableStateOf("") }
    var jValue by remember { mutableStateOf("") }
    var radius by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            "Cellular Frequency Reuse",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = iValue,
            onValueChange = { iValue = it },
            label = { Text("Enter i value") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = jValue,
            onValueChange = { jValue = it },
            label = { Text("Enter j value") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = radius,
            onValueChange = { radius = it },
            label = { Text("Enter Cell Radius (R)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                if (iValue.isNotEmpty() && jValue.isNotEmpty() && radius.isNotEmpty()) {

                    val i = iValue.toInt()
                    val j = jValue.toInt()
                    val r = radius.toDouble()

                    // Cluster size formula
                    val N = i*i + i*j + j*j

                    // Reuse distance formula
                    val D = sqrt(3 * N.toDouble()) * r

                    result = """
                        📌 Cluster Size (N) = $N
                        
                        📌 Reuse Distance (D) = %.2f
                        
                        📌 Co-channel cells are obtained by:
                        Move $i cells in one direction,
                        then move $j cells at 60°.
                        
                        📌 Frequency Reuse improves:
                        • Spectral Efficiency
                        • Network Capacity
                        • Coverage Planning
                    """.trimIndent().format(D)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calculate")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(result)
    }
}
