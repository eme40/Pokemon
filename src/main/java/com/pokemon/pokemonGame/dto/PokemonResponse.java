package com.pokemon.pokemonGame.dto;


import lombok.Builder;
import lombok.Data;

import java.util.List;
@Builder
@Data
public class PokemonResponse {
  private List<PokemonDto> content;
  private int pageNo;
  private int pageSize;
  private Long totalElement;
  private int totalPages;
  private boolean last;
}
