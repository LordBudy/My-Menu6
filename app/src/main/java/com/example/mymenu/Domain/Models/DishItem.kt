package com.example.mymenu.Domain.Models

data class DishItem(

    val id: Int,
    val url: String,
    val name: String,
    val price: Double,
    val weight: Double,
    val description: String,
    val categoryId: Int? = null,
    val count: Int
)
