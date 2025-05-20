package com.example.mymenu.Presentation.ViewModels.Factoryes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymenu.Domain.Menu.GetDishsMenuUseCase
import com.example.mymenu.Presentation.ViewModels.MenuViewModel

class MenuViewModelFactory (
    private val getDishsMenuUseCase: GetDishsMenuUseCase,
    private val categoryId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MenuViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MenuViewModel(getDishsMenuUseCase, categoryId) as T
        }
        throw IllegalArgumentException("Неизвестный класс ViewModel")
    }
}