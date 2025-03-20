package com.example.mymenu.Presentation.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mymenu.Data.ApiService.DishDataSource
import com.example.mymenu.Data.Repository.MenuMiniRepositoryImpl
import com.example.mymenu.Domain.MenuMini.GetDishMiniUseCase
import com.example.mymenu.Presentation.ViewModels.MenuMiniViewModel
import com.example.mymenu.R
import com.squareup.picasso.Picasso

@Suppress("UNCHECKED_CAST")
class MenuMini : Fragment() {

    // viewModel - ViewModel для этого фрагмента
    private lateinit var viewModel: MenuMiniViewModel
    private var dishID: Int = -1  // ID блюда
    private lateinit var dishImageView: ImageView
    private lateinit var dishNameTextView: TextView
    private lateinit var dishPriceTextView: TextView
    private lateinit var dishWeightTextView: TextView
    private lateinit var dishDescriptionTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Получаем dishID из аргументов
        arguments?.let {
            dishID = it.getInt("dishId", -1)  // -1 значение по умолчанию, если аргумент не передан
            Log.d("MenuMiniFragment", "onCreate: dishID = $dishID")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,// inflater - LayoutInflater для создания View из XML
        container: ViewGroup?,// container - ViewGroup, в который будет добавлен View фрагмента
        savedInstanceState: Bundle?// savedInstanceState - Bundle, содержащий сохраненное состояние фрагмента
    ): View? {
        // Создаем View из XML-файла fragment_home.xml
        val view = inflater.inflate(R.layout.fragment_menu_mini, container, false)
        dishImageView = view.findViewById(R.id.Image)
        dishNameTextView = view.findViewById(R.id.name_dish)
        dishPriceTextView = view.findViewById(R.id.price)
        dishWeightTextView = view.findViewById(R.id.weight)
        dishDescriptionTextView = view.findViewById(R.id.description)
        Log.d("MenuMiniFragment", "onCreateView: view inflated")
        // Возвращаем созданный View
        return view
    }

    // onViewCreated - вызывается после создания View фрагмента
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Создаем зависимости (DishDataSource, MenuMiniRepositoryImpl, GetCategoryUseCase)
        val dishDataSource = DishDataSource()
        val menuMiniRepository = MenuMiniRepositoryImpl(dishDataSource)
        val getDishsUseCase = GetDishMiniUseCase(menuMiniRepository)

        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(MenuMiniViewModel::class.java)) {
                    return MenuMiniViewModel(getDishsUseCase,dishID) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        })[MenuMiniViewModel::class.java]
        viewModel.loadDishs(dishID)
        viewModel.dish.observe(viewLifecycleOwner, Observer { dish ->
            Log.d("MenuMiniFragment", "dish.observe: dish = $dish")
            if (dish != null) {
                dishNameTextView.text = dish.name
                dishDescriptionTextView.text = dish.description
                dishPriceTextView.text = "Цена: ${dish.price}"
                Picasso.get().load(dish.url).into(dishImageView)

            } else {

                dishNameTextView.text = "Блюдо не найдено"
            }
        })
        Log.d("MenuMini", "onViewCreated finished")
    }

    private fun openMenuMiniFragment(dishId: Int) {
        // 1. Создание Bundle
        val bundle = Bundle()
        // 2. Добавление данных в Bundle
        bundle.putInt("dishId", dishId)
        // 3. Навигация с использованием NavController
        findNavController().navigate(R.id.action_menu_to_menuMini, bundle)

    }
}
