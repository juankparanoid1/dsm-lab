package com.example.desafioapp1.Screens

import androidx.compose.foundation.BorderStroke

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.desafioapp1.components.InputField
import com.example.desafioapp1.models.EmployeeSalary
import com.example.desafioapp1.models.StudentAverageGrade
import java.math.BigDecimal
import java.math.RoundingMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Ejercicio2(navigateToHome: (() -> Unit)? = null) {

    var employee by rememberSaveable {
        mutableStateOf(EmployeeSalary());
    }

    var cleanAllValues by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Ejercicio 2",
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
            val typeTex = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text
            );
            val typeNumber = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Decimal
            );
            InputField(
                label = "Nombre",
                keyboardType = typeTex,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                cleanAllValues = cleanAllValues,
                onValueChanged = { value ->
                    employee = employee.copy(name = value);
                }
            )
            InputField(
                label = "Salario base",
                keyboardType = typeNumber,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                cleanAllValues = cleanAllValues,
                onValueChanged = { value ->
                    if (value.toBigDecimal() > BigDecimal.ZERO) {
                        employee = employee.copy(salary = value.toBigDecimal());
                    }
                }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedButton(
                    modifier = Modifier.padding(vertical = 10.dp),
                    onClick = {
                        if (employee.salary > BigDecimal.ZERO && employee.name.isNotEmpty()) {
                            val isss = employee.salary.multiply(BigDecimal(0.03))
                            val afp = employee.salary.multiply(BigDecimal(0.04))
                            val renta = employee.salary.multiply(BigDecimal(0.05))
                            val deductions = isss.add(afp).add(renta)
                            val finalSalary = employee.salary.minus(deductions)
                                .setScale(2, RoundingMode.HALF_UP)

                            // Update the employee state with the new finalSalary
                            employee = employee.copy(finalSalary = finalSalary)
                        }
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
                    .padding(vertical = 20.dp)
            ) {
                Text(
                    text = "Salario neto: ${employee.finalSalary}",
                    modifier = Modifier
                        .padding(15.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }
        }
    }
}