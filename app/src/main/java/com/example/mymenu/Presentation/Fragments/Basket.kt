package com.example.mymenu.Presentation.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymenu.Data.ApiService.DishDataSource
import com.example.mymenu.Data.DAO.BasketDao
import com.example.mymenu.Data.DB.AppDataBase
import com.example.mymenu.Data.ModelsEntitys.DishEntity
import com.example.mymenu.Data.Repository.BasketRepositoryImpl
import com.example.mymenu.Domain.Basket.GetAllBasketUseCase
import com.example.mymenu.Domain.Models.DishItem
import com.example.mymenu.Presentation.Adapters.BasketAdapter
import com.example.mymenu.Presentation.ViewModels.BasketViewModel
import com.example.mymenu.Presentation.ViewModels.Interfaces.BasketInterface
import com.example.mymenu.R
import kotlinx.coroutines.launch
import com.example.mymenu.Data.ModelsEntitys.toDomainDishItem

@Suppress("UNCHECKED_CAST")
class Basket : Fragment(), BasketInterface{

    private lateinit var recyclerView: RecyclerView
    private lateinit var basketAdapter: BasketAdapter
    private lateinit var basketViewModel: BasketViewModel
    private lateinit var basketDao: BasketDao

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

        basketAdapter = BasketAdapter(emptyList(), this)
        recyclerView.adapter = basketAdapter

        val dishDataSource = DishDataSource()
        basketDao = AppDataBase.getDatabase(requireContext()).basketDao()
        val basketRepository = BasketRepositoryImpl(dishDataSource, basketDao)
        val getAllBasketUseCase = GetAllBasketUseCase(basketRepository)

        basketViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return BasketViewModel(getAllBasketUseCase) as T
            }
        })[BasketViewModel::class.java]

        basketViewModel.basketItems.observe(viewLifecycleOwner, Observer { basketItems ->
            Log.d("BasketFragment", "basketViewModel.basketItems.observe: ${basketItems.size}")
            basketAdapter.updateData(basketItems)
        })
        basketViewModel.loadBasketItems()
    }

    override fun plus(dishId: DishItem) {
        updateDishCount(dishId.id, 1, "Увеличено")
    }

    override fun minus(dishId: DishItem) {
        updateDishCount(dishId.id, -1, "Уменьшено")
    }

    private fun updateDishCount(dishId: Int, change: Int, message: String) {
        lifecycleScope.launch {
            try {
                val dishEntity = basketDao.getDishById(dishId)
                if (dishEntity != null) {
                    val newCount = dishEntity.count + change // Сначала вычисляем новое значение
                    if (newCount > 0) { // Проверяем, что новое значение больше нуля
                        dishEntity.count = newCount // Устанавливаем новое значение
                        basketDao.updateDish(dishEntity) // Обновляем в базе данных
                        basketViewModel.loadBasketItems() // Обновляем список
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    } else {
                        delete(DishItem(dishEntity.id,dishEntity.url,
                            dishEntity.name,dishEntity.price,dishEntity.weight,
                            dishEntity.description,dishEntity.categoryId,
                            dishEntity.count)) // Если 0, удаляем
                    }
                }
            } catch (e: Exception) {
                Log.e("BasketFragment", "Ошибка при обновлении количества", e)
                Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun delete(dishId: DishItem) {
        lifecycleScope.launch {
            try {
                basketDao.deleteDishById(dishId.id)
                basketViewModel.loadBasketItems()
                Toast.makeText(requireContext(), "Удалено", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show()
            }
        }
    }
}