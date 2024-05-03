package com.pokemon.pokemonGame.dto;

import lombok.Data;

@Data
public class AuthResponseDto {
  private String accessToken;
  private String tokeType = "Bearer";

  public AuthResponseDto(String accessToken) {
    this.accessToken = accessToken;
  }
}
