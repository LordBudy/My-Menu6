package com.example.mymenu.core.data.apiService

import android.content.Context
import com.example.mymenu.core.data.modelsEntitys.CategoryCachEntity
import com.example.mymenu.core.models.CategoryItem

// CatDataSource имитируем источник данных для категорий

class CatDataSource(private val context: Context) {
    // Метод получения списка категорий.
    fun getLocalCategory(): List<CategoryItem> {
        // Возвращаем список категорий, созданный с помощью функции listOf().
        return listOf(
            // Создаем объект CategoryEntity с ID=1, URL изображения и названием "Выпечка".
            CategoryItem(
                1,
                "https://i.pinimg.com/736x/20/2f/5b/202f5ba961353fe53a32712047627792.jpg",
                "Выпечка"
            ),
            CategoryItem(
                2,
                "https://avatars.mds.yandex.net/i?id=66475a7cf4119d283d805507c5a46a68ac0fce06-5113868-images-thumbs&n=13",
                "Фасфуд"
            ),
            CategoryItem(
                3,
                "https://avatars.mds.yandex.net/i?id=3aa46be126dd2f69da65035a6f39f92c_l-4507718-images-thumbs&n=13",
                "Азиатская кухня"
            ),
            CategoryItem(
                4,
                "https://webstockreview.net/images/clam-clipart-clam-chowder-15.png",
                "Супы"
            )
        )
    }
    // Метод для получения категорий из API или другого источника
    suspend fun getCategories(): List<CategoryCachEntity> {
        // Здесь должна быть ваша логика для получения категорий.
        // Например, пока просто возвращаем локальные категории как заглушку.
        return getLocalCategory().map { categoryItem ->
            CategoryCachEntity(categoryItem.id, categoryItem.url, categoryItem.name)
        }
    }
}
