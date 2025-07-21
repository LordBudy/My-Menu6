package com.example.mymenu.coreData.ApiService

import com.example.mymenu.coreData.ModelsEntitys.Category

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
                "https ://i.pinimg.com/736x/f9/2c/c6/f92cc6c8994b89920b42cad1a759a8ef.jpg",
                "Фасфуд"
            ),
            Category(
                3,
                "https://i.pinimg.com/736x/59/c0/b6/59c0b6dfc3438f0503d0fc294a7c5cbc.jpg",
                "Азиатская кухня"
            ),
            Category(
                4,
                "https://img.freepik.com/premium-photo/homemade-lentil-stew-with-vegetables-chorizo_58460-10975.jpg?w=900",
                "Супы"
            )
        )
    }
}