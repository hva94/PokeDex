package com.hvasoft.pokedex.presentation.ui.detail;

import com.hvasoft.domain.model.Pokemon;

public class DetailState {

    public static DetailState Loading;

    public static final class Success extends DetailState {
        private final Pokemon pokemon;

        public Success(Pokemon pokemon) {
            this.pokemon = pokemon;
        }

        public Pokemon getPokemon() {
            return pokemon;
        }
    }

    public static final class Failure extends DetailState {
        private final String message;

        public Failure(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}