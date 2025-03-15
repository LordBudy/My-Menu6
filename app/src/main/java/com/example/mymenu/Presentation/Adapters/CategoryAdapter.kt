package com.example.mymenu.Presentation.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymenu.Domain.Models.CategoryItem
import com.squareup.picasso.Picasso
import com.example.mymenu.R

class CategoryAdapter(
    private var categories: List<CategoryItem>,
    private val onClick: (CategoryItem) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    // Функция для обновления данных адаптера
    fun updateData(newCategories: List<CategoryItem>) {
        categories = newCategories
        notifyDataSetChanged()
    }

    //ViewHolder
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

        //Заполнение данными
        holder.categoryTextView.text = category.name_cat
        Picasso.get()
            .load(category.url_cat)
            .placeholder(R.drawable.placeholder_image) // Заглушка на время загрузки
            .error(R.drawable.error_image) // Картинка в случае ошибки загрузки
            .into(holder.categoryImageButton)

        holder.itemView.setOnClickListener {
            onClick(category)
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }
}