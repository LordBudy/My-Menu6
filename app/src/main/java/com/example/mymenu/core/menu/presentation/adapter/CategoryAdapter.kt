package com.example.mymenu.core.menu.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymenu.R
import com.example.mymenu.core.models.CategoryItem
import com.squareup.picasso.Picasso
import java.io.File

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class CategoryAdapter(private var categories: List<CategoryItem>,
                      private val onClick: (CategoryItem) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    // updateData - функция для обновления данных в адаптере
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newCategories: List<CategoryItem>) {
        categories = newCategories
        notifyDataSetChanged()
    }

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryImageButton: ImageButton = itemView.findViewById(R.id.imageCategory)
        val categoryTextView: TextView = itemView.findViewById(R.id.textCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]

        // Устанавливаем текст названия категории
        holder.categoryTextView.text = category.name

        // Проверяем наличие кешированного изображения
        if (category.imagePath != null && File(category.imagePath).exists()) {
            // Если кешированное изображение существует, загружаем его
            Picasso.get()
                .load(File(category.imagePath)) // Загружаем кешированное изображение
                .placeholder(R.drawable.placeholder_image) // Заглушка на время загрузки
                .error(R.drawable.error_image) // Картинка в случае ошибки загрузки
                .into(holder.categoryImageButton)
        } else {
            // Если кешированного изображения нет, загружаем по URL
            Picasso.get()
                .load(category.url) // Загружаем URL изображения
                .placeholder(R.drawable.placeholder_image) // Заглушка на время загрузки
                .error(R.drawable.error_image) // Картинка в случае ошибки загрузки
                .into(holder.categoryImageButton)
        }

        // Устанавливаем обработчик нажатия на элемент списка
        holder.itemView.setOnClickListener {
            onClick(category)
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }
}