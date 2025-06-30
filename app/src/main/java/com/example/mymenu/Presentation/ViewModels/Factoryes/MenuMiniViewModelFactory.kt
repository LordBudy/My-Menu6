package com.example.mymenu.Presentation.ViewModels.Factoryes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymenu.Domain.Basket.AddDishToBasketUseCase
import com.example.mymenu.Domain.MenuMini.GetDishMiniUseCase
import com.example.mymenu.Presentation.ViewModels.MenuMiniViewModel

class MenuMiniViewModelFactory (
    private val getDishMiniUseCase: GetDishMiniUseCase,
    private val addDishToBasketUseCase: AddDishToBasketUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MenuMiniViewModel::class.java)) {
                return MenuMiniViewModel(addDishToBasketUseCase, getDishMiniUseCase) as T
            }
            throw IllegalArgumentException("Неизвестный класс ViewModel")
        }
    }
