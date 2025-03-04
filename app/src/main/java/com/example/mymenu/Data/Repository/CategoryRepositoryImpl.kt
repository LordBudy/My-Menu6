package com.example.mymenu.Data.Repository

import com.example.mymenu.Domain.Category1.CategoryListRepository
import com.example.mymenu.Domain.Models.CategoryItem
import com.example.mymenu.Domain.Models.DishItem

class CategoryRepositoryImpl:CategoryListRepository {
    override suspend fun getCategoryList(): List<CategoryItem> {
        TODO("Not yet implemented")
    }

    override suspend fun OpenGetMenuItems(): List<DishItem> {
        TODO("Not yet implemented")
    }
}