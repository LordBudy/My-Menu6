package com.example.mymenu.Domain.Category1

import com.example.mymenu.Domain.Models.CategoryItem

class GetCategoryUseCase(private val categoryRepository: CategoryRepository ) {

    suspend fun getCategoryList(): List<CategoryItem>{
        return categoryRepository.getCategoryList()
    }
}