package com.example.mymenu.Presentation.Fragments

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
import com.example.mymenu.Data.ApiService.DishDataSource
import com.example.mymenu.Data.DAO.BasketDao
import com.example.mymenu.Data.DB.AppDataBase
import com.example.mymenu.Data.Repository.BasketRepositoryImpl
import com.example.mymenu.Data.Repository.MenuMiniRepositoryImpl
import com.example.mymenu.Domain.Basket.AddDishToBasketUseCase
import com.example.mymenu.Domain.MenuMini.GetDishMiniUseCase
import com.example.mymenu.Domain.Models.DishItem
import com.example.mymenu.MainActivity
import com.example.mymenu.Presentation.ViewModels.Interfaces.MiniInterface
import com.example.mymenu.Presentation.ViewModels.MenuMiniViewModel
import com.example.mymenu.R
import com.squareup.picasso.Picasso

@Suppress("UNCHECKED_CAST", "DEPRECATION")
class MenuMini : Fragment(), MiniInterface {

    // Объявляем переменную viewModel
    private lateinit var viewModel: MenuMiniViewModel
    // Объявляем переменные(для хранения id блюда и id категории)
    private var dishId: Int = -1
    private var categoryId: Int = -1

    // Объявляем переменные для хранения ссылок на элементы UI
    private lateinit var dishImageView: ImageView //картинка
    private lateinit var dishNameTextView: TextView //название
    private lateinit var dishPriceTextView: TextView //цена
    private lateinit var dishWeightTextView: TextView //вес
    private lateinit var dishDescriptionTextView: TextView //описание

    // Объявляем переменную database (для доступа к базе данных)
    private lateinit var database: AppDataBase
    // Объявляем переменную basketDao (для доступа к DAO)
    private lateinit var basketDao: BasketDao
    override fun onCreate(savedInstanceState: Bundle?) {
        // Вызываем super.onCreate() (выполняем базовую инициализацию)
        super.onCreate(savedInstanceState)
        // Получаем dishID из аргументов
        arguments?.let {
            //получаем значение dishId из Bundle
            dishId = it.getInt("dishId", -1)
            categoryId = it.getInt("categoryId", -1)
        }
    }

    // Переопределяем метод onCreateView() (создаем View фрагмента)
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
        // Возвращаем созданный View
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // super.onViewCreated(view, savedInstanceState)
        super.onViewCreated(view, savedInstanceState)
        // Инициализация базы данных и DAO и получаем экземпляр базы данных
        database = AppDataBase.getDatabase(requireContext())
        // получаем DAO для работы с корзиной
        basketDao = database.basketDao()

        // Находим кнопку закрытия
        val closeButton: Button = view.findViewById(R.id.close)
        closeButton.setOnClickListener {
            (activity as? MainActivity)?.hideMenuMiniFragment()
        }

        val dishDataSource = DishDataSource()
        val menuMiniRepository = MenuMiniRepositoryImpl(dishDataSource)
        val getDishMiniUseCase = GetDishMiniUseCase(menuMiniRepository)
        val basketRepository = BasketRepositoryImpl(dishDataSource, basketDao)
        val addDishToBasketUseCase = AddDishToBasketUseCase(basketRepository)
        // Инициализируем viewModel. Также реализуем и передаем интерфейс
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                //  проверяем, что modelClass - MenuMiniViewModel
                if (modelClass.isAssignableFrom(MenuMiniViewModel::class.java)) {
                    //  создаем и возвращаем экземпляр MenuMiniViewModel
                    return MenuMiniViewModel(addDishToBasketUseCase,
                        getDishMiniUseCase,
                        this@MenuMini as MiniInterface
                    ) as T
                }
                // выбрасываем исключение, если modelClass не MenuMiniViewModel
                throw IllegalArgumentException("неизвестный ViewModel class")
            }
        })[MenuMiniViewModel::class.java] // Получаем экземпляр ViewModel
        viewModel.dish.observe(viewLifecycleOwner, Observer { dish ->
            showMini(dish)
        })
        viewModel.getDish(dishId,categoryId)
    }

    override fun showMini(dish: DishItem?) {
        if (dish != null) {
            //название блюда в TextView
            dishNameTextView.text = dish.name
            //цену блюда в TextView
            dishPriceTextView.text = "Цена: \n${dish.price}р."
            //вес блюда в TextView
            dishWeightTextView.text = "Вес:\n${dish.weight}гр."
            //описание блюда в TextView
            dishDescriptionTextView.text = dish.description
            //изображение блюда с помощью Picasso
            Picasso.get()
                .load(dish.url)
                .into(dishImageView)
        } else {
            //устанавливаем текст "Блюдо не найдено", если dish == null
            dishNameTextView.text = "Блюдо не найдено"
        }
    }


    override fun navigateToBascket() {
        TODO("Not yet implemented")
    }

    override fun saveToBascket(dish: DishItem) {
        TODO("Not yet implemented")
    }
}
