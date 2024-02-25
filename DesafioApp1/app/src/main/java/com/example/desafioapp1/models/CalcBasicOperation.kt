package com.example.desafioapp1.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class CalcBasicOperation(
    val value1: BigDecimal = BigDecimal.ZERO,
    val value2: BigDecimal= BigDecimal.ZERO,
    val operation: String = "",
    val result: BigDecimal= BigDecimal.ZERO,
): Parcelable
