package com.example.mymenu.menu.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymenu.menu.domain.GetDishsMenuUseCase
import com.example.mymenu.menu.presentation.viewModel.MenuViewModel

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