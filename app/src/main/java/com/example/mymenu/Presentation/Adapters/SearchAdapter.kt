package com.example.mymenu.Presentation.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymenu.Domain.Models.DishItem
import com.example.mymenu.R
import com.squareup.picasso.Picasso

class SearchAdapter(
// Список блюд, которые нужно отобразить
    private var dishs: List<DishItem>,
    private val onClick: (DishItem) -> Unit //  Лямбда-функция, вызываемая при нажатии на блюдо
    // Указываем тип ViewHolder, который будет использоваться
) : RecyclerView.Adapter<SearchAdapter.DishViewHolder>() {
    // Метод для обновления данных в адаптере

    // Класс DishViewHolder, который представляет собой контейнер для View элемента списка
    class DishViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // ImageButton для отображения изображения блюда
        val dishImageView: ImageButton = itemView.findViewById(R.id.dish)
        // TextView для отображения названия блюда
        val dishNameTextView: TextView = itemView.findViewById(R.id.name_dish)
        // TextView для отображения цены блюда
        val PriceTextView: TextView = itemView.findViewById(R.id.Price)
        // TextView для отображения веса блюда
        val WeightTextView: TextView = itemView.findViewById(R.id.Weight)
    }
    // Этот метод вызывается, когда нужно создать новый ViewHolder для элемента списка
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        // Раздуваем (inflate) layout item_dish_menu.xml, который содержит UI для одного блюда
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dish_menu, parent, false)
        // Создаем и возвращаем ViewHolder
        return DishViewHolder(view)
    }
    // привязываем данные к ViewHolder
    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        val dish = dishs[position]// Получаем блюдо по позиции
        holder.dishNameTextView.text = dish.name //название блюда
        holder.PriceTextView.text = dish.price.toString()//цена блюда
        holder.WeightTextView.text = dish.weight.toString()// вес блюда
        Picasso.get()
            // Загружаем изображение по URL
            .load(dish.url)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.error_image)
            .into(holder.dishImageView)

        //  Обработчик нажатия на элемент списка
        holder.itemView.setOnClickListener {
            onClick(dish) //  Вызываем лямбда-функцию, передавая DishItem
            Log.d("BasketFragment", "клик по блюду прошел ")
        }
    }
    //метод возвращает количество элементов в списке
    override fun getItemCount(): Int {
        // Возвращаем размер списка блюд
        return dishs.size
    }
    fun updateData(newDish: List<DishItem>) {
        // Обновляем список блюд
        dishs = newDish
        // Сообщаем адаптеру, что данные изменились, чтобы он переписал список
        notifyDataSetChanged()
    }

}