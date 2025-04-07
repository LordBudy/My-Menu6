package com.example.mymenu.Data.ApiService

import com.example.mymenu.Data.ModelsEntitys.Category

// Объявляем класс CatDataSource (источник данных для категорий)
// Этот класс отвечает за предоставление данных о категориях блюд
class CatDataSource {

    // fun getLocalCategory(): List<CategoryEntity>
    // Метод для получения списка категорий.
    // Возвращает List<CategoryEntity> (список объектов CategoryEntity).
    fun getLocalCategory(): List<Category> {
        // return listOf(...)
        // Возвращаем список категорий, созданный с помощью функции listOf().
        return listOf(
            // CategoryEntity(1, "URL_1", "Выпечка")
            // Создаем объект CategoryEntity с ID=1, URL изображения и названием "Выпечка".
            Category(
                1,
                "https://img.freepik.com/premium-photo/composition-with-buckwheat-flour-fresh-bread-white-background_392895-171296.jpg?w=900",
                "Выпечка"
            ),
            // CategoryEntity(2, "URL_2", "Фасфуд")
            // Создаем объект CategoryEntity с ID=2, URL изображения и названием "Фасфуд".
            Category(
                2,
                "https://img.freepik.com/premium-photo/junk-food-fast-food-burger-french-fries_744040-1059.jpg?w=1380",
                "Фасфуд"
            ),
            // CategoryEntity(3, "URL_3", "Азиатская кухня")
            // Создаем объект CategoryEntity с ID=3, URL изображения и названием "Азиатская кухня".
            Category(
                3,
                "https://img.freepik.com/premium-photo/noodles-with-seafood_144962-852.jpg?w=900",
                "Азиатская кухня"
            ),
            // CategoryEntity(4, "URL_4", "Супы")
            // Создаем объект CategoryEntity с ID=4, URL изображения и названием "Супы".
            Category(
                4,
                "https://img.freepik.com/premium-photo/homemade-lentil-stew-with-vegetables-chorizo_58460-10975.jpg?w=900",
                "Супы"
            )
        )
    }
}