package com.hvasoft.pokedex.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.hvasoft.domain.model.Pokemon
import com.hvasoft.pokedex.R
import com.hvasoft.pokedex.databinding.FragmentHomeBinding
import com.hvasoft.pokedex.presentation.ui.common.showPopUpMessage
import com.hvasoft.pokedex.presentation.ui.home.adapter.HomeLoadingStateAdapter
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
    private lateinit var homeLoadingStateAdapter: HomeLoadingStateAdapter

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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupRecyclerView() {
        homePagingAdapter = HomePagingAdapter(this).apply {
            withLoadStateHeaderAndFooter(
                header = HomeLoadingStateAdapter(this),
                footer = HomeLoadingStateAdapter(this)
            )
        }
        val gridLayoutManager =
            GridLayoutManager(context, resources.getInteger(R.integer.main_columns))
        binding.homeRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = gridLayoutManager
            adapter = homePagingAdapter
        }
        homeLoadingStateAdapter = HomeLoadingStateAdapter(homePagingAdapter)
    }

    private fun setupViewModel() {
        with(binding) {
            viewLifecycleOwner.lifecycleScope.launch {
                homeViewModel.uiState.collectLatest { state ->
                    homeRecyclerView.isVisible = state is HomeState.Success
                    homeProgressBar.isVisible = state is HomeState.Loading

                    when (state) {
                        is HomeState.Loading -> {
                            homeProgressBar.isVisible = true
                        }

                        is HomeState.Success -> {
                            homePagingAdapter.submitData(state.pagingData)
                        }

                        is HomeState.Failure -> {
                            if (state.errorMessage != null) {
                                showPopUpMessage(state.errorMessage, isError = true)
                            }
                        }
                    }
                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                homePagingAdapter.loadStateFlow.collectLatest { loadStates ->
                    if (loadStates.refresh is LoadState.Loading ||
                        loadStates.append is LoadState.Loading) {
                        if (homePagingAdapter.itemCount > 0)
                            homeProgressBarSecondary.isVisible = true
                        else
                            homeProgressBar.isVisible = true
                        btnRetry.isVisible = false
                        btnRetrySecondary.isVisible = false
                        ivError.isVisible = false
                        tvError.isVisible = false
                    } else {
                        homeProgressBar.isVisible = false
                        homeProgressBarSecondary.isVisible = false
                        val errorState = when {
                            loadStates.prepend is LoadState.Error -> loadStates.prepend as LoadState.Error
                            loadStates.append is LoadState.Error -> loadStates.append as LoadState.Error
                            loadStates.refresh is LoadState.Error -> loadStates.refresh as LoadState.Error
                            else -> null
                        }
                        errorState?.let {
                            showPopUpMessage(getString(R.string.error_connectivity))
                            if (homePagingAdapter.itemCount > 0) {
                                ivError.isVisible = false
                                tvError.isVisible = false
                                btnRetry.isVisible = false
                                btnRetrySecondary.isVisible = true
                            } else {
                                ivError.isVisible = true
                                tvError.isVisible = true
                                btnRetry.isVisible = true
                                btnRetrySecondary.isVisible = false
                            }
                        }
                    }
                }
            }
            btnRetry.setOnClickListener {
                homeViewModel.fetchPokemons()
            }
            btnRetrySecondary.setOnClickListener {
                homeViewModel.fetchPokemons()
            }
        }
    }

    private fun startDetailFragment(pokemon: Pokemon) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(pokemon)
        findNavController().navigate(action)
    }

    /**
     * OnClickListener
     */
    override fun onClickPokemon(pokemon: Pokemon) {
        startDetailFragment(pokemon)
    }

    override fun onClickFavorite(pokemon: Pokemon) {
        homeViewModel.updatePokemon(pokemon)
    }

}