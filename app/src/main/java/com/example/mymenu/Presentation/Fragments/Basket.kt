package com.example.mymenu.Presentation.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymenu.Data.ApiService.DishDataSource
import com.example.mymenu.Data.DB.AppDataBase
import com.example.mymenu.Data.Repository.BasketRepositoryImpl
import com.example.mymenu.Domain.Basket.GetAllBasketUseCase
import com.example.mymenu.Presentation.Adapters.BasketAdapter
import com.example.mymenu.Presentation.ViewModels.BasketViewModel
import com.example.mymenu.R


@Suppress("UNCHECKED_CAST")
class Basket : Fragment() {
    //переменные, которые будут использоваться
    // RecyclerView для отображения списка элементов корзины
    private lateinit var recyclerView: RecyclerView
    // Адаптер для RecyclerView
    private lateinit var basketAdapter: BasketAdapter
    // ViewModel для управления данными корзины
    private lateinit var basketViewModel: BasketViewModel
    // метод onCreateView вызывается для создания View фрагмента (UI)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Раздуваем (inflate) layout fragment_basket.xml, который содержит UI для этого фрагмента
        val view = inflater.inflate(R.layout.fragment_basket, container, false)
        // Находим RecyclerView по его ID в раздутом View
        recyclerView = view.findViewById(R.id.rvBasket)
        // Устанавливаем LinearLayoutManager для RecyclerView (управляет расположением элементов в списке)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        // Возвращаем созданое View
        return view
    }
    // Этот метод вызывается только после создания View
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Создаем экземпляр BasketAdapter с пустым списком (пока нет данных)
        basketAdapter = BasketAdapter(emptyList())
        // Устанавливаем basketAdapter как адаптер для RecyclerView
        // (RecyclerView теперь знает, как отображать данные)
        recyclerView.adapter = basketAdapter
        // 1. Создание зависимостей (DI-dependency injection - внедрение зависимостей)
        // Создаем экземпляр источника данных (для получения информации о блюдах)
        val dishDataSource = DishDataSource()
        // Получаем доступ к DAO (Data Access Object) для работы с базой данных
        val basketDao = AppDataBase.getDatabase(requireContext()).basketDao()
        // Создаем экземпляр репозитория, который управляет данными корзины
        val basketRepository = BasketRepositoryImpl(dishDataSource, basketDao)
        // Создаем экземпляр UseCase, который получает все элементы корзины
        val getAllBasketUseCase = GetAllBasketUseCase(basketRepository)
        // 2. ViewModelProvider (создание и получение ViewModel)
        basketViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                // Создаем BasketViewModel, передавая ему UseCase в качестве аргумента
                return BasketViewModel(getAllBasketUseCase) as T
            }
        })[BasketViewModel::class.java] // Получаем экземпляр BasketViewModel
        // 3. Подписка на LiveData и обновление адаптера (Observer)
        basketViewModel.basketItems.observe(viewLifecycleOwner, Observer { basketItems ->
            // Этот код будет выполнен каждый раз, когда basketItems (LiveData) в BasketViewModel изменится
            // Обновляем данные в адаптере новым списком элементов корзины
            basketAdapter.updateData(basketItems)
        })
    }
}