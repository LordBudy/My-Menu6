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
// Объявляем класс DishAdapter, который наследуется от RecyclerView.Adapter
// Этот адаптер отвечает за отображение списка блюд в RecyclerView
class MenuAdapter(
    // Список блюд, которые нужно отобразить
    private var dishs: List<DishItem>,
    private val onClick: (DishItem) -> Unit //  Лямбда-функция, вызываемая при нажатии на блюдо
    // Указываем тип ViewHolder, который будет использоваться
     ) : RecyclerView.Adapter<MenuAdapter.DishViewHolder>() {
    // Метод для обновления данных в адаптере
    fun updateData(newDish: List<DishItem>) {
        // Обновляем список блюд
        dishs =newDish
        // Сообщаем адаптеру, что данные изменились, чтобы он переписал список
        notifyDataSetChanged()
    }
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dish_menu, parent, false)
        // Создаем и возвращаем ViewHolder
        return DishViewHolder(view)
    }
    // Этот метод вызывается, чтобы привязать данные к ViewHolder
    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        // Получаем блюдо по позиции
        val dish = dishs[position]
        // Устанавливаем данные в соответствующие View
        // Устанавливаем название блюда
        holder.dishNameTextView.text = dish.name
        // Устанавливаем цену блюда
        holder.PriceTextView.text = dish.price.toString()
        // Устанавливаем вес блюда
        holder.WeightTextView.text = dish.weight.toString()
        // Загружаем изображение блюда с помощью Picasso
        Picasso.get()
            // Загружаем изображение по URL
            .load(dish.url)
            // Устанавливаем изображение-заполнитель (отображается, пока загружается изображение)
            .placeholder(R.drawable.placeholder_image)
            // Устанавливаем изображение ошибки (отображается, если не удалось загрузить изображение)
            .error(R.drawable.error_image)
            // Помещаем загруженное изображение в dishImageView
            .into(holder.dishImageView)

        //  Обработчик нажатия на элемент списка
        holder.itemView.setOnClickListener {
            onClick(dish) //  Вызываем лямбда-функцию, передавая DishItem
        }
    }
    //метод возвращает количество элементов в списке
    override fun getItemCount(): Int {
        // Возвращаем размер списка блюд
        return dishs.size
    }
}