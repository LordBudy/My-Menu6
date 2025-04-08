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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymenu.Data.ApiService.DishDataSource

import com.example.mymenu.Data.Repository.DishRepositoryImpl  // Замените на путь к вашему DishRepositoryImpl
import com.example.mymenu.Domain.Dish.GetDishsUseCase  // Замените на путь к вашему GetDishesUseCase
import com.example.mymenu.Domain.Models.DishItem
import com.example.mymenu.Presentation.Adapters.DishAdapter
import com.example.mymenu.Presentation.ViewModels.MenuViewModel  // Замените на путь к вашей MenuViewModel
import com.example.mymenu.R

@Suppress("UNCHECKED_CAST")
class Menu : Fragment() {
    // Объявляем переменную viewModel типа MenuViewModel
    private lateinit var viewModel: MenuViewModel
    // Объявляем переменную recyclerView типа RecyclerView (для отображения списка блюд)
    private lateinit var recyclerView: RecyclerView
    // Объявляем переменную dishAdapter типа DishAdapter (для адаптера RecyclerView)
    private lateinit var dishAdapter: DishAdapter
    // Объявляем переменную categoryId типа Int (ID категории) и инициализируем значением -1
    private var categoryId: Int = -1
    // Переопределяем метод onCreate(), который вызывается при создании фрагмента
    override fun onCreate(savedInstanceState: Bundle?) {
        // Вызываем super.onCreate() для выполнения базовой инициализации
        super.onCreate(savedInstanceState)
        // Получаем ID категории из аргументов, переданных фрагменту (если они есть).
        // Если аргументы не переданы или ID категории не найден, то используется значение по умолчанию -1
        categoryId = arguments?.getInt("categoryId") ?: -1
    }
    // Переопределяем метод onCreateView(), который отвечает за создание View фрагмента
    override fun onCreateView(
        // Объект LayoutInflater для раздувания layout-файлов
        inflater: LayoutInflater,
        // Контейнер, в который будет помещен View фрагмента
        container: ViewGroup?,
        // Сохраненное состояние фрагмента (если есть)
        savedInstanceState: Bundle?
    ): View? {
        // Раздуваем layout-файл fragment_menu.xml
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        // Находим RecyclerView по его ID
        recyclerView = view.findViewById(R.id.recyclerViewMenu)
        // Устанавливаем GridLayoutManager для RecyclerView (для отображения элементов в виде сетки с 3 столбцами)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        // Возвращаем созданное View (корневой элемент layout-файла)
        return view
    }
    // Функция для открытия фрагмента MenuMini (с информацией о блюде)
    private fun openMenuMiniFragment(dishItem: DishItem) {
        // Создаем экземпляр фрагмента MenuMini
        val menuMiniFragment = MenuMini()
        // Создаем экземпляр Bundle для передачи данных
        val bundle = Bundle()
        // Помещаем ID блюда в Bundle с ключом "dishId"
        bundle.putInt("dishId", dishItem.id)
        // Устанавливаем аргументы для MenuMiniFragment (передаем Bundle)
        menuMiniFragment.arguments = bundle
// Получаем FragmentTransaction для управления фрагментами
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        // Добавляем MenuMiniFragment в контейнер (R.id.Container_frag)
        transaction.add(R.id.Container_frag, menuMiniFragment)
// Добавляем транзакцию в BackStack (для возможности возврата)
        transaction.addToBackStack(null)
// Подтверждаем транзакцию
        transaction.commit()
    }

    // Переопределяем метод onViewCreated(), который вызывается после создания View фрагмента
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Вызываем super.onViewCreated() для выполнения базовой инициализации
        super.onViewCreated(view, savedInstanceState)
// Проверяем, удалось ли получить ID категории
        if (categoryId == -1) {
            // Toast.makeText(requireContext(), "Не удалось получить ID категории", Toast.LENGTH_SHORT).show()
            // Отображаем сообщение об ошибке
            Toast.makeText(requireContext(), "Не удалось получить ID категории", Toast.LENGTH_SHORT)
                .show()
            // Возвращаемся к предыдущему фрагменту
            requireActivity().supportFragmentManager.popBackStack()
            // Выходим из метода
            return
        }
// Создаем экземпляр DishDataSource (из сточника данных для блюд)
        val dishDataSource = DishDataSource()
// Создаем экземпляр DishRepositoryImpl (реализация репозитория для блюд)
        val dishRepository = DishRepositoryImpl(dishDataSource)
// Создаем экземпляр GetDishsUseCase (UseCase для получения списка блюд)
        val getDishsUseCase = GetDishsUseCase(dishRepository)
// Создаем MenuViewModel с помощью ViewModelProvider
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            // Переопределяем метод create() для создания экземпляра ViewModel
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
// Создаем и возвращаем экземпляр MenuViewModel, передавая ему GetDishsUseCase и categoryId
                return MenuViewModel(getDishsUseCase, categoryId) as T
            }
        })[MenuViewModel::class.java] // Получаем экземпляр MenuViewModel

        // Инициализируем адаптер с передачей лямбда-функции onClick
        // Создаем экземпляр DishAdapter с пустым списком и лямбда-функцией, которая будет вызываться при нажатии на элемент списка
        dishAdapter = DishAdapter(emptyList()) { dishItem ->
            // Вызываем метод openMenuMiniFragment(), чтобы открыть фрагмент с информацией о блюде
            openMenuMiniFragment(dishItem) // Вызываем метод для перехода
        }
        // Устанавливаем адаптер для RecyclerView
        recyclerView.adapter = dishAdapter
// Наблюдаем за LiveData dishs в ViewModel и обновляем UI при изменении данных
        viewModel.dishs.observe(viewLifecycleOwner, Observer { dishList ->
            // Проверяем, что список блюд не null
            if (dishList != null) {
                // Обновляем данные в адаптере
                dishAdapter.updateData(dishList)
            } else {
                // Toast.makeText(requireContext(), "Не удалось загрузить блюда", Toast.LENGTH_SHORT).show()
                // Отображаем сообщение об ошибке
                Toast.makeText(requireContext(), "Не удалось загрузить блюда", Toast.LENGTH_SHORT)
                    .show()
            }
        })
// Загружаем список блюд для заданной категории
        viewModel.loadDishes()
    }
}