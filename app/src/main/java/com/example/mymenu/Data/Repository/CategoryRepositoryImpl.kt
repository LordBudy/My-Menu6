package com.example.mymenu.Data.Repository

import android.util.Log
import com.example.mymenu.Data.ApiService.CatDataSource
import com.example.mymenu.Data.ModelsEntitys.CategoryEntity
import com.example.mymenu.Domain.Category1.CategoryRepository
import com.example.mymenu.Domain.Models.CategoryItem
import com.example.mymenu.Domain.Models.DishItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoryRepositoryImpl(private val catDataSource: CatDataSource):CategoryRepository {
    private val TAG = "CategoryRepositoryImpl"

    override suspend fun getCategoryList(): List<CategoryItem> = withContext(Dispatchers.IO) {
        try {
            catDataSource.getLocalDishes().map { categoryEntity ->
                categoryEntity.toDomainCategory()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching category list: ${e.message}", e)
            emptyList() // Возвращаем пустой список в случае ошибки
        }
    }

    private fun CategoryEntity.toDomainCategory(): CategoryItem =
        CategoryItem(
            id = id,
            url_cat = url_cat,
            name_cat= name_cat
        )
}