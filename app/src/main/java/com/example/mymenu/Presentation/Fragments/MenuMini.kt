package com.example.mymenu.Presentation.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mymenu.Data.ApiService.DishDataSource
import com.example.mymenu.Data.DAO.BasketDao
import com.example.mymenu.Data.DB.AppDataBase
import com.example.mymenu.Data.ModelsEntitys.CategoryEntity
import com.example.mymenu.Data.ModelsEntitys.DishEntity
import com.example.mymenu.Data.Repository.BasketRepositoryImpl
import com.example.mymenu.Data.Repository.MenuMiniRepositoryImpl
import com.example.mymenu.Domain.Basket.AddDishToBasketUseCase
import com.example.mymenu.Domain.MenuMini.GetDishMiniUseCase
import com.example.mymenu.Domain.Models.CategoryItem
import com.example.mymenu.Domain.Models.DishItem
import com.example.mymenu.Presentation.ViewModels.MenuMiniViewModel
import com.example.mymenu.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Suppress("UNCHECKED_CAST", "DEPRECATION")
class MenuMini : Fragment() {

        // Объявляем переменную viewModel (для хранения экземпляра MenuMiniViewModel)
        private lateinit var viewModel: MenuMiniViewModel
        // Объявляем переменную dishID (для хранения ID блюда)
        private var dishID: Int = -1
        // Объявляем переменные для хранения ссылок на элементы UI
        private lateinit var dishImageView: ImageView
        private lateinit var dishNameTextView: TextView
        private lateinit var dishPriceTextView: TextView
        private lateinit var dishWeightTextView: TextView
        private lateinit var dishDescriptionTextView: TextView
        // Объявляем переменную database (для доступа к базе данных)
        private lateinit var database: AppDataBase
        // Объявляем переменную basketDao (для доступа к DAO)
        private lateinit var basketDao: BasketDao
    private lateinit var addDishToBasketUseCase: AddDishToBasketUseCase
    private lateinit var getDishsUseCase: GetDishMiniUseCase
        // Переопределяем метод onCreate() (вызывается при создании фрагмента)
        override fun onCreate(savedInstanceState: Bundle?) {
            // Вызываем super.onCreate() (выполняем базовую инициализацию)
            super.onCreate(savedInstanceState)
            // Получаем dishID из аргументов
            // проверяем, что arguments не null, и выполняем лямбда-выражение
            arguments?.let {
                //получаем значение dishId из Bundle
                dishID = it.getInt("dishId", -1)
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
        // Метод для открытия фрагмента корзины
        private fun openBasketFragment(dishId: Int) {
            // Создаем Bundle и передаем dishId
            val bundle = Bundle()
            bundle.putInt("dishId", dishId)
            // Выполняем навигацию к фрагменту корзины
            findNavController().navigate(R.id.action_menuMini_to_basket, bundle)
        }
        // Переопределяем метод onViewCreated() (вызывается после создания View)
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            // super.onViewCreated(view, savedInstanceState)
            super.onViewCreated(view, savedInstanceState)
            // Инициализация базы данных и DAO и получаем экземпляр базы данных
            database = AppDataBase.getDatabase(requireContext())
            // получаем DAO для работы с корзиной
            basketDao = database.basketDao()
            // Находим кнопку добавления в корзину
            val addToBasket: Button = view.findViewById(R.id.add_Bascket)
            // Устанавливаем обработчик нажатия на кнопку
            addToBasket.setOnClickListener {
                // Добавляем логику добавления в корзину
            }
            // Находим кнопку закрытия
            val closeButton: Button = view.findViewById(R.id.close)
            // Устанавливаем обработчик нажатия на кнопку
            closeButton.setOnClickListener {
                // Закрываем фрагмент
                // val transaction = requireFragmentManager().beginTransaction()
                val transaction = requireFragmentManager().beginTransaction() // Используем requireFragmentManager()
                // transaction.remove(this) - удаляем текущий фрагмент
                transaction.remove(this)
                // transaction.commit() - подтверждаем транзакцию
                transaction.commit()
            }
            // Создаем ViewModel с помощью ViewModelProvider
            viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    //  проверяем, что modelClass - MenuMiniViewModel
                    if (modelClass.isAssignableFrom(MenuMiniViewModel::class.java)) {
                        //  создаем и возвращаем экземпляр MenuMiniViewModel
                        return MenuMiniViewModel(getDishsUseCase,dishID ) as T
                    }
                    // выбрасываем исключение, если modelClass не MenuMiniViewModel
                    throw IllegalArgumentException("неизвестный ViewModel class")
                }
            })[MenuMiniViewModel::class.java] // Получаем экземпляр ViewModel
            // viewModel.loadDishs(dishID) - загружаем данные о блюде
            viewModel.getDish(dishID)
            //  подписываемся на изменения LiveData dish в ViewModel
            viewModel.dish.observe(viewLifecycleOwner, Observer { dish ->
                // логируем информацию о блюде
                Log.d("MenuMiniFragment", "dish.observe: dish = $dish")
                if (dish != null) {
                    // dishNameTextView.text = dish.name - устанавливаем название блюда в TextView
                    dishNameTextView.text = dish.name
                    // dishPriceTextView.text = "Цена: \n${dish.price}р." - устанавливаем цену блюда в TextView
                    dishPriceTextView.text = "Цена: \n${dish.price}р."
                    // dishWeightTextView.text = "Вес:\n${dish.weight}гр." - устанавливаем вес блюда в TextView
                    dishWeightTextView.text = "Вес:\n${dish.weight}гр."
                    // dishDescriptionTextView.text = dish.description - устанавливаем описание блюда в TextView
                    dishDescriptionTextView.text = dish.description
                    // Picasso.get().load(dish.url).into(dishImageView) - загружаем изображение блюда с помощью Picasso
                    Picasso.get()
                        .load(dish.url)
                        .into(dishImageView)
                } else {
                    // dishNameTextView.text = "Блюдо не найдено" - устанавливаем текст "Блюдо не найдено", если dish == null
                    dishNameTextView.text = "Блюдо не найдено"
                }
            })
        }
    }