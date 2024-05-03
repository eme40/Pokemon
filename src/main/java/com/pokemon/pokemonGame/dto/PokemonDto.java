package com.pokemon.pokemonGame.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PokemonDto {
  private Integer id;
  private String name;
  private String type;
}
