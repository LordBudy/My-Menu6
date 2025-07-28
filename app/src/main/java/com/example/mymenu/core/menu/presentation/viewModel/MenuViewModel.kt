package com.example.mymenu.core.menu.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymenu.core.menu.domain.GetDishsMenuUseCase
import com.example.mymenu.core.models.DishItem
import kotlinx.coroutines.launch

class MenuViewModel(
    private val getDishsMenuUseCase: GetDishsMenuUseCase,
    private val categoryId: Int
) : ViewModel() {
    private val _dishs = MutableLiveData<List<DishItem>>()
    val dishs: LiveData<List<DishItem>> = _dishs

    init {
        loadDishs()
    }

    fun loadDishs() {
        //  viewModelScope - корутин , связанный с жизненным циклом ViewModel
        viewModelScope.launch {
            try {
                //  Вызываем UseCase для получения списка блюд по ID категории
                val dishList =
                    getDishsMenuUseCase.execute(categoryId)   // Передаем categoryId в use case
                //  Присваиваем полученный список блюд переменной _dishs
                _dishs.value = dishList
            } catch (e: Exception) {
                println("Error loading dishs: ${e.message}")
                _dishs.value = emptyList()
            }
        }
    }

    fun fastLoadDishs(categoryId: Int, filterType: String?) {
        viewModelScope.launch {
            val allDishes = getDishsMenuUseCase.execute(categoryId)
            val filteredDishes = if (filterType != null) {
                //  Пример фильтрации (замените на вашу логику)
                allDishes.filter { dish ->
                    when (filterType) {
                        "meat" -> dish.description?.contains(
                            "мясо",
                            ignoreCase = true
                        ) == true //  или  dish.ingredients.contains("мясо")
                        "rice" -> dish.name?.contains(
                            "рис",
                            ignoreCase = true
                        ) == true //  или  dish.ingredients.contains("рис")
                        "fish" -> dish.name?.contains(
                            "рыба",
                            ignoreCase = true
                        ) == true //  или dish.ingredients.contains("рыба")
                        "salad" -> dish.name?.contains(
                            "салат",
                            ignoreCase = true
                        ) == true //  или dish.ingredients.contains("салат")
                        else -> false
                    }
                }
            } else {
                allDishes //  Показать все блюда
            }
            _dishs.value = filteredDishes
        }
    }
}