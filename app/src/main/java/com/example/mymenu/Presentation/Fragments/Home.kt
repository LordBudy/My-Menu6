package com.example.mymenu.Presentation.Fragments

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymenu.Data.ApiService.CatDataSource
import com.example.mymenu.Data.Repository.CategoryRepositoryImpl
import com.example.mymenu.Domain.Category1.GetCategoryUseCase
import com.example.mymenu.Domain.Models.CategoryItem
import com.example.mymenu.Presentation.Adapters.CategoryAdapter
import com.example.mymenu.Presentation.ViewModels.HomeViewModel
import com.example.mymenu.R

@Suppress("UNCHECKED_CAST")
class Home : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var errorMessageTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewCategories)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        progressBar = view.findViewById(R.id.progressBarHome)
        errorMessageTextView = view.findViewById(R.id.textViewErrorMessage)

        categoryAdapter = CategoryAdapter(emptyList()) { category ->
            // Открываем MenuFragment при нажатии на категорию
            val menuFragment = Menu().apply {
                arguments = Bundle().apply {
                    putInt("categoryId", category.id) // Передаем ID категории в MenuFragment
                }
            }
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.Container_frag, menuFragment) // Замените R.id.fragment_container на ваш контейнер
                .addToBackStack(null)
                .commit()
        }
        recyclerView.adapter = categoryAdapter
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Создаем зависимости
        val catDataSource = CatDataSource()
        val categoryRepository = CategoryRepositoryImpl(catDataSource)
        val getCategoryUseCase = GetCategoryUseCase(categoryRepository)

        // Создаем ViewModel с использованием фабрики
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel(getCategoryUseCase) as T
            }
        })[HomeViewModel::class.java] // Получаем экземпляр ViewModel

        // Подписываемся на LiveData
        viewModel.categories.observe(viewLifecycleOwner, Observer { categoryList ->
            if (categoryList != null) {
                // Обновляем адаптер с новым списком категорий
                categoryAdapter.updateData(categoryList) // Вызываем метод экземпляра adapter
            } else {
                // Обрабатываем ошибку (например, отображаем сообщение)
                Toast.makeText(requireContext(), "Не удалось загрузить категории", Toast.LENGTH_SHORT).show()
            }
        })

        // Подписываемся на LiveData isLoading
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            //  Используем progressBar
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            // Используем recyclerView
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
    }
}
