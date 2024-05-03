package com.pokemon.pokemonGame.services.Impl;

import com.pokemon.pokemonGame.dto.PokemonDto;
import com.pokemon.pokemonGame.dto.ReviewDto;
import com.pokemon.pokemonGame.exception.PokemonNotFoundException;
import com.pokemon.pokemonGame.exception.ReviewNotFoundException;
import com.pokemon.pokemonGame.model.Pokemon;
import com.pokemon.pokemonGame.model.Review;
import com.pokemon.pokemonGame.repository.PokemonRepository;
import com.pokemon.pokemonGame.repository.ReviewRepository;
import com.pokemon.pokemonGame.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
  private PokemonRepository pokemonRepository;
  private ReviewRepository reviewRepository;
  @Autowired
  public ReviewServiceImpl(PokemonRepository pokemonRepository, ReviewRepository reviewRepository) {
    this.pokemonRepository = pokemonRepository;
    this.reviewRepository = reviewRepository;
  }

  @Override
  public ReviewDto createReview(int pokemonId, ReviewDto reviewDto) {
    Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(()-> new PokemonNotFoundException("Pokemon does not exist"));
    Review review = mapToEntity(reviewDto);
    review.setPokemon(pokemon);
    Review newReview = reviewRepository.save(review);
    return mapToDto(newReview);
  }

  @Override
  public List<ReviewDto> getReviewByPokemonId(int id) {
    List<Review> reviews = reviewRepository.findByPokemonId(id);
    return reviews.stream().map((review)-> mapToDto(review)).collect(Collectors.toList());
  }

  @Override
  public ReviewDto getReviewById(int pokemonId, int reviewId) {
    Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(()-> new PokemonNotFoundException("Pokemon not found"));
    Review review = reviewRepository.findById(reviewId).orElseThrow(()-> new ReviewNotFoundException("Review not found"));
    if (review.getPokemon().getId() != pokemon.getId()){
      throw new ReviewNotFoundException("This review doesn't belong to a pokemon");
    }
    return mapToDto(review);
  }

  @Override
  public ReviewDto updateReview(int pokemonId, int reviewId, ReviewDto reviewDto) {
    Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(()-> new PokemonNotFoundException("Pokemon not found"));
    Review review = reviewRepository.findById(reviewId).orElseThrow(()-> new ReviewNotFoundException("Review not found"));
    if (review.getPokemon().getId() != pokemon.getId()){
      throw new ReviewNotFoundException("This review doesn't belong to a pokemon");
    }
    review.setContent(reviewDto.getContent());
    review.setTitle(reviewDto.getTitle());
    review.setStars(reviewDto.getStars());
    Review updateReview = reviewRepository.save(review);
    return mapToDto(updateReview);
  }

  @Override
  public void deleteReview(int pokemonId, int reviewId) {
    Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(()-> new PokemonNotFoundException("Pokemon not found"));
    Review review = reviewRepository.findById(reviewId).orElseThrow(()-> new ReviewNotFoundException("Review not found"));
    if (review.getPokemon().getId() != pokemon.getId()){
      throw new ReviewNotFoundException("This review doesn't belong to a pokemon");
    }
    reviewRepository.delete(review);
  }

  Review mapToEntity(ReviewDto reviewDto){
    Review review = Review.builder()
            .title(reviewDto.getTitle())
            .content(reviewDto.getContent())
            .stars(reviewDto.getStars())
            .build();
    return review;
   }

   ReviewDto mapToDto(Review review){
    ReviewDto reviewDto = ReviewDto.builder()
            .title(review.getTitle())
            .content(review.getContent())
            .id(review.getId())
            .stars(review.getStars())
            .build();
    return reviewDto;
   }
}
