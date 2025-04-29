package com.example.mymenu.Domain.Category1

import com.example.mymenu.Domain.Models.CategoryItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetCategoryUseCase(private val categoryRepository: CategoryRepository) {

    suspend operator fun invoke(): List<CategoryItem> {
        return withContext(Dispatchers.IO) {
            val categories = categoryRepository.getCategoryId()
            categories
        }
    }
}