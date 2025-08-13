package com.example.mymenu.core.data.modelsEntitys

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "cach_cat") // название таблици
data class CategoryCachEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "url")
    val url_cat: String,
    @ColumnInfo(name = "name")
    val name_cat: String
)
