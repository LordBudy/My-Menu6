package com.example.mymenu.Data.ApiService

import com.example.mymenu.Data.ModelsEntitys.CategoryEntity

class CatDataSource {

    fun getLocalCategory(): List<CategoryEntity> {
        return listOf(
            CategoryEntity(
                1,
                "https://img.freepik.com/premium-photo/composition-with-buckwheat-flour-fresh-bread-white-background_392895-171296.jpg?w=900",
                "Выпечка"
            ),
            CategoryEntity(
                2,
                "https://img.freepik.com/premium-photo/junk-food-fast-food-burger-french-fries_744040-1059.jpg?w=1380",
                "Фасфуд"
            ),
            CategoryEntity(
                3,
                "https://img.freepik.com/premium-photo/noodles-with-seafood_144962-852.jpg?w=900",
                "Азиатская кухня"
            ),
            CategoryEntity(
                4,
                "https://img.freepik.com/premium-photo/homemade-lentil-stew-with-vegetables-chorizo_58460-10975.jpg?w=900",
                "Супы"
            )
        )
    }
}