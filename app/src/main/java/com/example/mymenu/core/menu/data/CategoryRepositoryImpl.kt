package com.example.mymenu.core.menu.data

import com.example.mymenu.core.menu.domain.CategoryRepository
import com.example.mymenu.core.data.ApiService.CatDataSource
import com.example.mymenu.core.data.ModelsEntitys.Category
import com.example.mymenu.core.models.CategoryItem

class CategoryRepositoryImpl(private val catDataSource: CatDataSource) : CategoryRepository {

    override fun getCategoryId(): List<CategoryItem> {
        //гарантирует, что каждый список CategoryEntity в списке будет преобразован
        // в CategoryItem перед тем, как будет возвращен список
        return catDataSource.getLocalCategory().map {  category ->
            category.toDomainCategory()
        }
    }


    private fun Category.toDomainCategory(): CategoryItem =
        CategoryItem(
            id = id,
            url_cat = url_cat,
            name_cat = name_cat
        )
}