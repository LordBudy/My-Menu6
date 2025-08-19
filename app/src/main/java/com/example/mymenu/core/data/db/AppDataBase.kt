package com.example.mymenu.core.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mymenu.core.data.dao.BasketDao
import com.example.mymenu.core.data.dao.CategoryCachDao
import com.example.mymenu.core.data.dao.DishCachDao
import com.example.mymenu.core.data.modelsEntitys.BasketDishEntity
import com.example.mymenu.core.data.modelsEntitys.CategoryCachEntity
import com.example.mymenu.core.data.modelsEntitys.DishCachEntity

@Database(
    entities = [BasketDishEntity::class, DishCachEntity::class, CategoryCachEntity::class],
    version = 1,//при каждом изменении меняем версию
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun basketDao(): BasketDao
    abstract fun dishCachDao(): DishCachDao
    abstract fun categoryCachDao(): CategoryCachDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null
//добавляем новую колонку в таблицу
        //        val MIGRATION_1_2 = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("ALTER TABLE DishCachEntity ADD COLUMN название новой колонки INTEGER DEFAULT 0 NOT NULL")
//            }
//        }
                              // или добавляем новую таблицу
//        val MIGRATION_2_3 = object : Migration(2, 3) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                // Создайте новую таблицу
//                database.execSQL(
//                    """
//                    CREATE TABLE cachsCategory (
//                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
//                        name TEXT NOT NULL
//                    )
//                """.trimIndent()
//                )
//            }
//        }
        fun getDatabase(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "app_database"
                )//добавляем новую колонку в таблицу
                    //.addMigrations(MIGRATION_1_2) // Используем миграцию из того же класса
                    //или добавляем новую таблицу
                   // .addMigrations(MIGRATION_2_3)
                    .fallbackToDestructiveMigration(false)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}