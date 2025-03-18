package com.example.mymenu.Presentation.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymenu.Domain.Dish.GetDishsUseCase
import com.example.mymenu.Domain.Models.DishItem
import kotlinx.coroutines.launch

class MenuViewModel(
    private val getDishsUseCase: GetDishsUseCase,
    private val categoryId: Int  // Получаем ID категории из конструктора
) : ViewModel() {

    private val _dishes = MutableLiveData<List<DishItem>>()
    val dishes: LiveData<List<DishItem>> = _dishes

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        loadDishes(categoryId) // Загружаем блюда при создании ViewModel
    }

    fun loadDishes(categoryId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val dishes = getDishsUseCase.execute(categoryId) // Используем execute с categoryId
                _dishes.value = dishes
            } catch (e: Exception) {
                _errorMessage.value = "Ошибка загрузки: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}