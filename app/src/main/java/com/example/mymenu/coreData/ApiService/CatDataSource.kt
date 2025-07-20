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
                "http://img.freepik.com/premium-photo/composition-with-buckwheat-flour-fresh-bread-white-background_392895-171296.jpg?w=900",
                "Выпечка"
            ),
            Category(
                2,
                "http://img.freepik.com/premium-photo/junk-food-fast-food-burger-french-fries_744040-1059.jpg?w=1380",
                "Фасфуд"
            ),
            Category(
                3,
                "https://img.freepik.com/premium-photo/noodles-with-seafood_144962-852.jpg?w=900",
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