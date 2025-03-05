package com.example.mymenu.Data.ModelsEntitys

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dishs") //  таблица называется "dishs"
data class DishEntity(

    @PrimaryKey
    @ColumnInfo(name = "id_dish")
    var id: Int,

    @ColumnInfo(name = "url")
    var url_dish: String,

    @ColumnInfo(name = "name")
    var name_dish: String,

    @ColumnInfo(name = "price")
    var price_dish: Double,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "weight")
    var weight_dish: Double,

    @ColumnInfo(name = "quantity")
    val quantity: Int?

)
