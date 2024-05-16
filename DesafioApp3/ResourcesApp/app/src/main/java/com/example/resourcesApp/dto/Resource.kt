package com.example.resourcesApp.dto

import java.util.Date

class Resource (
    val title: String,
    val date: Date,
    val description: String,
    val category: String,
    val url: String,
    val tags: List<String>,
    val image: String,
    val titleLoweCase: String,
)
{
    constructor() : this("",Date(),"","","",emptyList<String>(), "", "")
}