package com.example.mymenu.Presentation.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymenu.Domain.Category1.CategoryRepository
import com.example.mymenu.Domain.Category1.GetCategoryUseCase
import com.example.mymenu.Domain.Models.CategoryItem
import kotlinx.coroutines.launch

class HomeViewModel(
    // Внедряем UseCase через конструктор
    // getCategoryUseCase - UseCase для получения списка категорий
    private val getCategoryUseCase: GetCategoryUseCase
) : ViewModel() {

    // _categories - MutableLiveData для хранения списка категорий (внутренняя переменная)
    private val _categories = MutableLiveData<List<CategoryItem>?>()
    // categories - LiveData для предоставления списка категорий UI (публичная переменная)
    // UI может только наблюдать за изменениями, но не изменять список напрямую
    val categories: LiveData<List<CategoryItem>?> = _categories
    // Инициализатор - вызывается при создании экземпляра HomeViewModel

    init {
        // Загружаем категории при создании ViewModel
        loadCategories()
    }
    // loadCategories - метод для загрузки списка категорий
    private fun loadCategories() {
        // viewModelScope - корутин скоуп, связанный с жизненным циклом ViewModel
        viewModelScope.launch {
            //  вызываем UseCase для получения списка категорий
            val categoryList = getCategoryUseCase()
            // Присваиваем полученный список категорий переменной _categories
            _categories.value = categoryList
        }
    }
}