package com.example.mymenu.Presentation.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymenu.Domain.Menu.GetDishsMenuUseCase
import com.example.mymenu.Domain.Menu.Search.GetSearchDishesUseCase
import com.example.mymenu.Domain.Models.DishItem
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchViewModel(
    private val getSearchDishesUseCase: GetSearchDishesUseCase,
) : ViewModel() {
    private val _dishs = MutableLiveData<List<DishItem>>()
    val dishs: LiveData<List<DishItem>> = _dishs

    fun loadDishs(query: String) { // Added name parameter here, remove from params
        //  viewModelScope - корутин , связанный с жизненным циклом ViewModel
        viewModelScope.launch {
            try {
                //  Вызываем UseCase для получения списка блюд по запросу
                getSearchDishesUseCase.execute(query).collectLatest { dishes ->
                    _dishs.value = dishes // Assign the list of dishes
                }
            } catch (e: Exception) {
                println("Error loading dishs: ${e.message}")
                _dishs.value = emptyList()
            }
        }
    }
}
