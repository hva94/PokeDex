package com.hvasoft.pokedex.presentation.ui.home.adapter

import com.hvasoft.domain.model.Pokemon

interface OnClickListener {
    fun onClickPokemon(pokemon: Pokemon)
}