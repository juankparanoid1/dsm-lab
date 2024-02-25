package com.example.desafioapp1.Screens

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.desafioapp1.components.InputField
import com.example.desafioapp1.models.StudentAverageGrade
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.coroutines.coroutineContext

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Ejercicio1(navigateToHome: (() -> Unit)? = null) {

    var studentAverageGrade by rememberSaveable {
        mutableStateOf(StudentAverageGrade());
    }

    var cleanAllValues by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Ejercicio 1",
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
                .padding(horizontal = 10.dp)
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
                    studentAverageGrade = studentAverageGrade.copy(name = value);
                }
            )

            Text(
                text = "Notas",
                modifier = Modifier
                    .padding(vertical = 15.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                InputField(
                    label = "Nota 1",
                    keyboardType = typeNumber,
                    modifier = Modifier
                        .width(190.dp)
                        .padding(vertical = 10.dp),
                    cleanAllValues = cleanAllValues,
                    onValueChanged = { value ->
                        if(value.toBigDecimal() > BigDecimal.ZERO && value.toBigDecimal() <= BigDecimal(10)) {
                            val updateGrade = studentAverageGrade.grades.toMutableList();
                            updateGrade.add(value.toBigDecimal());
                            studentAverageGrade = studentAverageGrade.copy(grades = updateGrade);
                        }
                    }
                )
                InputField(
                    label = "Nota 2",
                    keyboardType = typeNumber,
                    modifier = Modifier
                        .width(190.dp)
                        .padding(vertical = 10.dp),
                    cleanAllValues = cleanAllValues,
                    onValueChanged = { value ->
                        if(value.toBigDecimal() > BigDecimal.ZERO && value.toBigDecimal() <= BigDecimal(10)) {
                            val updateGrade = studentAverageGrade.grades.toMutableList();
                            updateGrade.add(value.toBigDecimal());
                            studentAverageGrade = studentAverageGrade.copy(grades = updateGrade);
                        }
                    }
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                InputField(
                    label = "Nota 3",
                    keyboardType = typeNumber,
                    modifier = Modifier
                        .width(190.dp)
                        .padding(vertical = 10.dp),
                    cleanAllValues = cleanAllValues,
                    onValueChanged = { value ->
                        if(value.toBigDecimal() > BigDecimal.ZERO && value.toBigDecimal() <= BigDecimal(10)) {
                            val updateGrade = studentAverageGrade.grades.toMutableList();
                            updateGrade.add(value.toBigDecimal());
                            studentAverageGrade = studentAverageGrade.copy(grades = updateGrade);
                        }
                    }
                )
                InputField(
                    label = "Nota 4",
                    keyboardType = typeNumber,
                    modifier = Modifier
                        .width(190.dp)
                        .padding(vertical = 10.dp),
                    cleanAllValues = cleanAllValues,
                    onValueChanged = { value ->
                        if(value.toBigDecimal() > BigDecimal.ZERO && value.toBigDecimal() <= BigDecimal(10)) {
                            val updateGrade = studentAverageGrade.grades.toMutableList();
                            updateGrade.add(value.toBigDecimal());
                            studentAverageGrade = studentAverageGrade.copy(grades = updateGrade);
                        }
                    }
                )
            }

            Row {
                InputField(
                    label = "Nota 5",
                    keyboardType = typeNumber,
                    modifier = Modifier
                        .width(190.dp)
                        .padding(vertical = 10.dp),
                    cleanAllValues = cleanAllValues,
                    onValueChanged = { value ->
                        if(value.toBigDecimal() > BigDecimal.ZERO && value.toBigDecimal() <= BigDecimal(10)) {
                            val updateGrade = studentAverageGrade.grades.toMutableList();
                            updateGrade.add(value.toBigDecimal());
                            studentAverageGrade = studentAverageGrade.copy(grades = updateGrade);
                        }
                    }
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedButton(
                    modifier = Modifier.padding(vertical = 10.dp)
                        .focusRequester(FocusRequester()),
                    onClick = {
                        cleanAllValues = true;
                        studentAverageGrade = studentAverageGrade.copy("", emptyList(), BigDecimal.ZERO, "Reprobado");
                    },
                    shape = MaterialTheme.shapes.small,
                    contentPadding = PaddingValues(13.dp)
                ) {
                    Text(
                        text = "Limpiar",
                        fontSize = 20.sp
                    )
                }
                OutlinedButton(
                    modifier = Modifier.padding(vertical = 10.dp),
                    onClick = {
                        cleanAllValues = false;
                        if(studentAverageGrade.grades.size == 5 && studentAverageGrade.name.isNotEmpty()) {
                            val averageGrade = studentAverageGrade.grades.reduce { acc, value -> acc.add(value) }
                                .divide(BigDecimal(studentAverageGrade.grades.size));
                            studentAverageGrade =  studentAverageGrade.copy(average = averageGrade.setScale(2, RoundingMode.HALF_UP));
                            val isApproved = when (studentAverageGrade.average?.compareTo(BigDecimal(6)) ?: -1) {
                                1, 0 -> "Aprobado"
                                else -> "Reprobado"
                            };
                            studentAverageGrade = studentAverageGrade.copy(isApproved = isApproved);
                            studentAverageGrade = studentAverageGrade.copy("", emptyList());
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
                    text = "Nota final: ${ studentAverageGrade.average }",
                    modifier = Modifier
                        .padding(15.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
                Text(
                    text = "Estudiante aprueba: ${ studentAverageGrade.isApproved }",
                    modifier = Modifier
                        .padding(15.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }

        }
    }
}