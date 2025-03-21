package com.example.mymenu.Presentation.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymenu.Data.ApiService.DishDataSource

import com.example.mymenu.Data.Repository.DishRepositoryImpl  // Замените на путь к вашему DishRepositoryImpl
import com.example.mymenu.Domain.Dish.GetDishsUseCase  // Замените на путь к вашему GetDishesUseCase
import com.example.mymenu.Domain.Models.DishItem
import com.example.mymenu.Presentation.Adapters.DishAdapter
import com.example.mymenu.Presentation.ViewModels.MenuViewModel  // Замените на путь к вашей MenuViewModel
import com.example.mymenu.R

@Suppress("UNCHECKED_CAST")
class Menu : Fragment() {

    private lateinit var viewModel: MenuViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var dishAdapter: DishAdapter
    private var categoryId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryId = arguments?.getInt("categoryId") ?: -1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewMenu)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)

        return view
    }
    private fun openMenuMiniFragment(dishItem: DishItem) {
        val menuMiniFragment = MenuMini()
        val bundle = Bundle()
        bundle.putInt("dishId", dishItem.id)
        menuMiniFragment.arguments = bundle

        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.add(R.id.Container_frag, menuMiniFragment, "MenuMiniFragmentTag") // Добавили тег
        transaction.addToBackStack(null)
        transaction.commit()
    }
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            if (categoryId == -1) {
                Toast.makeText(requireContext(), "Не удалось получить ID категории", Toast.LENGTH_SHORT).show()
                requireActivity().supportFragmentManager.popBackStack()
                return
            }

            val dishDataSource = DishDataSource()
            val dishRepository = DishRepositoryImpl(dishDataSource)
            val getDishsUseCase = GetDishsUseCase(dishRepository)

            viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MenuViewModel(getDishsUseCase, categoryId) as T
                }
            })[MenuViewModel::class.java]

            // Инициализируем адаптер с передачей лямбда-функции onClick
            dishAdapter = DishAdapter(emptyList()) { dishItem ->
                openMenuMiniFragment(dishItem) // Вызываем метод для перехода
            }
            recyclerView.adapter = dishAdapter

            viewModel.dishs.observe(viewLifecycleOwner, Observer { dishList ->
                if (dishList != null) {
                    dishAdapter.updateData(dishList)
                } else {
                    Toast.makeText(requireContext(), "Не удалось загрузить блюда", Toast.LENGTH_SHORT).show()
                }
            })

            viewModel.loadDishes(categoryId)
        }
    }