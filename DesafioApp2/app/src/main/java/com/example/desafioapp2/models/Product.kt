package com.example.desafioapp2.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: String,
    val name: String,
    val imageUrl: String? = null,
    val price: Double? = null,
    val description: String? = null
): Parcelable {
    @Suppress("ParcelCreator")
    constructor() : this("", "", "", 0.0, "")
}