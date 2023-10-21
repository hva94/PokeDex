package com.hvasoft.pokedex.presentation.ui.home.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hvasoft.domain.model.Pokemon
import com.hvasoft.pokedex.R
import com.hvasoft.pokedex.databinding.ItemPokemonBinding
import com.hvasoft.pokedex.presentation.ui.common.loadImageWithUrl

class HomePagingAdapter(private val listener: OnClickListener) :
    PagingDataAdapter<Pokemon, RecyclerView.ViewHolder>(PokemonDiffCallback()) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_pokemon, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val pokemon = getItem(position)

        with(holder as ViewHolder) {
            pokemon?.let { pokemon ->
                setListener(pokemon)
                with(binding) {
                    tvPokemonName.text = pokemon.name
                    ivPokemon.loadImageWithUrl(pokemon.imageUrl)
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
        }
    }

    class PokemonDiffCallback : DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem == newItem
        }
    }

}