package com.example.mymenu.core.menu.presentation.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymenu.core.activity.MainActivity
import com.example.mymenu.R
import com.example.mymenu.core.menu.presentation.adapter.MenuAdapter
import com.example.mymenu.core.menu.presentation.viewModel.MenuViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import kotlin.getValue

class FilterMenu : Fragment() {

    // Используем by viewModel() для создания ViewModel с помощью Koin
    //передаем categoryId в Koin иначе он не сможет найти значение параметра
    private val categoryId: Int by lazy {  // Получаем categoryId из arguments
        arguments?.getInt("category_id") ?: -1 // значение по умолчанию если аргумент отсутствует.
    }
    private val viewModel: MenuViewModel by viewModel { parametersOf(categoryId) }
    private lateinit var recyclerView: RecyclerView
    private lateinit var menuAdapter: MenuAdapter
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

        menuAdapter = MenuAdapter(emptyList()) { dishItem ->
            Log.d("FastMenu", "Dish clicked: ${dishItem.name}")
            (activity as? MainActivity)?.showMenuMiniFragment(dishItem.id, 1)
        }

        recyclerView.adapter = menuAdapter

        viewModel.dishs.observe(viewLifecycleOwner, Observer { dishList ->
            if (dishList != null) {
                menuAdapter.updateData(dishList)
            } else {
                Toast.makeText(requireContext(), "Не удалось загрузить блюда", Toast.LENGTH_SHORT)
                    .show()
            }
        })
        searchQuery?.let {
            viewModel.loadDishs()
        }
    }

    companion object {
        fun newInstance(query: String, categoryId: Int): FilterMenu { // Добавляем categoryId
            val fragment = FilterMenu()
            val args = Bundle()
            args.putString("search_query", query)
            args.putInt("category_id", categoryId) // Добавляем categoryId в arguments
            fragment.arguments = args
            return fragment
        }
    }

}