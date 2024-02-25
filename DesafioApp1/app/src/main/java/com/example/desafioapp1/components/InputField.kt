package com.example.desafioapp1.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun InputField(
    label: String,
    keyboardType: KeyboardOptions,
    modifier: Modifier,
    onValueChanged: (String) -> Unit,
    cleanAllValues: Boolean = false
) {
    var textValue by remember { mutableStateOf(TextFieldValue()) };
    var delayedText by remember { mutableStateOf(TextFieldValue()) }
    var wasCleaned by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope();

    fun updateTextAfterDelay(newText: TextFieldValue) {
        coroutineScope.launch {
            val oldValue = newText.text
            delay(2000)
            if (textValue.text == oldValue) {
                delayedText = newText
                onValueChanged(delayedText.text)
            }
        }
    }

    // Function to handle cleaning all values
    fun cleanAll() {
        if (keyboardType == KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)) {
            onValueChanged("")
            textValue = TextFieldValue("");
        } else {
            onValueChanged("0")
            textValue = TextFieldValue("0");
        }
        wasCleaned = true
    }

    // Check if cleanAllValues is true and clean the values accordingly
    if (cleanAllValues && !wasCleaned) {
        wasCleaned = false;
        cleanAll()
    }

    TextField(
        value = textValue,
        onValueChange = {
            if(it.text.isNotBlank()){
                textValue = it;
                updateTextAfterDelay(it);
            }else{
                textValue = TextFieldValue()
                if(keyboardType == KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)) {
                    onValueChanged("");
                }else {
                    onValueChanged("0");
                }
            }
             },
        label = {
                Text(
                    text = label
                );
        },
        keyboardOptions = keyboardType,
        modifier = modifier,
        maxLines = 1,
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Gray,
            unfocusedTextColor = Color.Gray,
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
//        trailingIcon = {
//            Icon(
//                Icons.Filled.Clear,
//                contentDescription = "sdsd",
//                modifier = Modifier
//                    .clickable {
//                        if(keyboardType == KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)) {
//                            onValueChanged("");
//                            textValue = TextFieldValue("");
//                        }else {
//                            onValueChanged("0");
//                            textValue = TextFieldValue("0");
//                        }
//                    }
//            )
//        }
    )
}