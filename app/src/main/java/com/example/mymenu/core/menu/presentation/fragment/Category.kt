package com.example.mymenu.core.menu.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymenu.R
import com.example.mymenu.core.menu.presentation.viewModel.CategoryViewModel
import com.example.mymenu.core.menu.presentation.adapter.CategoryAdapter
import com.example.mymenu.core.models.CategoryItem
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.security.Policy.Parameters

@Suppress("UNCHECKED_CAST")
class Category : Fragment() {
    // Используем by viewModel() для создания ViewModel с помощью Koin
    val viewModel: CategoryViewModel by viewModel()
    // recyclerView - RecyclerView для отображения списка категорий
    private lateinit var recyclerView: RecyclerView
    // categoryAdapter - Adapter для RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater,//LayoutInflater для создания View из XML
        container: ViewGroup?,//ViewGroup, в который будет добавлен View фрагмента
        savedInstanceState: Bundle?//Bundle, содержащий сохраненное состояние фрагмента
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


        viewModel.categories.observe(viewLifecycleOwner, Observer { categoryes ->
            if (categoryes != null) {
                showCategoryes(categoryes)
            }
        })
    }

    //методы интерфейса для отображения категорий и открытия меню по клику на одно из категорий
    fun showCategoryes(categoryes: List<CategoryItem>) {
        categoryAdapter.updateData(categoryes)
    }

    // Функция для открытия Menu по нажатию на категорию
    fun onCategoryClicked(categoryId: CategoryItem) {
        // 1. Создание Bundle
        val bundle = Bundle()
        // 2. Добавление данных в Bundle
        bundle.putInt("categoryId", categoryId.id)
        findNavController().navigate(R.id.action_home_to_menu, bundle)
    }
}