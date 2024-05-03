package com.pokemon.pokemonGame.exception;

public class ReviewNotFoundException extends RuntimeException{
  private static final long serialVisionUID = 2L;
  public ReviewNotFoundException(String message) {
    super(message);
  }
}
