package com.example.mymenu.core.menu.domain

import com.example.mymenu.core.models.CategoryItem

//Интерфейс для UseCase (для соблюдения принципа Open/Closed)
interface CategoryRepository {

    //Выводит список квтегорий
    fun getCategoryId(): List<CategoryItem>

}