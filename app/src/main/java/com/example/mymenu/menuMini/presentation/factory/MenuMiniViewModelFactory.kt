package com.example.mymenu.menuMini.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymenu.basket.domain.AddDishToBasketUseCase
import com.example.mymenu.menuMini.domain.GetDishMiniUseCase
import com.example.mymenu.menuMini.presentation.viewModel.MenuMiniViewModel

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
