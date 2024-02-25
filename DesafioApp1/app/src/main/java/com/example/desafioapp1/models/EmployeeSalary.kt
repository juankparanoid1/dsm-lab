package com.example.desafioapp1.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class EmployeeSalary(
    val name: String = "",
    val salary: BigDecimal = BigDecimal.ZERO,
    val finalSalary: BigDecimal? = BigDecimal.ZERO,
):Parcelable
