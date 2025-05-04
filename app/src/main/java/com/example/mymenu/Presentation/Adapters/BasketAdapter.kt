package com.example.mymenu.Presentation.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymenu.Domain.Models.DishItem
import com.example.mymenu.Presentation.ViewModels.Interfaces.BasketInterface
import com.example.mymenu.R
import com.squareup.picasso.Picasso
// Объявляем класс BasketAdapter, который является адаптером для RecyclerView
// Он отвечает за отображение списка элементов корзины
class BasketAdapter (
    // Принимает список элементов корзины в конструкторе
    private var basketItems: List<DishItem>,
    private val listener: BasketInterface) :
// Наследуемся от RecyclerView.Adapter и указываем тип ViewHolder
    RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {
    // Класс BasketViewHolder, который представляет собой
        // контейнер для View элемента списка
    class BasketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val urlImageView: ImageView = itemView.findViewById(R.id.urlImage)
        val nameTextView: TextView = itemView.findViewById(R.id.name)
        val priceTextView: TextView = itemView.findViewById(R.id.price)
        val weightTextView: TextView = itemView.findViewById(R.id.weight)
        val countTextView: TextView = itemView.findViewById(R.id.count1
        )
        val plusButton: ImageButton = itemView.findViewById(R.id.plusBtn)
        val minusButton: ImageButton = itemView.findViewById(R.id.minusBtn)
    }
    // Этот метод вызывается, когда нужно создать новый ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dish_basket, parent, false)
        return BasketViewHolder(view)
    }
    // Этот метод вызывается, чтобы привязать данные к ViewHolder
    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        Log.d("BasketAdapter1", "onBindViewHolder: position = $position")
        // Получаем элемент корзины по позиции
        val item = basketItems[position]
        Log.d("BasketAdapter2", "item.count = ${item.count}")
        // Устанавливаем данные в соответствующие View элементы
        holder.nameTextView.text = item.name // Устанавливаем название блюда
        holder.priceTextView.text = "Цена: ${item.price}" // Устанавливаем цену блюда
        holder.weightTextView.text = "Вес: ${item.weight}" // Устанавливаем вес блюда
        holder.countTextView.text = item.count.toString()
        // Загружаем изображение блюда с помощью Picasso
        Picasso.get()
            .load(item.url) // Загружаем изображение по URL
            .placeholder(R.drawable.placeholder_image) // Устанавливаем изображение-заполнитель
            .error(R.drawable.error_image) // Устанавливаем изображение ошибки
            .into(holder.urlImageView) // Помещаем изображение в urlImageView
        holder.plusButton.setOnClickListener {
            listener.plus(item)
        }

        holder.minusButton.setOnClickListener {
            listener.minus(item)
        }
    }
    //метод возвращает количество элементов в списке
    override fun getItemCount(): Int {
        Log.d("BasketAdapter", "getItemCount: basketItems.size = ${basketItems.size}")
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