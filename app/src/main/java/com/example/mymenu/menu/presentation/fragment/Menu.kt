package com.example.mymenu.menu.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymenu.coreData.ApiService.DishDataSource

import com.example.mymenu.Data.Repository.DishRepositoryImpl
import com.example.mymenu.menu.domain.GetDishsMenuUseCase
import com.example.mymenu.activity.MainActivity
import com.example.mymenu.menu.presentation.adapter.MenuAdapter
import com.example.mymenu.menu.presentation.viewModel.MenuViewModel
import com.example.mymenu.R

@Suppress("UNCHECKED_CAST")
class Menu : Fragment() {

    private lateinit var viewModel: MenuViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var menuAdapter: MenuAdapter
    private var categoryId: Int = -1

    private lateinit var btnAllMenu: Button
    private lateinit var btnWithMeat: Button
    private lateinit var btnWithRice: Button
    private lateinit var btnWithFish: Button
    private lateinit var btnSalads: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        btnAllMenu = view.findViewById(R.id.btnAllMenu)
        btnWithMeat = view.findViewById(R.id.btnWithMeat)
        btnWithRice = view.findViewById(R.id.btnWithRice)
        btnWithFish = view.findViewById(R.id.btnWithFish)
        btnSalads = view.findViewById(R.id.btnSalads)
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
            Toast.makeText(requireContext(), "Не удалось получить ID категории", Toast.LENGTH_SHORT)
                .show()
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
                Toast.makeText(
                    requireContext(),
                    "Ошибка: ID блюда или категории не найдены",
                    Toast.LENGTH_SHORT
                ).show()
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
        //  Устанавливаем слушатели на кнопки
        btnAllMenu.setOnClickListener {
            viewModel.fastLoadDishs(categoryId, null) //  Показать все блюда
        }
        btnWithMeat.setOnClickListener {
            viewModel.fastLoadDishs(categoryId, "meat") //  Показать блюда с мясом
        }
        btnWithRice.setOnClickListener {
            viewModel.fastLoadDishs(categoryId, "rice") //  Показать блюда с рисом
        }
        btnWithFish.setOnClickListener {
            viewModel.fastLoadDishs(categoryId, "fish") //  Показать блюда с рыбой
        }
        btnSalads.setOnClickListener {
            viewModel.fastLoadDishs(categoryId, "salad") //  Показать салаты
        }

        viewModel.dishs.observe(viewLifecycleOwner, Observer { dishList ->
            if (dishList != null) {
                menuAdapter.updateData(dishList)
            } else {
                Toast.makeText(requireContext(), "Не удалось загрузить блюда", Toast.LENGTH_SHORT).show()
            }
        })

        //  Загружаем блюда при создании фрагмента
        viewModel.loadDishs()

        viewModel.dishs.observe(viewLifecycleOwner, Observer { dishList ->
            if (dishList != null) {
                menuAdapter.updateData(dishList)
            } else {
                Toast.makeText(requireContext(), "Не удалось загрузить блюда", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}