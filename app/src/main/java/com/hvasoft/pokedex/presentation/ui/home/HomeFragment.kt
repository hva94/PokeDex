package com.hvasoft.pokedex.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.hvasoft.domain.model.Pokemon
import com.hvasoft.pokedex.R
import com.hvasoft.pokedex.databinding.FragmentHomeBinding
import com.hvasoft.pokedex.presentation.ui.common.showPopUpMessage
import com.hvasoft.pokedex.presentation.ui.home.adapter.HomeLoadStateAdapter
import com.hvasoft.pokedex.presentation.ui.home.adapter.HomePagingAdapter
import com.hvasoft.pokedex.presentation.ui.home.adapter.OnClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
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
        homePagingAdapter = HomePagingAdapter(this).apply {
            this.withLoadStateHeaderAndFooter(
            header = HomeLoadStateAdapter { homePagingAdapter.retry() },
            footer = HomeLoadStateAdapter { homePagingAdapter.retry() }
        )
        }
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
            homeViewModel.uiState.collectLatest { state ->
                when (state) {
                    is HomeState.Loading -> {
                        binding.homeRecyclerView.isVisible = false
                        binding.homeProgressBar.isVisible = true
                    }
                    is HomeState.Success -> {
                        binding.homeRecyclerView.isVisible = true
                        binding.homeProgressBar.isVisible = false
                        homePagingAdapter.submitData(state.pagingData)
                    }
                    is HomeState.Failure -> {
                        binding.homeRecyclerView.isVisible = false
                        binding.homeProgressBar.isVisible = false
                        if (state.errorMessage != null)
                            showPopUpMessage(state.errorMessage, isError = true)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            homePagingAdapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.homeRecyclerView.scrollToPosition(0) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClickPokemon(pokemon: Pokemon) {
        showPopUpMessage(pokemon.url)
    }

}