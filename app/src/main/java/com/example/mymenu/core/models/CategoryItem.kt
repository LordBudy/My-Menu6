package com.example.mymenu.core.models

data class CategoryItem(

    val id: Int,
    val url: String,
    val name: String,
    // Путь к кешированному изображению
    var imagePath: String? = null

)