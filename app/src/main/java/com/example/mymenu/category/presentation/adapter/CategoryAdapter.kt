package com.example.mymenu.category.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymenu.coreModels.CategoryItem
import com.squareup.picasso.Picasso
import com.example.mymenu.R

// CategoryAdapter - Adapter для RecyclerView, отображающий список категорий
// categories - список объектов CategoryItem для отображения
// onClick - лямбда-функция, которая вызывается при нажатии на элемент списка

class CategoryAdapter(
    private var categories: List<CategoryItem>,
    private val onClick: (CategoryItem) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    // updateData - функция для обновления данных в адаптере
    // newCategories - новый список объектов CategoryItem
    fun updateData(newCategories: List<CategoryItem>) {
        // Обновляем список categories
        categories = newCategories
        // Уведомляем адаптер об изменении данных, чтобы RecyclerView обновил отображение
        notifyDataSetChanged()
    }

    // CategoryViewHolder - ViewHolder для отображения одного элемента категории
    // itemView - View, представляющий собой один элемент списка (item_category.xml)
    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // categoryImageButton - ImageButton для отображения изображения категории
        val categoryImageButton: ImageButton = itemView.findViewById(R.id.imageCategory)

        // categoryTextView - TextView для отображения названия категории
        val categoryTextView: TextView = itemView.findViewById(R.id.textCategory)
    }

    // onCreateViewHolder - вызывается для создания ViewHolder-а
    // parent - ViewGroup, в котором будет создан ViewHolder
    // viewType - тип View (в данном случае, один тип для всех элементов)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        // Создаем View для одного элемента списка из XML-файла item_category.xml
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        // Создаем и возвращаем CategoryViewHolder
        return CategoryViewHolder(view)
    }
    // onBindViewHolder - вызывается для привязки данных к ViewHolder-у
    // holder - CategoryViewHolder, который нужно заполнить данными
    // position - позиция элемента в списке
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        // Получаем объект CategoryItem по позиции
        val category = categories[position]

        // Заполнение данных в ViewHolder
        // Устанавливаем текст названия категории
        holder.categoryTextView.text = category.name_cat
        // Загружаем изображение категории с помощью библиотеки Picasso
        Picasso.get()
            .load(category.url_cat)// Загружаем URL изображения
            .placeholder(R.drawable.placeholder_image) // Заглушка на время загрузки
            .error(R.drawable.error_image) // Картинка в случае ошибки загрузки
            .into(holder.categoryImageButton)// Отображаем изображение в ImageButton
        // Устанавливаем обработчик нажатия на элемент списка
        // При нажатии вызывается лямбда-функция onClick, передавая объект CategoryItem
        holder.itemView.setOnClickListener {
            onClick(category)
        }
    }
    // getItemCount - возвращает количество элементов в списке
    override fun getItemCount(): Int {
        return categories.size// Возвращаем размер списка категорий
    }
}