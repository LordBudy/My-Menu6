package com.example.mymenu.Presentation.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymenu.Domain.Menu.GetDishsMenuUseCase
import com.example.mymenu.Domain.Models.DishItem
import kotlinx.coroutines.launch

// MenuViewModel - ViewModel для фрагмента Menu
class MenuViewModel(
    private val getDishsMenuUseCase: GetDishsMenuUseCase,
    private val categoryId: Int
) : ViewModel() {
    private val _dishs = MutableLiveData<List<DishItem>>()
    val dishs: LiveData<List<DishItem>> = _dishs
    init {
        loadDishs()
    }
    private fun loadDishs() {
        //  viewModelScope - корутин скоуп, связанный с жизненным циклом ViewModel
        viewModelScope.launch {
            try {
                //  Вызываем UseCase для получения списка блюд по ID категории
                val dishList = getDishsMenuUseCase.execute(categoryId)   // Передаем categoryId в use case
                //  Присваиваем полученный список блюд переменной _dishs
                _dishs.value = dishList
            } catch (e: Exception) {
                println("Error loading dishs: ${e.message}")
                _dishs.value = emptyList()
            }
        }
    }
}