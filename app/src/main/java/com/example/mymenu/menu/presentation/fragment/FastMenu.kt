package com.example.mymenu.menu.presentation.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymenu.coreData.ApiService.DishDataSource
import com.example.mymenu.menu.data.DishRepositoryImpl
import com.example.mymenu.fastSearch.domain.GetSearchDishesUseCase
import com.example.mymenu.activity.MainActivity
import com.example.mymenu.fastSearch.presentation.adapter.SearchAdapter
import com.example.mymenu.fastSearch.presentation.factory.SearchViewModelFactory
import com.example.mymenu.fastSearch.presentation.viewModel.SearchViewModel
import com.example.mymenu.R
class FastMenu : Fragment() {
    private lateinit var viewModel: SearchViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchAdapter: SearchAdapter
    private var searchQuery: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            searchQuery = it.getString("search_query")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_fast_menu, container, false)
        recyclerView = view.findViewById(R.id.recyclerFastMenu)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dishDataSource = DishDataSource()
        val dishRepository = DishRepositoryImpl(dishDataSource)
        val getSearchDishesUseCase = GetSearchDishesUseCase(dishRepository)

        val factory = SearchViewModelFactory(getSearchDishesUseCase)
        viewModel = ViewModelProvider(this, factory)[SearchViewModel::class.java]

        searchAdapter = SearchAdapter(emptyList()) { dishItem ->
            Log.d("FastMenu", "Dish clicked: ${dishItem.name}")
            (activity as? MainActivity)?.showMenuMiniFragment(dishItem.id, 1)
        }

        recyclerView.adapter = searchAdapter

        viewModel.dishs.observe(viewLifecycleOwner, Observer { dishList ->
            if (dishList != null) {
                searchAdapter.updateData(dishList)
            } else {
                Toast.makeText(requireContext(), "Не удалось загрузить блюда", Toast.LENGTH_SHORT).show()
            }
        })
        searchQuery?.let {
            viewModel.loadDishs(it)
        }
    }

    companion object {
        fun newInstance(query: String): FastMenu {
            val fragment = FastMenu()
            val args = Bundle()
            args.putString("search_query", query)
            fragment.arguments = args
            return fragment
        }
    }

}