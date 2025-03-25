package com.example.mymenu.Presentation.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymenu.Domain.Basket.GetAllBasketUseCase
import com.example.mymenu.Domain.Models.DishItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BasketViewModel(private val getAllBasketUseCase: GetAllBasketUseCase):ViewModel() {
    private val _basketItems = MutableLiveData<List<DishItem>>()
    val basketItems: LiveData<List<DishItem>> = _basketItems

    init {
        loadBasketItems()
    }

    private fun loadBasketItems() {
        viewModelScope.launch {
            getAllBasketUseCase().collect { items ->
                _basketItems.value = items
            }
        }
    }
}