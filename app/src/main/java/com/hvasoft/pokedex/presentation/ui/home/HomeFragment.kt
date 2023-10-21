package com.hvasoft.pokedex.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.hvasoft.domain.model.Pokemon
import com.hvasoft.pokedex.R
import com.hvasoft.pokedex.databinding.FragmentHomeBinding
import com.hvasoft.pokedex.presentation.ui.common.showPopUpMessage
import com.hvasoft.pokedex.presentation.ui.home.adapter.HomePagingAdapter
import com.hvasoft.pokedex.presentation.ui.home.adapter.OnClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(), OnClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var homePagingAdapter: HomePagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupViewModel()
    }

    private fun setupRecyclerView() {
        homePagingAdapter = HomePagingAdapter(this)
        val gridLayoutManager =
            GridLayoutManager(context, resources.getInteger(R.integer.main_columns))
        binding.homeRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = gridLayoutManager
            adapter = homePagingAdapter
        }
    }

    private fun setupViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                with(binding) {
                    homeViewModel.uiState.collectLatest { homeState ->
                        when (homeState) {
                            is HomeState.Loading -> homeProgressBar.isVisible = true

                            is HomeState.Empty -> {
                                homeProgressBar.isVisible = false
                                emptyStateLayout.isVisible = true
                                homePagingAdapter.submitData(PagingData.empty())
                            }

                            is HomeState.Success -> {
                                homeProgressBar.isVisible = false
                                emptyStateLayout.isVisible = false
                                homePagingAdapter.submitData(homeState.pagingData)
                                homePagingAdapter.refresh()
                            }

                            is HomeState.Failure -> {
                                homeProgressBar.isVisible = false
                                showPopUpMessage(
                                    homeState.errorMessage ?: R.string.error_unknown,
                                    isError = true
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClickPokemon(pokemon: Pokemon) {
        showPopUpMessage(pokemon.name ?: R.string.pokemon_unknown)
    }

}