package com.example.mymenu.Presentation.Fragments

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymenu.Data.ApiService.CatDataSource
import com.example.mymenu.Data.Repository.CategoryRepositoryImpl
import com.example.mymenu.Domain.Category1.GetCategoryUseCase
import com.example.mymenu.Domain.Models.CategoryItem
import com.example.mymenu.MainActivity
import com.example.mymenu.Presentation.Adapters.CategoryAdapter
import com.example.mymenu.Presentation.ViewModels.Interfaces.HomeInterface
import com.example.mymenu.Presentation.ViewModels.HomeViewModel
import com.example.mymenu.R

// Отключил предупреждение о небезопасном приведении типов
@Suppress("UNCHECKED_CAST")
class Home : Fragment(), HomeInterface {
    // viewModel - ViewModel для этого фрагмента
    private lateinit var viewModel: HomeViewModel
    // recyclerView - RecyclerView для отображения списка категорий
    private lateinit var recyclerView: RecyclerView
    // categoryAdapter - Adapter для RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater,// inflater - LayoutInflater для создания View из XML
        container: ViewGroup?,// container - ViewGroup, в который будет добавлен View фрагмента
        savedInstanceState: Bundle?// savedInstanceState - Bundle, содержащий сохраненное состояние фрагмента
    ): View? {
        // Создаем View из XML-файла fragment_home.xml
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        // Находим RecyclerView в View
        recyclerView = view.findViewById(R.id.recyclerViewCategories)
        // Устанавливаем LayoutManager для RecyclerView (в данном случае LinearLayoutManager для вертикального списка)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        // Инициализируем адаптер с обработчиком нажатий
        categoryAdapter = CategoryAdapter(emptyList()) { category ->
            // Открываем MenuFragment при нажатии на категорию
            onCategoryClicked(category) //  Сообщаем ViewModel о нажатии
        }
        // Устанавливаем adapter для RecyclerView
        recyclerView.adapter = categoryAdapter
        // Возвращаем созданный View
        return view
    }

    // onViewCreated - вызывается после создания View фрагмента
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Создаем зависимости (CatDataSource, CategoryRepositoryImpl, GetCategoryUseCase)
        val catDataSource = CatDataSource()
        val categoryRepository = CategoryRepositoryImpl(catDataSource)
        val getCategoryUseCase = GetCategoryUseCase(categoryRepository)
        // Создаем ViewModel с использованием фабрики
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            // Метод create - вызывается для создания ViewModel
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                // Создаем HomeViewModel с внедренным GetCategoryUseCase
                return HomeViewModel(getCategoryUseCase) as T
            }
        })[HomeViewModel::class.java] // Получаем экземпляр ViewModel
        // Устанавливаем ссылку на интерфейс
        viewModel.homeInterface = this
        viewModel.categories.observe(viewLifecycleOwner, {categoryes ->
            if(categoryes != null) {
                showCategoryes(categoryes)
               }
        })
    }

    //методы интерфейса для отображения категорий и открытия меню по клику на одно из категорий
    override fun showCategoryes(categoryes: List<CategoryItem>) {
        categoryAdapter.updateData(categoryes)
    }

    // Функция для открытия Menu по нажатию на категорию
    override fun onCategoryClicked(categoryId: CategoryItem) {
        // 1. Создание Bundle
        val bundle = Bundle()
        // 2. Добавление данных в Bundle
        (activity as? MainActivity)?.navigateToMenu(categoryId.id)
    }
}
