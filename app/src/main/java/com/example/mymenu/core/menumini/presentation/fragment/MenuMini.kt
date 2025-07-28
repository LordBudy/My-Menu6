package com.example.mymenu.core.menumini.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymenu.core.data.ApiService.DishDataSource
import com.example.mymenu.core.data.DAO.BasketDao
import com.example.mymenu.core.data.DB.AppDataBase
import com.example.mymenu.core.basket.data.BasketRepositoryImpl
import com.example.mymenu.core.menumini.data.MenuMiniRepositoryImpl
import com.example.mymenu.core.basket.domain.AddDishToBasketUseCase
import com.example.mymenu.core.menumini.domain.GetDishMiniUseCase
import com.example.mymenu.core.models.DishItem
import com.example.mymenu.core.activity.MainActivity
import com.example.mymenu.core.basket.presentation.viewModel.BasketViewModel
import com.example.mymenu.core.menumini.presentation.viewModel.MenuMiniViewModel
import com.example.mymenu.R
import com.squareup.picasso.Picasso

@Suppress("UNCHECKED_CAST", "DEPRECATION")
class MenuMini(private val basketViewModel: BasketViewModel) : Fragment() {
    // Объявляем переменную viewModel
    private lateinit var viewModel: MenuMiniViewModel
    // Объявляем переменные(для хранения id блюда и id категории)
    private var dishId: Int = -1
    private var categoryId: Int = -1

    private lateinit var dishImageView: ImageView //картинка
    private lateinit var dishNameTextView: TextView //название
    private lateinit var dishPriceTextView: TextView //цена
    private lateinit var dishWeightTextView: TextView //вес
    private lateinit var dishDescriptionTextView: TextView //описание

    private lateinit var database: AppDataBase
    private lateinit var basketDao: BasketDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dishId = it.getInt("dishId", -1)
            categoryId = it.getInt("categoryId", -1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, // для создания View из XML
        container: ViewGroup?, // в который будет добавлен View фрагмента
        savedInstanceState: Bundle? // содержащий сохраненное состояние фрагмента
    ): View? {
        // Создаем View из XML-файла fragment_menu_mini.xml
        val view = inflater.inflate(R.layout.fragment_menu_mini, container, false)
        // Инициализируем переменные, связывая их с элементами UI
        dishImageView = view.findViewById(R.id.Image)
        dishNameTextView = view.findViewById(R.id.name_dish)
        dishPriceTextView = view.findViewById(R.id.price)
        dishWeightTextView = view.findViewById(R.id.weight)
        dishDescriptionTextView = view.findViewById(R.id.description)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = AppDataBase.getDatabase(requireContext())
        basketDao = database.basketDao()

        val closeButton: Button = view.findViewById(R.id.close) //кнопка закрытия
        closeButton.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this@MenuMini).commit()
        }
        val aadButton: Button = view.findViewById(R.id.add_Bascket)//кнопка добавить в корзину
        aadButton.setOnClickListener {
            val dish = viewModel.dish.value
            if (dish != null) {
                basketViewModel.addDishToBasket(dish.id)
            }
                (activity as? MainActivity)?.hideMenuMiniFragment()
        }
        val dishDataSource = DishDataSource()
        val menuMiniRepository = MenuMiniRepositoryImpl(dishDataSource)
        val getDishMiniUseCase = GetDishMiniUseCase(menuMiniRepository)
        val basketRepository = BasketRepositoryImpl(dishDataSource, basketDao)
        val addDishToBasketUseCase = AddDishToBasketUseCase(basketRepository)

        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(MenuMiniViewModel::class.java)) {
                    return MenuMiniViewModel(addDishToBasketUseCase,
                        getDishMiniUseCase) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        })[MenuMiniViewModel::class.java]

        viewModel.dish.observe(viewLifecycleOwner, Observer { dish ->
            showMini(dish)
        })
        viewModel.getDish(dishId, categoryId)
    }
    fun showMini(dish: DishItem?) {
        if (dish != null) {
            dishNameTextView.text = dish.name
            dishPriceTextView.text = "Цена: \n${dish.price}р."
            dishWeightTextView.text = "Вес:\n${dish.weight}гр."
            dishDescriptionTextView.text = dish.description
            Picasso.get()
                .load(dish.url)
                .into(dishImageView)
        } else {
            dishNameTextView.text = "Блюдо не найдено"
        }
    }
}

