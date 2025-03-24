package com.example.mymenu.Data.ModelsEntitys

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dish") //  таблица называется "dish"
data class DishEntity(

    @PrimaryKey
    @ColumnInfo(name = "id_dish")
    val id: Int,

    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "price")
    val price: Double,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "weight")
    val weight: Double,

    @ColumnInfo(name = "categoryId")
    val categoryId: Int? = null,

    @ColumnInfo(name = "count")
    val count: Int

)
