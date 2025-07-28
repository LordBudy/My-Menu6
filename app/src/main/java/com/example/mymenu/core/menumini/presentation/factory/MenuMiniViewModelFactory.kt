package com.example.mymenu.core.menumini.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymenu.core.basket.domain.AddDishToBasketUseCase
import com.example.mymenu.core.menumini.domain.GetDishMiniUseCase
import com.example.mymenu.core.menumini.presentation.viewModel.MenuMiniViewModel

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
