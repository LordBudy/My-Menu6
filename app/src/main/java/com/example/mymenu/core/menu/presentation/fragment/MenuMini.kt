package com.example.mymenu.core.menu.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.mymenu.R
import com.example.mymenu.core.menu.presentation.viewModel.MenuMiniViewModel
import com.example.mymenu.core.models.DishItem
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuMini : Fragment() {

    private val menuMiniViewModel: MenuMiniViewModel by viewModel()

    private var dishId: Int = -1
    private var categoryId: Int = -1

    private lateinit var dishImageView: ImageView //картинка
    private lateinit var dishNameTextView: TextView //название
    private lateinit var dishPriceTextView: TextView //цена
    private lateinit var dishWeightTextView: TextView //вес
    private lateinit var dishDescriptionTextView: TextView //описание

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
        // Получаем аргументы здесь, чтобы гарантированно иметь доступ к Bundle
        arguments?.let {
            dishId = it.getInt("dishId", -1)
            categoryId = it.getInt("categoryId", -1)
            Log.d("MenuMiniFragment", "Получены аргументы: dishId=$dishId, categoryId=$categoryId")
            menuMiniViewModel.getDish(dishId, categoryId)
        }
        val closeButton: Button = view.findViewById(R.id.close) //кнопка закрытия
        closeButton.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this@MenuMini).commit()
        }
        //событие добавления блюда
        menuMiniViewModel.addDishEvent.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Блюдо в корзине", Toast.LENGTH_SHORT).show()
            //после успешного добавления закрываем меню мини
            parentFragmentManager.beginTransaction().remove(this).commit()
        }
        val aadButton: Button = view.findViewById(R.id.add_Bascket)//кнопка добавить в корзину
        aadButton.setOnClickListener {
            val dish = menuMiniViewModel.dish.value
            if (dish != null) {
                Log.d("MenuMiniFragment", "Добавляем блюдо в корзину: id=${dish.id}, name=${dish.name}")
                menuMiniViewModel.addDishToBasket(dish.id)
            } else {
                Log.w("MenuMiniFragment", "Блюдо отсутствует при попытке добавить в корзину")
            }
        }
// Наблюдаем за LiveData dish из menuMiniViewModel
        menuMiniViewModel.dish.observe(viewLifecycleOwner, Observer { dish ->
            Log.d("MenuMiniFragment", "Получены данные о блюде: $dish")
            showMini(dish)
        })
        // Загружаем данные блюда
        if (dishId != -1 && categoryId != -1) {
            Log.d("MenuMiniFragment", "Запрос блюда с  ID: $dishId, Category ID: $categoryId")
            menuMiniViewModel.getDish(dishId, categoryId)
        } else {
            Log.e("MenuMiniFragment", "Неверный dishId или categoryId передан фрагменту MenuMini")
            parentFragmentManager.beginTransaction().remove(this).commit()
        }
    }

    fun showMini(dish: DishItem?) {
        if (dish != null) {
            Log.d("MenuMiniFragment", "Отображаем блюдо: ${dish.name}")
            dishNameTextView.text = dish.name
            dishPriceTextView.text = "Цена: \n${dish.price}р."
            dishWeightTextView.text = "Вес:\n${dish.weight}гр."
            dishDescriptionTextView.text = dish.description
            Picasso.get()
                .load(dish.url)
                .into(dishImageView)
        } else {
            Log.w("MenuMiniFragment", "Не удалось загрузить данные о блюде")
            dishNameTextView.text = "Блюдо не найдено"
        }
    }
}