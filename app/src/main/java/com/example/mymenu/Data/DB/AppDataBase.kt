package com.example.mymenu.Data.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mymenu.Data.DAO.BasketDao
import com.example.mymenu.Data.ModelsEntitys.BasketItemEntity
import com.example.mymenu.Data.ModelsEntitys.DishEntity
// Аннотация @Database указывает, что это класс базы данных Room
// entities = [DishEntity::class, BasketItemEntity::class] - список сущностей (таблиц) в базе данных
// version = 1 - версия базы данных (нужна для миграций при изменении схемы)
// exportSchema = false - не экспортировать схему базы данных в файл (рекомендуется для production)
@Database(entities = [DishEntity::class, BasketItemEntity::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    // DAO для работы с блюдами в корзине
    // Абстрактный метод для получения экземпляра BasketDao.
    // Room автоматически генерирует реализацию этого метода.
    abstract fun basketDao(): BasketDao
    // Companion object - это объект, связанный с классом AppDataBase.
    // Он позволяет обращаться к его свойствам и методам напрямую через имя класса (AppDataBase.getDatabase(context))
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
                ).build()// Строим базу данных
                // Сохраняем созданный экземпляр базы данных в INSTANCE
                INSTANCE = instance
                // Возвращаем созданный экземпляр базы данных.
                instance
            }
        }
    }
}