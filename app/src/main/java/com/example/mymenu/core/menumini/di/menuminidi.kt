package com.example.mymenu.core.menumini.di


import com.example.mymenu.core.basket.data.BasketRepositoryImpl
import com.example.mymenu.core.basket.domain.AddDishToBasketUseCase
import com.example.mymenu.core.basket.domain.BasketRepository
import com.example.mymenu.core.data.ApiService.DishDataSource
import com.example.mymenu.core.data.DAO.BasketDao
import com.example.mymenu.core.data.DB.AppDataBase
import com.example.mymenu.core.menumini.data.MenuMiniRepositoryImpl
import com.example.mymenu.core.menumini.domain.GetDishMiniUseCase
import com.example.mymenu.core.menumini.domain.MenuMiniRepository
import com.example.mymenu.core.menumini.presentation.viewModel.MenuMiniViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val menuminidi = module {
    single {
        DishDataSource()
    }
    //чтобы Koin мог найти экземпляр BasketDao
    single {
       AppDataBase.getDatabase(androidContext()).basketDao()
    }
    single< MenuMiniRepository> {
       MenuMiniRepositoryImpl (dishDataSource = get())
    }
    single< BasketRepository> {
        BasketRepositoryImpl (dishDataSource = get(), basketDao = get())
    }
    factory {
        GetDishMiniUseCase(repositoryMini = get())
    }
    factory {
        AddDishToBasketUseCase(repository = get())
    }
    viewModel{
        MenuMiniViewModel(
            getDishMiniUseCase = get(),
            addDishToBasketUseCase = get()
        )
    }
}
