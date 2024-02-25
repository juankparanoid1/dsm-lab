package com.example.desafioapp1.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class StudentAverageGrade(
    val name: String = "",
    var grades: List<BigDecimal> = emptyList(),
    var average: BigDecimal? = BigDecimal.ZERO,
    var isApproved: String? = "Reprobado",
): Parcelable