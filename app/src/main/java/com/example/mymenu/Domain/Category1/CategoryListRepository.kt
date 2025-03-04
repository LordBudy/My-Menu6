package com.example.mymenu.Domain.Category1

import com.example.mymenu.Domain.Models.CategoryItem
import com.example.mymenu.Domain.Models.DishItem

//Интерфейс для UseCase (для соблюдения принципа Open/Closed)
interface CategoryListRepository {

    //Выводит список квтегорий
    suspend fun getCategoryList(): List<CategoryItem>
    //Выводит список меню из выбранной квтегории
    suspend fun OpenGetMenuItems(): List<DishItem>
}