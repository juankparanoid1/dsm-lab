package com.example.operationsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.operationsapp.ui.theme.OperationsAppTheme
import androidx.compose.runtime.setValue

class MainViewModel {
    var operatorValue1 by mutableStateOf("")
    var operatorValue2 by mutableStateOf("")
    var result by mutableStateOf("")

    fun calculateResult() {
        val value1 = operatorValue1.toFloatOrNull() ?: 0f
        val value2 = operatorValue2.toFloatOrNull() ?: 0f
        result = (value1 + value2).toString()
        cleanInputs();
    }

    fun cleanInputs() {
        operatorValue1 = "";
        operatorValue2 = "";
    }
}

class MainActivity : ComponentActivity() {
    private val viewModel = MainViewModel();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OperationsAppTheme {
                Row(
                    modifier = Modifier
                        .padding(top = 20.dp),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    InputText(
                        value = viewModel.operatorValue1,
                        onValueChanged = { viewModel.operatorValue1 = it });
                    InputText(
                        value = viewModel.operatorValue2,
                        onValueChanged = { viewModel.operatorValue2 = it });
                }
                Row(
                    modifier = Modifier
                        .padding(top = 100.dp, start = 100.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextValue(viewModel.result);
                }
                Row(
                    modifier = Modifier
                        .padding(top = 115.dp, start = 60.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button { viewModel.calculateResult() }
                }
            }
        }
    }
}

@Composable
fun TextValue(value: String, modifier: Modifier = Modifier) {
    Text(
        text = "Respuesta: $value",
        modifier = modifier
    )
}

@Composable
fun InputText(value: String, onValueChanged: (String) -> Unit) {
    BasicTextField(
        value = value,
        singleLine = true,
        onValueChange = onValueChanged,
        modifier = Modifier
            .width(200.dp)
            .height(65.dp)
            .padding(12.dp)
            .border(border = BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(5.dp)),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
        decorationBox = { innerTextField ->  //add this and config yout layout
            Row(
                modifier = Modifier.padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                innerTextField()
            }
        },
    )
}

@Composable
fun Button(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .padding(16.dp)
            .width(200.dp)
            .height(40.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(4.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Click Me",
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    OperationsAppTheme {
    }
}