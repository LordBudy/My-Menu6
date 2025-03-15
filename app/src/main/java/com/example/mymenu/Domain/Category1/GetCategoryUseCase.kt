package com.example.mymenu.Domain.Category1

import com.example.mymenu.Domain.Models.CategoryItem

class GetCategoryUseCase(private val categoryRepository: CategoryRepository ) {

    suspend operator fun invoke(): List<CategoryItem>{
        return categoryRepository.getDishesByCategoryId()
    }
}