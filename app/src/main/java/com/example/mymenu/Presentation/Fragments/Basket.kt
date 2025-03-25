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


class Basket : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var basketAdapter: BasketAdapter
    private lateinit var basketViewModel: BasketViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_basket, container, false)
        recyclerView = view.findViewById(R.id.rvBasket)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the adapter with an empty list
        basketAdapter = BasketAdapter(emptyList())
        recyclerView.adapter = basketAdapter
        //1. Создание зависимостей
        val dishDataSource = DishDataSource()
        val basketDao = AppDataBase.getDatabase(requireContext()).basketDao() // Исправлено: удалены ()
        val basketRepository = BasketRepositoryImpl(dishDataSource, basketDao)
        val getAllBasketUseCase = GetAllBasketUseCase(basketRepository)
        //2. ViewModelProvider
        basketViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return BasketViewModel(getAllBasketUseCase) as T
            }
        })[BasketViewModel::class.java]

        // 3. Подписка на LiveData и обновление адаптера
        basketViewModel.basketItems.observe(viewLifecycleOwner, Observer { basketItems ->
            basketAdapter.updateData(basketItems)
        })
    }
}