package com.pokemon.pokemonGame.services;

import com.pokemon.pokemonGame.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
  ReviewDto createReview(int pokemonId, ReviewDto reviewDto);
  List<ReviewDto> getReviewByPokemonId(int id);
  ReviewDto getReviewById(int pokemonId, int reviewId);
  ReviewDto updateReview(int pokemonId, int reviewId, ReviewDto reviewDto);
  void deleteReview(int pokemonId, int reviewId);
}
