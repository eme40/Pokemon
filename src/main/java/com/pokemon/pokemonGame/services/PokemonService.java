package com.pokemon.pokemonGame.services;

import com.pokemon.pokemonGame.dto.PokemonDto;
import com.pokemon.pokemonGame.dto.PokemonResponse;

import java.util.List;

public interface PokemonService {
  PokemonDto createPokemon(PokemonDto pokemonDto);
  List<PokemonDto> getAllPokemons();
  PokemonDto getPokemonById(int id);
  PokemonDto updatePokemonById(int id, PokemonDto pokemonDto);
  void deletePokemon(int id);

  PokemonResponse pagination(int pageNo, int pageSize);
}
