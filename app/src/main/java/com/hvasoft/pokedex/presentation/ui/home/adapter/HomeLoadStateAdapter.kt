package com.hvasoft.pokedex.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hvasoft.pokedex.R
import com.hvasoft.pokedex.databinding.ItemPokemonBinding

class HomeLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<HomeLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = LoadStateViewHolder(parent, retry)

    override fun onBindViewHolder(
        holder: LoadStateViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)

    inner class LoadStateViewHolder(
        parent: ViewGroup,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pokemon, parent, false)
    ) {
        private val binding = ItemPokemonBinding.bind(itemView)
        private val progressBar: ProgressBar = binding.pbLoading
        private val errorMsg: TextView = binding.tvPokemonName
        private val retry: Button = binding.btnRetry
            .also {
                it.setOnClickListener { retry() }
            }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) errorMsg.text = loadState.error.localizedMessage
            progressBar.isVisible = loadState is LoadState.Loading
            retry.isVisible = loadState is LoadState.Error
            errorMsg.isVisible = loadState is LoadState.Error
        }
    }
}