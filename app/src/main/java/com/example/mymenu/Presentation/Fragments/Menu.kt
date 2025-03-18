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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymenu.Data.ApiService.DishDataSource

import com.example.mymenu.Data.Repository.DishRepositoryImpl  // Замените на путь к вашему DishRepositoryImpl
import com.example.mymenu.Domain.Dish.GetDishsUseCase  // Замените на путь к вашему GetDishesUseCase
import com.example.mymenu.Presentation.Adapters.DishAdapter
import com.example.mymenu.Presentation.ViewModels.MenuViewModel  // Замените на путь к вашей MenuViewModel
import com.example.mymenu.R

@Suppress("UNCHECKED_CAST")
class Menu : Fragment() {

    private lateinit var viewModel: MenuViewModel  // Изменили тип на MenuViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var dishAdapter: DishAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var errorMessageTextView: TextView
    private var categoryId: Int = -1  // ID категории, для которой отображаем блюда

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MenuFragmentTag", "onCreate() вызван")
        // Получаем ID категории из аргументов
        categoryId = arguments?.getInt("categoryId") ?: -1
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewMenu)
        recyclerView.layoutManager = GridLayoutManager(requireContext(),3)
        errorMessageTextView = view.findViewById(R.id.textViewMenuErrorMessage)
        dishAdapter = DishAdapter(emptyList()) // Инициализируем адаптер
        recyclerView.adapter = dishAdapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Проверяем, что ID категории получен
        if (categoryId == -1) {
            Toast.makeText(requireContext(), "Не удалось получить ID категории", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.popBackStack() // Возвращаемся назад, если ID не получен
            return
        }

        // Создаем зависимости
        val dishDataSource = DishDataSource()
        val dishRepository = DishRepositoryImpl(dishDataSource)
        val getDishsUseCase = GetDishsUseCase(dishRepository)

        // Создаем ViewModel
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MenuViewModel(getDishsUseCase, categoryId) as T // Передаем categoryId в ViewModel
            }
        })[MenuViewModel::class.java]

        // Подписываемся на LiveData
        viewModel.dishes.observe(viewLifecycleOwner, Observer { dishList -> // Здесь dishes, а не categories!
            if (dishList != null) {
                dishAdapter.updateData(dishList)  // Используем dishAdapter
            } else {
                Toast.makeText(requireContext(), "Не удалось загрузить блюда", Toast.LENGTH_SHORT).show()
            }
        })

        // Подписываемся на LiveData isLoading
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            recyclerView.visibility = if (!isLoading) View.VISIBLE else View.GONE
        })

        // Подписываемся на LiveData errorMessage
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            if (errorMessage != null) {
                errorMessageTextView.text = errorMessage
                errorMessageTextView.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else {
                errorMessageTextView.visibility = View.GONE
            }
        })

        // Загружаем блюда для выбранной категории
        viewModel.loadDishes(categoryId) // загрузка при создании
    }
}