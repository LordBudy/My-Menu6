package com.example.mymenu.fastSearch.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.mymenu.coreData.ApiService.DishDataSource
import com.example.mymenu.Data.Repository.DishRepositoryImpl
import com.example.mymenu.fastSearch.domain.GetSearchDishesUseCase
import com.example.mymenu.activity.MainActivity
import com.example.mymenu.fastSearch.presentation.factory.SearchViewModelFactory
import com.example.mymenu.fastSearch.presentation.viewModel.SearchViewModel
import com.example.mymenu.R

@Suppress("DEPRECATION")
class FastSearch : Fragment() {
    private lateinit var viewModel: SearchViewModel
    private lateinit var searchView: SearchView
    private lateinit var btnSearch: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_fast_search, container, false)
        searchView = view.findViewById(R.id.searchView)
        btnSearch = view.findViewById(R.id.btnSearch)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dishDataSource = DishDataSource()
        val dishRepository = DishRepositoryImpl(dishDataSource)
        val getSearchDishesUseCase = GetSearchDishesUseCase(dishRepository)

        val factory = SearchViewModelFactory(getSearchDishesUseCase)
        viewModel = ViewModelProvider(this, factory)[SearchViewModel::class.java]

        btnSearch.setOnClickListener {
            val query = searchView.query.toString()
            if (query.isNotEmpty()) {
                (activity as? MainActivity)?.showSearchResults(query)
                fragmentManager?.beginTransaction()?.remove(this)?.commit()
            } else {
                Toast.makeText(requireContext(), "Введите название блюда", Toast.LENGTH_SHORT).show()
            }
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }
}
