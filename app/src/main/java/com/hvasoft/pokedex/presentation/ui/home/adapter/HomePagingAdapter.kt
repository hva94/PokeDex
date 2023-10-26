package com.hvasoft.pokedex.presentation.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hvasoft.domain.model.Pokemon
import com.hvasoft.pokedex.R
import com.hvasoft.pokedex.databinding.ItemPokemonBinding
import com.hvasoft.pokedex.presentation.ui.common.getInitials
import com.hvasoft.pokedex.presentation.ui.common.isInputInitialValid
import com.hvasoft.pokedex.presentation.ui.common.loadImageWithUrl

class HomePagingAdapter(private val listener: OnClickListener) :
    PagingDataAdapter<Pokemon, RecyclerView.ViewHolder>(PokemonDiffCallback()) {

    private lateinit var context: Context
    private lateinit var homeLoadingStateAdapter: HomeLoadingStateAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_pokemon, parent, false)
        homeLoadingStateAdapter = HomeLoadingStateAdapter(this)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val pokemon = getItem(position)

        with(holder as ViewHolder) {
            pokemon?.let { pokemon ->
                setListener(pokemon)
                with(binding) {
                    val imageUrl = pokemon.sprites.first().frontDefault
                    ivPokemon.loadImageWithUrl(
                        url = imageUrl,
                        isCircle = true,
                        onSuccess = {
                            ivPokemon.isVisible = true
                            tvPokemonName.isVisible = true
                            tvInitials.isVisible = false
                            tvPokemonName.text = pokemon.name
                            cbFavorite.isVisible = true
                            cbFavorite.isChecked = pokemon.isFavorite
                            ivPlaceholder.isVisible = false
                        },
                        onError = {
                            if (pokemon.name.isNotEmpty() || pokemon.name.isInputInitialValid()) {
                                ivPokemon.isVisible = false
                                tvPokemonName.isVisible = false
                                tvInitials.text = pokemon.name.getInitials()
                                tvInitials.isVisible = true
                                cbFavorite.isVisible = false
                                ivPlaceholder.isVisible = false
                            } else {
                                ivPokemon.isVisible = false
                                tvPokemonName.isVisible = false
                                cbFavorite.isVisible = false
                                tvInitials.isVisible = false
                                ivPlaceholder.isVisible = true
                            }
                        }
                    )
                    homeLoadingStateAdapter.NetworkStateItemViewHolder(binding) { retry() }
                }
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemPokemonBinding.bind(itemView)

        fun setListener(pokemon: Pokemon) {
            binding.root.setOnClickListener {
                listener.onClickPokemon(pokemon)
            }
            binding.cbFavorite.setOnClickListener {
                listener.onClickFavorite(pokemon)
            }
            binding.btnRetry.setOnClickListener {
                retry()
            }
        }
    }

    class PokemonDiffCallback : DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem == newItem
        }
    }

}