package com.pokemon.pokemonGame.exception;

import lombok.Data;

@Data
public class PokemonNotFoundException extends RuntimeException{
  private static final long serialVisionUID = 1L;

  public PokemonNotFoundException(String message) {
    super(message);
  }
}
