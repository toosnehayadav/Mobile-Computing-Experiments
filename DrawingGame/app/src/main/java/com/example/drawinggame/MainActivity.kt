package com.example.drawinggame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DrawingGameApp()
        }
    }
}

@Composable
fun DrawingGameApp() {

    var currentShape by remember { mutableStateOf(generateShape()) }
    var score by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("🎨 Drawing Game", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(10.dp))

        Text("Draw this shape: $currentShape")

        Spacer(modifier = Modifier.height(10.dp))

        Text("Score: $score")

        Spacer(modifier = Modifier.height(10.dp))

        DrawingCanvas()

        Spacer(modifier = Modifier.height(10.dp))

        Row {

            Button(onClick = {
                currentShape = generateShape()
            }) {
                Text("New Shape")
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(onClick = {
                score++
                currentShape = generateShape()
            }) {
                Text("I Drew It!")
            }
        }
    }
}

@Composable
fun DrawingCanvas() {

    val path = remember { Path() }

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .background(Color.White)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        path.moveTo(offset.x, offset.y)
                    },
                    onDrag = { change, _ ->
                        path.lineTo(change.position.x, change.position.y)
                    }
                )
            }
    ) {
        drawPath(
            path = path,
            color = Color.Black
        )
    }

    Spacer(modifier = Modifier.height(10.dp))

    Button(onClick = {
        path.reset()
    }) {
        Text("Clear Drawing")
    }
}


fun generateShape(): String {
    val shapes = listOf(
        "Circle",
        "Square",
        "Triangle",
        "Star",
        "Rectangle"
    )
    return shapes[Random.nextInt(shapes.size)]
}
