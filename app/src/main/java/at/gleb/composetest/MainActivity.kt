package at.gleb.composetest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import at.gleb.composetest.ui.theme.ComposeTestTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    private var canvasWidth = 0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTestTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Column {
                        Area()
                        Field()
                    }
                }
            }
        }

    }

    @Composable
    fun Area() {
        val state = viewModel.areaLiveData.observeAsState()
        val stateRemember = remember { state }
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = "Biggest Rectangle: ${stateRemember.value}")
        }
    }

    @Preview
    @Composable
    fun Field() {
        val state = viewModel.fieldLiveData.observeAsState()
        val stateRemember = remember { state }

        //Just draw the field on the canvass
        Canvas(modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures {
                    stateRemember.value?.run {
                        val x = (it.x / (canvasWidth / size)).toInt()
                        val y = (it.y / (canvasWidth / this[0].size)).toInt()
                        viewModel.clickOn(x, y)
                    }
                }
            }
        ) {
            stateRemember.value?.let {
                canvasWidth = size.width
                val rectSize = size.width / it.size

                for (i in it.indices) {
                    for (j in it[i].indices) {
                        val color = it[i][j].stateColor
                        val offset = Offset(
                            x = rectSize * i,
                            y = rectSize * j
                        )

                        drawRect(
                            color = color,
                            topLeft = offset,
                            size = Size(rectSize, rectSize),
                        )
                    }
                }

                for (row in 1 until it.size) {
                    drawLine(
                        start = Offset(x = row * rectSize, y = 0f),
                        end = Offset(x = row * rectSize, y = size.width),
                        color = Color.Black,
                        strokeWidth = 1.dp.value
                    )
                }

                for (col in 1 until it[0].size) {
                    drawLine(
                        start = Offset(y = col * rectSize, x = 0f),
                        end = Offset(y = col * rectSize, x = size.height),
                        color = Color.Black,
                        strokeWidth = 1.dp.value
                    )
                }
            }

        }
    }
}

private val Int.stateColor
    get() = when (this) {
        1 -> Color.DarkGray
        2 -> Color.Red
        else -> Color.Gray
    }

