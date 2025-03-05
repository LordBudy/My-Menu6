package com.example.mymenu.Domain.Models

data class DishItem(

    val id: Int,
    val url_dish: String,
    val name_dish: String,
    val price_dish: String,
    val countPrice: Double,
    val weight_dish: String,
    val quantity: Int
)
