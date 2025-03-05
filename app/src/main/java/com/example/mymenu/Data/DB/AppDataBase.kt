package com.example.mymenu.Data.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mymenu.Data.DAO.BasketDao
import com.example.mymenu.Data.ModelsEntitys.DishEntity

@Database(entities = [DishEntity::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    // DAO для работы с блюдами в корзине
    abstract fun basketDao(): BasketDao
}