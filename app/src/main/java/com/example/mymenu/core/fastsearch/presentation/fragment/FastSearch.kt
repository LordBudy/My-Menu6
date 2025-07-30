package com.example.mymenu.core.fastsearch.presentation.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymenu.core.fastsearch.presentation.viewModel.SearchViewModel
import com.example.mymenu.R
import com.example.mymenu.core.activity.MainActivity
import com.example.mymenu.core.fastsearch.presentation.adapter.FastSearchAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

@Suppress("DEPRECATION")
class FastSearch : Fragment() {
    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private lateinit var fastadapter: FastSearchAdapter
    private lateinit var searc_btn: Button
    private var categoryId: Int = -1

    // Используем by viewModel() для создания ViewModel с помощью Koin
    //передаем categoryId в Koin иначе он не сможет найти значение параметра
    private val viewModel: SearchViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_fast_search, container, false)
        searchView = view.findViewById(R.id.searchView)
        recyclerView = view.findViewById(R.id.recyclerView)
        searc_btn = view.findViewById(R.id.searc_btn)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fastadapter = FastSearchAdapter(emptyList()) { dishItem ->
            val dishId = dishItem.id
            val categoryId = dishItem.categoryId
            Log.d("BasketFragment12", "клик по блюду прошел ,созданы переменные ")
            if (dishId != null && categoryId != null) {
                Log.d("BasketFragment12", "клик по блюду прошел ,проверку null прошел ")
                (activity as? MainActivity)?.showMenuMiniFragment(dishId = dishId, categoryId = categoryId)
            } else {
                // если dishId или categoryId равны null то
                Toast.makeText(
                    requireContext(),
                    "Ошибка: ID блюда или категории не найдены",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        recyclerView.adapter = fastadapter


        // Отслеживаем изменения в LiveData и обновляем адаптер
        viewModel.dishs.observe(viewLifecycleOwner) { dishes ->
            Log.d("FastSearch", "Получено ${dishes.size} блюд от ViewModel")
            fastadapter.updateData(dishes) // Обновляем адаптер с новыми данными
        }
// Устанавливаем обработчик нажатия на кнопку поиска
        searc_btn.setOnClickListener {
            val query = searchView.query.toString() // Получаем текст из SearchView
            Log.d("FastSearch1", "получили текст ${query}")
            viewModel.loadDishs(query) // Вызываем метод loadDishs ViewModel
            Log.d("FastSearch2", "вызвали метод loadDishs и передали ${query} ")
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.loadDishs(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.loadDishs(newText ?: "")
                return true
            }
        })
    }

    companion object {
        fun newInstance(categoryId: Int): FastSearch {
            val fragment = FastSearch()
            val args = Bundle()
            args.putInt("category_id", categoryId)
            fragment.arguments = args
            return fragment
        }
    }
}
