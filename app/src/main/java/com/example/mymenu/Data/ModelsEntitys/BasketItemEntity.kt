package com.example.mymenu.Data.ModelsEntitys

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "basket_item")
data class BasketItemEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int, // ID блюда (ссылка на DishEntity)

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "price")
    val price: Double,

    @ColumnInfo(name = "weight")
    val weight: Double,

    @ColumnInfo(name = "count")
    val count: Int // Количество
)