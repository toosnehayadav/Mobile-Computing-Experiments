package com.example.drawinggame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WritingCanvasApp()
        }
    }
}

@Composable
fun WritingCanvasApp() {

    var paths by remember { mutableStateOf(listOf<Path>()) }
    var currentPath by remember { mutableStateOf(Path()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("✍ Drawing Canvas",
            style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .background(Color.White)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            currentPath = Path().apply {
                                moveTo(offset.x, offset.y)
                            }
                        },
                        onDrag = { change, _ ->
                            currentPath.lineTo(
                                change.position.x,
                                change.position.y
                            )
                        },
                        onDragEnd = {
                            paths = paths + currentPath
                        }
                    )
                }
        ) {

            // Draw previous strokes
            paths.forEach {
                drawPath(
                    path = it,
                    color = Color.Black,
                    style = Stroke(width = 8f)
                )
            }

            // Draw current stroke
            drawPath(
                path = currentPath,
                color = Color.Black,
                style = Stroke(width = 8f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                paths = emptyList()
                currentPath = Path()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Clear")
        }
    }
}

