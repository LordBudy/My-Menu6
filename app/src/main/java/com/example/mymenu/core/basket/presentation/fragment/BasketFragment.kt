package com.example.mymenu.core.basket.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymenu.core.data.DAO.BasketDao
import com.example.mymenu.core.models.DishItem
import com.example.mymenu.core.basket.presentation.adapter.BasketAdapter
import com.example.mymenu.core.basket.presentation.viewModel.BasketViewModel
import com.example.mymenu.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

@Suppress("UNCHECKED_CAST")
class BasketFragment : Fragment() {
    private val basketViewModel: BasketViewModel by viewModel()

    private lateinit var recyclerView: RecyclerView
    private lateinit var basketAdapter: BasketAdapter
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