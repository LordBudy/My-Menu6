package com.example.mymenu.core.menu.domain

import com.example.mymenu.core.data.modelsEntitys.CategoryCachEntity
import com.example.mymenu.core.models.CategoryItem

//Интерфейс для UseCase (для соблюдения принципа Open/Closed)
interface CategoryRepository {

    suspend fun getCategories(): List<CategoryCachEntity>

    //Выводит список категорий
    fun getCategoryId(): List<CategoryItem>

}