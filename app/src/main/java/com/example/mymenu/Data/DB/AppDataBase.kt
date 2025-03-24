package com.example.mymenu.Data.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mymenu.Data.DAO.BasketDao
import com.example.mymenu.Data.ModelsEntitys.BasketItemEntity
import com.example.mymenu.Data.ModelsEntitys.DishEntity

@Database(entities = [DishEntity::class, BasketItemEntity::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    // DAO для работы с блюдами в корзине
    abstract fun basketDao(): BasketDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}