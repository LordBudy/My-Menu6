package com.example.mymenu.core.menu.data

import com.example.mymenu.core.menu.domain.CategoryRepository
import com.example.mymenu.core.data.apiService.CatDataSource
import com.example.mymenu.core.data.dao.CategoryCachDao
import com.example.mymenu.core.data.modelsEntitys.CategoryCachEntity
import com.example.mymenu.core.models.CategoryItem

class CategoryRepositoryImpl(
    private val catDataSource: CatDataSource,
    private val categoryCachDao: CategoryCachDao)
    : CategoryRepository {

    override fun getCategoryId(): List<CategoryItem> {
        //гарантирует, что каждый список CategoryEntity в списке будет преобразован
        // в CategoryItem перед тем, как будет возвращен список
        return catDataSource.getLocalCategory()
        }
    suspend fun getItem(itemId: Int): CategoryCachEntity? {
        return categoryCachDao.getItemById(itemId)
    }

    suspend fun insertItem(item: CategoryCachEntity) {
        categoryCachDao.insertItem(item)
    }
    override suspend fun getCategories(): List<CategoryCachEntity> {
        // Проверяем наличие кэшированных данных
        val cachedCategories = categoryCachDao.getAllItems()
        return if (cachedCategories.isNotEmpty()) {
            cachedCategories // Если есть кэшированные данные, возвращаем их
        } else {
            // Если кэш пуст, загружаем данные из API и сохраняем в кэш
            try {
                val categoriesFromApi = catDataSource.getCategories()
                // Сохраняем каждую категорию в кэш
                categoriesFromApi.forEach { category ->
                    categoryCachDao.insertItem(category)
                }
                categoriesFromApi // Возвращаем данные из API
            } catch (e: Exception) {
                // Обработка ошибок, если не удалось загрузить данные из API
                emptyList() // Возвращаем пустой список в случае ошибки
            }
        }
    }
}

