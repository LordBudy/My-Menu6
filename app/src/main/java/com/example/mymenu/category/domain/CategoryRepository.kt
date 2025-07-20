package com.example.mymenu.category.domain

import com.example.mymenu.coreModels.CategoryItem

//Интерфейс для UseCase (для соблюдения принципа Open/Closed)
interface CategoryRepository {

    //Выводит список квтегорий
    fun getCategoryId(): List<CategoryItem>

}