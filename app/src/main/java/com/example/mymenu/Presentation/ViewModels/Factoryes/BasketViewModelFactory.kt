package com.example.mymenu.Presentation.ViewModels.Factoryes

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymenu.Data.ApiService.DishDataSource
import com.example.mymenu.Data.DAO.BasketDao
import com.example.mymenu.Data.DB.AppDataBase
import com.example.mymenu.Data.Repository.BasketRepositoryImpl
import com.example.mymenu.Domain.Basket.GetAllBasketUseCase
import com.example.mymenu.Presentation.ViewModels.BasketViewModel
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