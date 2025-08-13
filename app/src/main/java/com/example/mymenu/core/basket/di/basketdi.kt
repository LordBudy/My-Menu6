package com.example.mymenu.core.basket.di

import com.example.mymenu.core.basket.data.BasketRepositoryImpl
import com.example.mymenu.core.basket.domain.BasketRepository
import com.example.mymenu.core.basket.domain.DeleteDishBasketUseCase
import com.example.mymenu.core.basket.domain.GetAllBasketUseCase
import com.example.mymenu.core.basket.domain.GetItemByDishIdUseCase
import com.example.mymenu.core.basket.domain.MinusDishUseCase
import com.example.mymenu.core.basket.domain.PlusDishUseCase
import com.example.mymenu.core.basket.domain.UpdateBasketUseCase
import com.example.mymenu.core.basket.presentation.viewModel.BasketViewModel
import com.example.mymenu.core.data.apiService.DishDataSource
import com.example.mymenu.core.data.db.AppDataBase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val basketdi = module {
    single<DishDataSource> {
        DishDataSource()
    }
    //чтобы Koin мог найти экземпляр BasketDao
    single {
        AppDataBase.getDatabase(androidContext()).basketDao()
    }
    single<BasketRepository> {
        BasketRepositoryImpl(dishDataSource = get(), basketDao = get())
    }
    factory {
        GetAllBasketUseCase(basketRepository = get())
    }
//    factory {
//        AddDishToBasketUseCase(basketRepository = get())
//    }
    factory {
        DeleteDishBasketUseCase(basketRepository = get())
    }
    factory {
        GetItemByDishIdUseCase(basketRepository = get())
    }
    factory {
        MinusDishUseCase(basketRepository = get())
    }
    factory {
        PlusDishUseCase(basketRepository = get())
    }
    factory {
        UpdateBasketUseCase(basketRepository = get())
    }
    viewModel {
        BasketViewModel(
           // addDishToBasketUseCase = get(),
            getAllBasketUseCase = get(),
            deleteDishBasketUseCase = get(),
            minusDishUseCase = get(),
            plusDishUseCase = get()
        )
    }
}