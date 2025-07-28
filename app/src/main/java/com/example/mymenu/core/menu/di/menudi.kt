package com.example.mymenu.core.menu.di

import com.example.mymenu.core.data.ApiService.DishDataSource
import com.example.mymenu.core.menu.data.DishRepositoryImpl
import com.example.mymenu.core.menu.domain.DishRepository
import com.example.mymenu.core.menu.domain.GetDishsMenuUseCase
import com.example.mymenu.core.menu.presentation.viewModel.MenuViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val menudi = module {
    single {
        DishDataSource()
    }
    single<DishRepository> {
        DishRepositoryImpl(dishDataSource = get())
    }
    factory {
        GetDishsMenuUseCase(dishRepository = get())
    }
    viewModel { parameters ->
        val categoryId: Int = parameters.get() //Получаем categoryId из параметров

        MenuViewModel(  //Создаем MenuViewModel с categoryId
            getDishsMenuUseCase = get(),
            categoryId = categoryId    // Передаем categoryId
        )
    }
}