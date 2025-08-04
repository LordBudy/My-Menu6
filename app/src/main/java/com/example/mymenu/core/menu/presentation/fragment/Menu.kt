package com.example.mymenu.core.menu.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymenu.core.activity.MainActivity
import com.example.mymenu.core.menu.presentation.adapter.MenuAdapter
import com.example.mymenu.core.menu.presentation.viewModel.MenuViewModel
import com.example.mymenu.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import kotlin.getValue

@Suppress("UNCHECKED_CAST")
class Menu : Fragment() {
    private val viewModel: MenuViewModel by viewModel { parametersOf(categoryId) }
    private lateinit var recyclerView: RecyclerView
    private lateinit var menuAdapter: MenuAdapter
    private var categoryId: Int = -1
    private lateinit var btnAllMenu: Button
    private lateinit var btnWithMeat: Button
    private lateinit var btnWithRice: Button
    private lateinit var btnWithFish: Button
    private lateinit var btnSalads: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        categoryId = arguments?.getInt("categoryId") ?: -1
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (categoryId == -1) {
            Toast.makeText(requireContext(), "Не удалось получить ID категории", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.popBackStack()
            return
        }

        setupViews(view)
        setupRecyclerView()
        observeViewModel()
        setupButtonListeners()

        viewModel.loadDishs()
    }

    private fun setupViews(view: View) {
        btnAllMenu = view.findViewById(R.id.btnAllMenu)
        btnWithMeat = view.findViewById(R.id.btnWithMeat)
        btnWithRice = view.findViewById(R.id.btnWithRice)
        btnWithFish = view.findViewById(R.id.btnWithFish)
        btnSalads = view.findViewById(R.id.btnSalads)
        recyclerView = view.findViewById(R.id.recyclerViewMenu)
    }

    private fun setupRecyclerView() {
        menuAdapter = MenuAdapter(emptyList()) { dishItem ->
            val dishId = dishItem.id
            val categoryId = dishItem.categoryId

            if (dishId != null&& categoryId != null) {
                (activity as? MainActivity)?.showMenuMiniFragment(dishId, categoryId)
            } else {
                Toast.makeText(requireContext(), "Ошибка: ID блюда не найден", Toast.LENGTH_SHORT).show()
            }
        }
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        recyclerView.adapter = menuAdapter
    }

    private fun observeViewModel() {
        viewModel.dishs.observe(viewLifecycleOwner, Observer { dishList ->
            if (dishList != null) {
                menuAdapter.updateData(dishList)
            } else {
                Toast.makeText(requireContext(), "Не удалось загрузить блюда", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupButtonListeners() {
        btnAllMenu.setOnClickListener {
            viewModel.fastLoadDishs(categoryId, null)
        }
        btnWithMeat.setOnClickListener {
            viewModel.fastLoadDishs(categoryId, "meat")
        }
        btnWithRice.setOnClickListener {
            viewModel.fastLoadDishs(categoryId, "rice")
        }
        btnWithFish.setOnClickListener {
            viewModel.fastLoadDishs(categoryId, "fish")
        }
        btnSalads.setOnClickListener {
            viewModel.fastLoadDishs(categoryId, "salad")
        }
    }
}