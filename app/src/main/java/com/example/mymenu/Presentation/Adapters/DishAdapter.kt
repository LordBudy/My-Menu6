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

class DishAdapter(
    private var dishs: List<DishItem>,
    private val onClick: (DishItem) -> Unit //  Лямбда-функция, вызываемая при нажатии на блюдо
     ) : RecyclerView.Adapter<DishAdapter.DishViewHolder>() {

    fun updateData(newDishes: List<DishItem>) {
        dishs = newDishes
        notifyDataSetChanged()
    }

    class DishViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dishImageView: ImageButton = itemView.findViewById(R.id.dish)
        val dishNameTextView: TextView = itemView.findViewById(R.id.name_dish)
        val PriceTextView: TextView = itemView.findViewById(R.id.Price)
        val WeightTextView: TextView = itemView.findViewById(R.id.Price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dish_menu, parent, false) // Замените на ваш layout
        return DishViewHolder(view)
    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        val dish = dishs[position]

        holder.dishNameTextView.text = dish.name
        holder.PriceTextView.text = dish.price.toString()
        holder.WeightTextView.text = dish.weight.toString()
        Picasso.get()
            .load(dish.url)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.error_image)
            .into(holder.dishImageView)

        //  Обработчик нажатия на элемент списка
        holder.itemView.setOnClickListener {
            onClick(dish) //  Вызываем лямбда-функцию, передавая DishItem
        }
    }

    override fun getItemCount(): Int {
        return dishs.size
    }
}