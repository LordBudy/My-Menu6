package com.example.mymenu.Presentation.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymenu.Data.ApiService.DishDataSource

import com.example.mymenu.Data.Repository.DishRepositoryImpl
import com.example.mymenu.Domain.Menu.GetDishsMenuUseCase
import com.example.mymenu.MainActivity
import com.example.mymenu.Presentation.Adapters.MenuAdapter
import com.example.mymenu.Presentation.ViewModels.MenuViewModel
import com.example.mymenu.R

@Suppress("UNCHECKED_CAST")
class Menu : Fragment() {

    private lateinit var viewModel: MenuViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var menuAdapter: MenuAdapter
    private var categoryId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewMenu)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryId = arguments?.getInt("categoryId") ?: -1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (categoryId == -1) {
            Toast.makeText(requireContext(), "Не удалось получить ID категории", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.popBackStack()
            return
        }

        menuAdapter = MenuAdapter(emptyList()) { dishItem ->
            val dishId = dishItem.id
            val categoryId = dishItem.categoryId

            if (dishId != null && categoryId != null) {
                (activity as? MainActivity)?.showMenuMiniFragment(dishId, categoryId)
            } else {
                // Обработка случая, когда dishId или categoryId равны null
                Toast.makeText(requireContext(), "Ошибка: ID блюда или категории не найдены", Toast.LENGTH_SHORT).show()
            }
        }
        recyclerView.adapter = menuAdapter

        val dishDataSource = DishDataSource()
        val dishRepository = DishRepositoryImpl(dishDataSource)
        val getDishsMenuUseCase = GetDishsMenuUseCase(dishRepository)

        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MenuViewModel(getDishsMenuUseCase, categoryId) as T
            }
        })[MenuViewModel::class.java]
        viewModel.dishs.observe(viewLifecycleOwner, Observer { dishList ->
            if (dishList != null) {
                menuAdapter.updateData(dishList)
            } else {
                Toast.makeText(requireContext(), "Не удалось загрузить блюда", Toast.LENGTH_SHORT).show()
            }
        })
    }
}