package com.example.mymenu.Data.ApiService

import com.example.mymenu.Data.ModelsEntitys.CategoryEntity
import com.example.mymenu.Data.ModelsEntitys.DishEntity

class CatDataSource {

    fun getLocalDishes(): List<CategoryEntity> {
        return listOf(
            CategoryEntity(
                1,
                "https://img.freepik.com/free-photo/top-view-tasty-breakfast_23-2147991202.jpg?t=st=1709971481~exp=1709975081~hmac=596414fed39256a867f2bed0f189bc9f1a23b673cf4e5f6cb434b48c2db4732c&w=740",
                "Выпечка"
            ),
            CategoryEntity(
                2,
                "https://img.freepik.com/free-photo/flat-lay-american-food-concept-with-copyspace_23-2148238453.jpg?t=st=1709971555~exp=1709975155~hmac=a50549e9afd0d9cfa1dd53b553a435898bb67b890c0788519756840de63bf844&w=740",
                "Фасфуд"
            ),
            CategoryEntity(
                3,
                "https://img.freepik.com/free-photo/japanese-traditional-salad-with-pieces-medium-rare-grilled-ahi-tuna-sesame-with-fresh-vegetable-salad-plate_2829-18366.jpg?t=st=1709971633~exp=1709975233~hmac=4b136ac43e0e4c5334b1c8bcaaba8dc84f9ee7e3e49fa3aa0dc1a9fdd36d69e5&w=740",
                "Азиатская кухня"
            ),
            CategoryEntity(
                4,
                "https://img.freepik.com/free-photo/top-view-autumn-food-with-copy-space_23-2148666923.jpg?t=st=1709972165~exp=1709975765~hmac=b45fdca07cc5968e1b4ef687b6afca7fee6940b695db72a3fac5d0d2f15d1df3&w=740",
                "Супы"
            )
        )
    }
}