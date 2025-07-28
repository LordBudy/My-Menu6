package com.example.mymenu.core.data.ApiService

import com.example.mymenu.core.data.ModelsEntitys.Category

// CatDataSource имитируем источник данных для категорий

class CatDataSource {
    // Метод получения списка категорий.
    fun getLocalCategory(): List<Category> {
        // Возвращаем список категорий, созданный с помощью функции listOf().
        return listOf(
            // Создаем объект CategoryEntity с ID=1, URL изображения и названием "Выпечка".
            Category(
                1,
                "https://i.pinimg.com/736x/20/2f/5b/202f5ba961353fe53a32712047627792.jpg",
                "Выпечка"
            ),
            Category(
                2,
                "https://avatars.mds.yandex.net/i?id=66475a7cf4119d283d805507c5a46a68ac0fce06-5113868-images-thumbs&n=13",
                "Фасфуд"
            ),
            Category(
                3,
                "https://get.pxhere.com/photo/dish-meal-food-fish-cuisine-asian-food-sushi-salmon-japanese-food-japanese-cuisine-1235309.jpg",
                "Азиатская кухня"
            ),
            Category(
                4,
                "https://webstockreview.net/images/clam-clipart-clam-chowder-15.png",
                "Супы"
            )
        )
    }
}