package com.example.mymenu.core.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DishItem(

    val id: Int,
    val url: String,
    val name: String,
    val price: Double,
    val weight: Double,
    val description: String,
    val categoryId: Int? = null,
    var count: Int
) : Parcelable