package com.example.mymenu.core.menu.di

import android.app.Application
import androidx.room.Room
import com.example.mymenu.core.basket.domain.AddDishToBasketUseCase
import com.example.mymenu.core.data.apiService.CatDataSource
import com.example.mymenu.core.data.apiService.DishDataSource
import com.example.mymenu.core.data.db.AppDataBase
import com.example.mymenu.core.menu.data.CategoryRepositoryImpl
import com.example.mymenu.core.menu.data.DishRepositoryImpl
import com.example.mymenu.core.menu.data.MenuMiniRepositoryImpl
import com.example.mymenu.core.menu.domain.CategoryRepository
import com.example.mymenu.core.menu.domain.DishRepository
import com.example.mymenu.core.menu.domain.GetCategoryUseCase
import com.example.mymenu.core.menu.domain.GetDishMiniUseCase
import com.example.mymenu.core.menu.domain.GetDishsMenuUseCase
import com.example.mymenu.core.menu.domain.GetSearchDishsUseCase
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
        DishDataSource(get())
    }
    single<DishRepository> {
        DishRepositoryImpl(dishDataSource = get())
    }

    factory {
        GetDishsMenuUseCase(dishRepository = get())
    }
    factory {
        GetSearchDishsUseCase(dishRepository = get())
    }

    viewModel { parameters ->
        val categoryId: Int = parameters.get() //Получаем categoryId из параметров

        MenuViewModel(  //Создаем MenuViewModel с categoryId
            getDishsMenuUseCase = get(),
            categoryId = categoryId,// Передаем categoryId
            getSearchDishsUseCase = get(),

        )
    }
    //----------------------------------------------------------------------------------------------
    //Catrgory di
    single {
        CatDataSource(get())
    }
    single {
        get<AppDataBase>().categoryCachDao() // Получаем DAO из базы данных
    }
    factory {
        GetCategoryUseCase(categoryRepository = get())
    }
    //передаем categoryCachDao в CategoryRepositoryImpl
    single<CategoryRepository> {
        CategoryRepositoryImpl(
            catDataSource = get(),
            categoryCachDao = get() // Передаем DAO
        )
    }
    viewModel {
        CategoryViewModel(
            // Приводим к Application
            application = androidContext().applicationContext as Application,
            getCategoryUseCase = get(),
            categoryCachDao = get()

        )
    }
    //----------------------------------------------------------------------------------------------
    //MenuMini di

    single<MenuMiniRepository> {
        MenuMiniRepositoryImpl(dishDataSource = get())
    }
    factory {
        GetDishMiniUseCase(repositoryMini = get())
    }
    factory {
        AddDishToBasketUseCase(basketRepository = get())
    }
    viewModel {
        MenuMiniViewModel(
            getDishMiniUseCase = get(),
            addDishToBasketUseCase = get()
        )
    }
    single {
        Room.databaseBuilder(get(),
            AppDataBase::class.java,
            "app_database")
            .build()
    }
}