package com.example.mymenu.core.menu.presentation.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mymenu.R
import com.example.mymenu.core.menu.presentation.viewModel.MenuViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

@Suppress("DEPRECATION")
class FastSearch : Fragment() {
    private lateinit var searchView: SearchView
    private lateinit var btn_search: Button
    private var query: String? = null
    private var categoryId: Int = -1
private lateinit var searchEditText: EditText
    // Используем by viewModel() для создания ViewModel с помощью Koin
    //передаем categoryId в Koin иначе он не сможет найти значение параметра
    val viewModel: MenuViewModel by viewModel { parametersOf(categoryId) }


    @SuppressLint("MissingInflatedId", "ServiceCast")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("FastSearch", "onCreateView")
        val view = inflater.inflate(R.layout.fragment_fast_search, container, false)
        searchView = view.findViewById(R.id.searchView)
        btn_search = view.findViewById(R.id.btn_search)

        // Получаем EditText внутри SearchView
        searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text)

        // Сделаем весь SearchView кликабельным и фокусируем поле ввода при клике
        searchView.setOnClickListener {
            searchEditText.requestFocus()
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT)
        }
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            query = it.getString("search_query")
            categoryId = it.getInt("category_id", -1)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("FastSearch", "onViewCreated")

// Раскрываем SearchView, чтобы поле ввода было видно сразу
        searchView.isIconified = false

        // Отслеживаем изменения в LiveData и обновляем адаптер
        viewModel.dishs.observe(viewLifecycleOwner) { dishs ->
            Log.d("FastSearch", "Получено ${dishs.size} блюд от ViewModel")
            // Обновляем адаптер с новыми данными
        }
// Устанавливаем обработчик нажатия на кнопку поиска
        btn_search.setOnClickListener {
            val query = searchView.query.toString() // Получаем текст из SearchView
            Log.d("FastSearch1", "получили текст ${query}")
            // Запускаем поиск
            viewModel.setSearchQuery(query)
            // Переход на фрагмент Menu
            val bundle = Bundle().apply {
                putString("search_query", query)
                putInt("category_id", categoryId)
            }
            findNavController().navigate(R.id.action_fastSearch_to_menu, bundle)
        }
        // Установка слушателя для SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchLoadDishs(query ?: "")
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchLoadDishs(newText ?: "")
                return true
            }
        })
    }
}