package com.example.mymenu.core.category.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymenu.core.category.domain.GetCategoryUseCase

import com.example.mymenu.core.models.CategoryItem
import kotlinx.coroutines.launch

class CategoryViewModel(
    // Внедряем UseCase через конструктор
    // getCategoryUseCase - для получения списка категорий
    private val getCategoryUseCase: GetCategoryUseCase
) : ViewModel() {

    // _categories - MutableLiveData для хранения списка категорий (внутренняя переменная)
    private val _categories = MutableLiveData<List<CategoryItem>?>()

    // categories - LiveData для предоставления списка категорий UI (публичная переменная)
    // UI может только наблюдать за изменениями, но не изменять список напрямую
    val categories: LiveData<List<CategoryItem>?> = _categories
    // Инициализатор - вызывается при создании экземпляра HomeViewMod  el

    init {
        // Загружаем категории при создании ViewModel
        loadCategories()
    }

    // loadCategories - метод для загрузки списка категорий
    private fun loadCategories() {
        // viewModelScope - корутин скоуп, связанный с жизненным циклом ViewModel
        viewModelScope.launch {
            try {


                _categories.value = getCategoryUseCase()

            } catch (e: Exception) {
                println("Error loading cat: ${e.message}")
                _categories.value = emptyList()
            }
        }
    }
}