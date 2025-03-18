package com.example.mymenu.Domain.Category1

import com.example.mymenu.Domain.Models.CategoryItem

//Интерфейс для UseCase (для соблюдения принципа Open/Closed)
interface CategoryRepository {

    //Выводит список квтегорий
    suspend fun getDishsByCategoryId(): List<CategoryItem>

}