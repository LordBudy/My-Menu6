package com.example.mymenu.Domain.Category1

import com.example.mymenu.Domain.Models.CategoryItem
import com.example.mymenu.Domain.Models.DishItem

//Интерфейс для UseCase (для соблюдения принципа Open/Closed)
interface CategoryRepository {

    //Выводит список квтегорий
    suspend fun getCategoryList(): List<CategoryItem>

}