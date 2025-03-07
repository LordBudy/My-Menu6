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
    private val getCategoryUseCase: GetCategoryUseCase // Внедряем UseCase через конструктор
) : ViewModel() {

    private val _categories = MutableLiveData<List<CategoryItem>?>()
    val categories: LiveData<List<CategoryItem>?> = _categories

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        loadCategories()
    }

    private fun loadCategories() {
        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val categoryList = getCategoryUseCase() // Правильно вызываем UseCase
                _categories.value = categoryList
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load categories: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}