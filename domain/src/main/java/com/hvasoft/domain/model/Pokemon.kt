package com.hvasoft.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Pokemon(
    val id: Int,
    val name: String,
    val url: String,
    val height: Int?,
    val weight: Int?,
    val types: @RawValue List<PokemonType>,
    val sprites: @RawValue List<PokemonSprite>,
    val isFavorite: Boolean
) : Parcelable