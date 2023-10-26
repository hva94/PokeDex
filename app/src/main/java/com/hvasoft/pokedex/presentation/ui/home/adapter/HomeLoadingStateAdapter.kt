package com.hvasoft.pokedex.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hvasoft.pokedex.R
import com.hvasoft.pokedex.databinding.ItemPokemonBinding

class HomeLoadingStateAdapter(
    private val adapter: HomePagingAdapter
) : LoadStateAdapter<HomeLoadingStateAdapter.NetworkStateItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = NetworkStateItemViewHolder(
        ItemPokemonBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_pokemon, parent, false)
            )
        ) { adapter.retry() }

    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    inner class NetworkStateItemViewHolder(
        private val binding: ItemPokemonBinding,
        private val retryCallback: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnRetry.setOnClickListener { retryCallback() }
        }
        fun bind(loadState: LoadState) {
            with(binding) {
                if (loadState is LoadState.Error) {
                    tvError.text = loadState.error.localizedMessage
                }
                pbLoading.isVisible = loadState is LoadState.Loading
                tvError.isVisible = loadState !is LoadState.Loading
                btnRetry.isVisible = loadState !is LoadState.Loading
            }
        }
    }

}