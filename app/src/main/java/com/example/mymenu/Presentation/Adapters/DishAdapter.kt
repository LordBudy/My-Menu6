package com.example.mymenu.Presentation.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymenu.Domain.Models.DishItem
import com.example.mymenu.R
import com.squareup.picasso.Picasso

class DishAdapter(private var dishes: List<DishItem>) : RecyclerView.Adapter<DishAdapter.DishViewHolder>() {

    fun updateData(newDishes: List<DishItem>) {
        dishes = newDishes
        notifyDataSetChanged()
    }

    class DishViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dishImageView: ImageView = itemView.findViewById(R.id.dish)
        val dishNameTextView: TextView = itemView.findViewById(R.id.name_dish)
        val PriceTextView: TextView = itemView.findViewById(R.id.price)
        val WeightTextView: TextView = itemView.findViewById(R.id.weight)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dish_menu, parent, false) // Замените на ваш layout
        return DishViewHolder(view)
    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        val dish = dishes[position]

        holder.dishNameTextView.text = dish.name
        holder.PriceTextView.text = dish.price.toString()
        holder.WeightTextView.text = dish.weight.toString()
        Picasso.get()
            .load(dish.url)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.error_image)
            .into(holder.dishImageView)
    }

    override fun getItemCount(): Int {
        return dishes.size
    }
}