package com.example.mymenu.core.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mymenu.core.data.dao.BasketDao
import com.example.mymenu.core.data.dao.DishCachDao
import com.example.mymenu.core.data.modelsEntitys.BasketDishEntity
import com.example.mymenu.core.data.modelsEntitys.DishCachEntity

@Database(
    entities = [BasketDishEntity::class, DishCachEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun basketDao(): BasketDao
    abstract fun dishCachDao(): DishCachDao

    companion object {
        // @Volatile - гарантирует, что INSTANCE всегда будет виден всем потокам
        @Volatile
        // Приватная переменная для хранения единственного экземпляра базы данных (Singleton)
        private var INSTANCE: AppDataBase? = null

        // Метод для получения экземпляра базы данных (Singleton)
        fun getDatabase(context: Context): AppDataBase {
            // Если INSTANCE не null, возвращаем его.
            // Если INSTANCE null, создаем новый экземпляр базы данных в синхронизированном блоке
            return INSTANCE ?: synchronized(this) {
                // Создаем экземпляр базы данных с помощью Room.databaseBuilder()
                val instance = Room.databaseBuilder(
                    // Контекст приложения
                    context.applicationContext,
                    // Класс базы данных
                    AppDataBase::class.java,
                    // Название файла базы данных
                    "app_database"
                ).fallbackToDestructiveMigration(false).build()// Строим базу данных
                // Сохраняем созданный экземпляр базы данных в INSTANCE
                INSTANCE = instance
                // Возвращаем созданный экземпляр базы данных.
                instance
            }
        }
    }
}