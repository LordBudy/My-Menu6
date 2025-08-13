package com.example.mymenu.core.data.modelsEntitys

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "cachs_dish") // название таблици
data class DishCachEntity (
    @PrimaryKey
    @ColumnInfo(name = "id")
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
    var count: Int
)