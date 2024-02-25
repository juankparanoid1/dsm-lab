package com.example.desafioapp1.Screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.desafioapp1.components.InputField
import com.example.desafioapp1.models.CalcBasicOperation
import com.example.desafioapp1.models.EmployeeSalary
import java.math.BigDecimal
import java.math.RoundingMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Ejercicio3(navigateToHome: (() -> Unit)? = null) {
    val options = listOf("Sumar", "Restar", "Multiplicar", "Dividir")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }

    var calc by rememberSaveable {
        mutableStateOf(CalcBasicOperation());
    }

    var cleanAllValues by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Ejercicio 3",
                        fontWeight = FontWeight.W500,
                        fontSize = 25.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                ),
                navigationIcon = {
                    if (navigateToHome != null) {
                        IconButton(
                            onClick = navigateToHome
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Menu"
                            )
                        }
                    }
                }
            )
        }
    )
    { paddingValies ->
        Column(
            modifier = Modifier
                .padding(
                    top = paddingValies.calculateTopPadding(),
                    bottom = paddingValies.calculateBottomPadding(),
                )
                .padding(horizontal = 20.dp)
        ) {
            val typeNumber = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Decimal
            );
            InputField(
                label = "Valor 1",
                keyboardType = typeNumber,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                cleanAllValues = cleanAllValues,
                onValueChanged = { value ->
                    if(value.toBigDecimal() > BigDecimal.ZERO) {
                        calc = calc.copy(value1 = value.toBigDecimal());
                    }
                }
            )
            InputField(
                label = "Valor 2",
                keyboardType = typeNumber,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                cleanAllValues = cleanAllValues,
                onValueChanged = { value ->
                    if(value.toBigDecimal() > BigDecimal.ZERO) {
                        calc = calc.copy(value2 = value.toBigDecimal());
                    }
                }
            )

            Row(
            ) {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    }
                ) {
                    TextField(
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        textStyle = TextStyle.Default.copy(
                            fontSize = 17.sp
                        ),
                        readOnly = true,
                        value = selectedOptionText,
                        onValueChange = {},
                        label = {
                            Text(text = "Seleccione la operación", fontSize = 14.sp)
                        },
                        //trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        shape = RoundedCornerShape(8.dp),
                        colors = ExposedDropdownMenuDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer
                        ),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    )
                    DropdownMenu(
                        modifier = Modifier.size(width = 370.dp, height = 200.dp),
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        },
                    ) {
                        options.forEach {option ->
                            DropdownMenuItem(
                                modifier = Modifier.fillMaxWidth(),
                                text = {
                                    Text(
                                        text = option,
                                        fontSize = 17.sp,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                },
                                onClick = {
                                    selectedOptionText = option
                                    expanded = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedButton(
                    modifier = Modifier.padding(vertical = 30.dp),
                    onClick = {
                              val result = when(selectedOptionText) {
                                  "Sumar" -> calc.value1.add(calc.value2)
                                  "Restar" -> calc.value1.minus(calc.value2)
                                  "Multiplicar" -> calc.value1.multiply(calc.value2)
                                  "Dividir" -> calc.value1.divide(calc.value2)
                                  else -> {
                                      BigDecimal.ZERO
                                  }
                              }
                        calc = calc.copy(result = result.setScale(2, RoundingMode.HALF_UP));
                    },
                    shape = MaterialTheme.shapes.small,
                    contentPadding = PaddingValues(13.dp)
                ) {
                    Text(
                        text = "Calcular",
                        fontSize = 20.sp
                    )
                }
            }

            OutlinedCard(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
                border = BorderStroke(1.dp, Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            ) {
                Text(
                    text = "Resultado: ${ calc.result }",
                    modifier = Modifier
                        .padding(15.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }
        }
    }
}