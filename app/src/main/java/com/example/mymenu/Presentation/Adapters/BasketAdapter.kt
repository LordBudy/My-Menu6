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

class BasketAdapter (private var basketItems: List<DishItem>) :
    RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {

    class BasketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val urlImageView: ImageButton = itemView.findViewById(R.id.urlImage)
        val nameTextView: TextView = itemView.findViewById(R.id.name)
        val priceTextView: TextView = itemView.findViewById(R.id.price)
        val weightTextView: TextView = itemView.findViewById(R.id.weight)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dish_basket, parent, false)
        return BasketViewHolder(view)
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        val item = basketItems[position]
        holder.nameTextView.text = item.name
        holder.priceTextView.text = "Цена: ${item.price}"
        holder.weightTextView.text = "Вес: ${item.weight}"
        Picasso.get()
            .load(item.url)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.error_image)
            .into(holder.urlImageView)
    }

    override fun getItemCount(): Int {
        return basketItems.size
    }

    fun updateData(newBasketItems: List<DishItem>) {
        basketItems = newBasketItems
        notifyDataSetChanged()
    }
}