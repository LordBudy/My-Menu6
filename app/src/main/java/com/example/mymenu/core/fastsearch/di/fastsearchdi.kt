package com.example.mymenu.core.fastsearch.di

import com.example.mymenu.core.data.ApiService.DishDataSource
import com.example.mymenu.core.fastsearch.domain.GetSearchDishesUseCase
import com.example.mymenu.core.fastsearch.presentation.viewModel.SearchViewModel
import com.example.mymenu.core.menu.data.DishRepositoryImpl
import com.example.mymenu.core.menu.domain.DishRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val fastsearchdi = module {
    single {
        DishDataSource()
    }
    single<DishRepository> {
        DishRepositoryImpl(dishDataSource = get())
    }
    factory {
        GetSearchDishesUseCase(dishRepository = get())
    }
    viewModel {
            SearchViewModel(get())
    }
}