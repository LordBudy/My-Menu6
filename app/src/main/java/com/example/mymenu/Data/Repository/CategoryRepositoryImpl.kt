package com.example.mymenu.Data.Repository

import com.example.mymenu.Data.ApiService.CatDataSource
import com.example.mymenu.Data.ModelsEntitys.Category
import com.example.mymenu.Domain.Category1.CategoryRepository
import com.example.mymenu.Domain.Models.CategoryItem


class CategoryRepositoryImpl(private val catDataSource: CatDataSource) : CategoryRepository {

    override suspend fun getDishsByCategoryId(): List<CategoryItem> {
        //гарантирует, что каждый список CategoryEntity в списке будет преобразован
        // в CategoryItem перед тем, как будет возвращен список
        return catDataSource.getLocalCategory().map {  categoryEntity ->
            categoryEntity.toDomainCategory() }
    }


    private fun Category.toDomainCategory(): CategoryItem =
        CategoryItem(
            id = id,
            url_cat = url_cat,
            name_cat = name_cat
        )
}