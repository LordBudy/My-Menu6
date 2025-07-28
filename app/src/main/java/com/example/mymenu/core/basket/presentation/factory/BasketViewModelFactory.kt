package com.example.mymenu.core.basket.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymenu.core.basket.data.BasketRepositoryImpl
import com.example.mymenu.core.basket.domain.GetAllBasketUseCase
import com.example.mymenu.core.basket.presentation.viewModel.BasketViewModel
@Suppress("UNCHECKED_CAST")
class BasketViewModelFactory(
    private val getAllBasketUseCase: GetAllBasketUseCase,
    private val basketRepository:BasketRepositoryImpl
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BasketViewModel::class.java)) {
            return BasketViewModel(getAllBasketUseCase, basketRepository) as T
        }
        throw IllegalArgumentException("Неизвестный класс ViewModel")
    }
}