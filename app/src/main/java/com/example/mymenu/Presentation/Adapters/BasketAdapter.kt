package com.example.mymenu.Presentation.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymenu.Domain.Models.DishItem
import com.example.mymenu.R
import com.squareup.picasso.Picasso
// Объявляем класс BasketAdapter, который является адаптером для RecyclerView
// Он отвечает за отображение списка элементов корзины (DishItem)
class BasketAdapter (
    // Принимает список элементов корзины в конструкторе
    private var basketItems: List<DishItem>) :
// Наследуемся от RecyclerView.Adapter и указываем тип ViewHolder
    RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {
    // Класс BasketViewHolder, который представляет собой контейнер для View элемента списка
    class BasketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Находим View элементы в layout-файле item_dish_basket.xml
        // ImageButton для отображения изображения блюда
        val urlImageView: ImageButton = itemView.findViewById(R.id.urlImage)
        // TextView для отображения названия блюда
        val nameTextView: TextView = itemView.findViewById(R.id.name)
        // TextView для отображения цены блюда
        val priceTextView: TextView = itemView.findViewById(R.id.price)
        // TextView для отображения веса блюда
        val weightTextView: TextView = itemView.findViewById(R.id.weight)
    }
    // Этот метод вызывается, когда нужно создать новый ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
// Раздуваем (inflate) layout item_dish_basket.xml
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dish_basket, parent, false)
// Создаем и возвращаем ViewHolder
        return BasketViewHolder(view)
    }
    // Этот метод вызывается, чтобы привязать данные к ViewHolder
    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        // Получаем элемент корзины по позиции
        val item = basketItems[position]
        // Устанавливаем данные в соответствующие View элементы
        holder.nameTextView.text = item.name // Устанавливаем название блюда
        holder.priceTextView.text = "Цена: ${item.price}" // Устанавливаем цену блюда
        holder.weightTextView.text = "Вес: ${item.weight}" // Устанавливаем вес блюда
        // Загружаем изображение блюда с помощью Picasso
        Picasso.get()
            .load(item.url) // Загружаем изображение по URL
            .placeholder(R.drawable.placeholder_image) // Устанавливаем изображение-заполнитель
            .error(R.drawable.error_image) // Устанавливаем изображение ошибки
            .into(holder.urlImageView) // Помещаем изображение в urlImageView
    }
    //метод возвращает количество элементов в списке
    override fun getItemCount(): Int {
        // Возвращаем размер списка элементов корзины
        return basketItems.size
    }
    // Метод для обновления данных в адаптере
    fun updateData(newBasketItems: List<DishItem>) {
        // Обновляем список элементов корзины
        basketItems = newBasketItems
        // Сообщаем адаптеру об изменении данных
        notifyDataSetChanged()
    }
}