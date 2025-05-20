package com.example.mymenu.Presentation.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymenu.Data.DAO.BasketDao
import com.example.mymenu.Data.DB.AppDataBase
import com.example.mymenu.Domain.Models.DishItem
import com.example.mymenu.MainActivity
import com.example.mymenu.Presentation.Adapters.BasketAdapter
import com.example.mymenu.Presentation.ViewModels.BasketViewModel
import com.example.mymenu.Presentation.ViewModels.Factoryes.BasketViewModelFactory
import com.example.mymenu.R
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class Basket : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var basketAdapter: BasketAdapter
    private lateinit var basketViewModel: BasketViewModel
    private lateinit var basketDao: BasketDao
    private lateinit var itogoButton: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_basket, container, false)
        recyclerView = view.findViewById(R.id.rvBasket)
        itogoButton = view.findViewById(R.id.itogo)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        basketAdapter = BasketAdapter(emptyList())
        recyclerView.adapter = basketAdapter


        basketViewModel = ViewModelProvider(requireActivity()).get(BasketViewModel::class.java)

        basketViewModel.basketItems.observe(viewLifecycleOwner, Observer { basketItems ->
            Log.d("BasketFragment", "basketViewModel.basketItems.observe: ${basketItems.size}")
            basketAdapter.updateData(basketItems)
        })
        basketViewModel.totalPrice.observe(viewLifecycleOwner, Observer { totalPrice ->
            Log.d("BasketFragment1", "totalPrice.observe: totalPrice = $totalPrice")
            itogoButton.text = "К оплате: ${String.format("%.2f", totalPrice)} р."
        })
        basketAdapter.setOnItemClickListener(object : BasketAdapter.OnItemClickListener {
            override fun onPlusClicked(dishItem: DishItem) {
                basketViewModel.onPlusClicked(dishItem)
            }

            override fun onMinusClicked(dishItem: DishItem) {
                basketViewModel.onMinusClicked(dishItem)
            }

            override fun onDeleteClicked(dishItem: DishItem) {
                basketViewModel.onDeleteClicked(dishItem)
            }
        })
    }
}