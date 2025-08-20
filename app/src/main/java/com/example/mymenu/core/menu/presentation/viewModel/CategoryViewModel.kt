package com.example.mymenu.core.menu.presentation.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mymenu.core.data.dao.CategoryCachDao
import com.example.mymenu.core.menu.domain.GetCategoryUseCase
import com.example.mymenu.core.models.CategoryItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class CategoryViewModel(
    // Внедряем UseCase через конструктор
    // getCategoryUseCase - для получения списка категорий
    private val getCategoryUseCase: GetCategoryUseCase,
    private val categoryCachDao: CategoryCachDao,
    application: Application // Добавляем для доступа к кешу
) : AndroidViewModel(application) {

    private val _categories = MutableLiveData<List<CategoryItem>?>()
    val categories: LiveData<List<CategoryItem>?> = _categories

    init {
        loadCategories()
    }
    suspend fun checkItem(itemId: Int): Boolean {
        val item = categoryCachDao.getItemById(itemId) // Используем DAO для получения элемента
        return item != null // true, если данные существуют, false - если отсутствуют
    }
    private fun loadCategories() {
        viewModelScope.launch {
            try {
                val categoryItems = getCategoryUseCase()
                categoryItems?.forEach { category ->
                    if (checkItem(category.id)) { // Проверяем, существует ли элемент
                        category.imagePath = getCachedImage(category.url) // Получаем путь к кешированному изображению
                    }
                }
                _categories.value = categoryItems
            } catch (e: Exception) {
                println("Error loading categories: ${e.message}")
                _categories.value = emptyList()
            }
        }
    }

    private suspend fun getCachedImage(url: String): String? {
        val fileName = url.substring(url.lastIndexOf('/') + 1)
        val cacheFile = File(getApplication<Application>()
            .cacheDir, fileName)

        return if (cacheFile.exists()) {
            // Если файл существует, возвращаем путь к файлу
            cacheFile.absolutePath
        } else {
            // Если файл не существует, загружаем его
            downloadImage(url, cacheFile)
            cacheFile.absolutePath
        }
    }

    private suspend fun downloadImage(url: String, cacheFile: File) {
        withContext(Dispatchers.IO) {
            try {
                val connection = URL(url).openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val input: InputStream = connection.inputStream
                val output = FileOutputStream(cacheFile)

                // Копируем данные из InputStream в файл
                input.copyTo(output)

                output.close()
                input.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}