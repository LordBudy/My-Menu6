package com.example.mymenu.core.menu.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymenu.core.menu.domain.GetDishsMenuUseCase
import com.example.mymenu.core.menu.domain.GetSearchDishsUseCase
import com.example.mymenu.core.models.DishItem
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MenuViewModel(
    private val getSearchDishsUseCase: GetSearchDishsUseCase,
    private val getDishsMenuUseCase: GetDishsMenuUseCase,
    private var categoryId: Int = -1
) : ViewModel() {
    private val _dishs = MutableLiveData<List<DishItem>>()
    val dishs: LiveData<List<DishItem>> = _dishs

    private val _searchQuery = MutableLiveData<String>()

    init {
        Log.d("MenuViewModel", "Создан MenuViewModel с categoryId: $categoryId")
        loadDishs()
    }

    fun setCategoryId(newCategoryId: Int) {
        if (categoryId != newCategoryId) {
            categoryId = newCategoryId
            loadDishs()
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
        searchLoadDishs(query)
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
                Log.e("MenuViewModel", "Ошибка загрузки: ${e.message}")
                _dishs.value = emptyList()
            }
        }
    }
    fun searchLoadDishs(query: String) {
        viewModelScope.launch {
            try {
                //  Вызываем UseCase для получения списка блюд по запросу
                getSearchDishsUseCase.execute(query).collectLatest { dishs ->
                    _dishs.value = dishs
                }
            } catch (e: Exception) {
                println("Ошибка загрузки блюд: ${e.message}")
                _dishs.value = emptyList()
            }
        }
    }

    fun fastLoadDishs(categoryId: Int, filterType: String?) {
        viewModelScope.launch {
            val allDishes = getDishsMenuUseCase.execute(categoryId)
            val filteredDishes = if (filterType != null) {
                allDishes.filter { dish ->
                    when (filterType) {
                        "meat" -> dish.description.contains(
                            "мясо",
                            ignoreCase = true
                        ) == true
                        "rice" -> dish.name.contains(
                            "рис",
                            ignoreCase = true
                        ) == true
                        "fish" -> dish.name.contains(
                            "рыба",
                            ignoreCase = true
                        ) == true
                        "salad" -> dish.name.contains(
                            "салат",
                            ignoreCase = true
                        ) == true
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