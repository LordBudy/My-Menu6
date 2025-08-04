package com.example.mymenu.core.menu.di

import com.example.mymenu.core.basket.data.BasketRepositoryImpl
import com.example.mymenu.core.basket.domain.AddDishToBasketUseCase
import com.example.mymenu.core.basket.domain.BasketRepository
import com.example.mymenu.core.data.ApiService.CatDataSource
import com.example.mymenu.core.data.ApiService.DishDataSource
import com.example.mymenu.core.data.DB.AppDataBase
import com.example.mymenu.core.menu.data.CategoryRepositoryImpl
import com.example.mymenu.core.menu.data.DishRepositoryImpl
import com.example.mymenu.core.menu.data.MenuMiniRepositoryImpl
import com.example.mymenu.core.menu.domain.CategoryRepository
import com.example.mymenu.core.menu.domain.DishRepository
import com.example.mymenu.core.menu.domain.GetCategoryUseCase
import com.example.mymenu.core.menu.domain.GetDishMiniUseCase
import com.example.mymenu.core.menu.domain.GetDishsMenuUseCase
import com.example.mymenu.core.menu.domain.MenuMiniRepository
import com.example.mymenu.core.menu.presentation.viewModel.CategoryViewModel
import com.example.mymenu.core.menu.presentation.viewModel.MenuMiniViewModel
import com.example.mymenu.core.menu.presentation.viewModel.MenuViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val menudi = module {
    //Menu di
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
    //----------------------------------------------------------------------------------------------
    //Catrgory di
    single {
        CatDataSource()
    }
    single<CategoryRepository> {
        CategoryRepositoryImpl(catDataSource = get())
    }
    factory {
        GetCategoryUseCase(categoryRepository = get())
    }
    viewModel {
        CategoryViewModel(
            getCategoryUseCase = get()
        )
    }
    //----------------------------------------------------------------------------------------------
    //MenuMini di
//    single {
//        AppDataBase.getDatabase(androidContext()).basketDao()
//    }
    single<MenuMiniRepository> {
        MenuMiniRepositoryImpl (dishDataSource = get())
    }
//    single<BasketRepository> {
//        BasketRepositoryImpl(dishDataSource = get(), basketDao = get())
//    }
    factory {
        GetDishMiniUseCase(repositoryMini = get())
    }
//    factory {
//        AddDishToBasketUseCase(basketRepository = get())
//    }
    viewModel{
        MenuMiniViewModel(
            getDishMiniUseCase = get(),
            addDishToBasketUseCase = get()
        )
    }
    //----------------------------------------------------------------------------------------------
//Search di





}