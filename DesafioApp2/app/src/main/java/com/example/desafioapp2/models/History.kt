package com.example.desafioapp2.models

import java.util.Date

data class History(
    val id: String,
    val date: Date,
    val productName: String,
    val price: Double,
    val quantity: Int
){
    constructor() : this("", Date(), "", 0.0, 0)
}