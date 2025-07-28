package com.example.mymenu.core.basket.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymenu.core.models.DishItem
import com.example.mymenu.R
import com.squareup.picasso.Picasso

class BasketAdapter (private var basketItems: List<DishItem>) :
    RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {
    interface OnItemClickListener {
        fun onPlusClicked(dishItem: DishItem)
        fun onMinusClicked(dishItem: DishItem)
        fun onDeleteClicked(dishItem: DishItem)
    }
    private var listener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
   inner class BasketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val urlImageView: ImageView = itemView.findViewById(R.id.urlImage)
        val nameTextView: TextView = itemView.findViewById(R.id.name)
        val priceTextView: TextView = itemView.findViewById(R.id.price)
        val weightTextView: TextView = itemView.findViewById(R.id.weight)
        val countTextView: TextView = itemView.findViewById(R.id.count)
        val plusButton: ImageButton = itemView.findViewById(R.id.plusBtn)
        val minusButton: ImageButton = itemView.findViewById(R.id.minusBtn)

        fun bind(dishItem: DishItem) {
            nameTextView.text = dishItem.name
            priceTextView.text = "Цена: ${dishItem.price}"
            weightTextView.text = "Вес: ${dishItem.weight}"
            countTextView.text = " ${dishItem.count}"
            Picasso.get()
                .load(dishItem.url)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(urlImageView)

            plusButton.setOnClickListener {
                listener?.onPlusClicked(dishItem)
            }

            minusButton.setOnClickListener {
                listener?.onMinusClicked(dishItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dish_basket, parent, false)
        return BasketViewHolder(view)
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        val item = basketItems[position]
        (holder as BasketViewHolder).bind(item)
    }

    override fun getItemCount(): Int {
        return basketItems.size
    }

    fun updateData(newBasketItems: List<DishItem>) {
        basketItems = newBasketItems
        notifyDataSetChanged()
    }
}
