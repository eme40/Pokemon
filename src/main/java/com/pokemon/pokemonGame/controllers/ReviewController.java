package com.pokemon.pokemonGame.controllers;

import com.pokemon.pokemonGame.dto.ReviewDto;
import com.pokemon.pokemonGame.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReviewController {
  private ReviewService reviewService;
  @Autowired
  public ReviewController(ReviewService reviewService) {
    this.reviewService = reviewService;
  }

  @PostMapping("/create/{pokemonId}")
  public ResponseEntity<ReviewDto> createReview(@PathVariable int pokemonId, @RequestBody ReviewDto reviewDto){
    return new ResponseEntity<>(reviewService.createReview(pokemonId,reviewDto),HttpStatus.CREATED);
  }
  @GetMapping("/review/{pokemonId}")
  public ResponseEntity<List<ReviewDto>> getReviewsByPokemon(@PathVariable int pokemonId){
    return new ResponseEntity<>(reviewService.getReviewByPokemonId(pokemonId),HttpStatus.OK);
  }
  @GetMapping("/review/{id}/{pokemonId}")
  public ResponseEntity<ReviewDto> getReviewById(@PathVariable(name = "id") int reviewId, @PathVariable(name = "pokemonId") int pokemonId){
    return new ResponseEntity<>(reviewService.getReviewById(pokemonId,reviewId),HttpStatus.OK);
  }
  @PutMapping("/review/{pokemonId}/{reviewId}")
  public ResponseEntity<ReviewDto> updateReview(@PathVariable(name = "pokemonId") int pokemonId, @PathVariable(name = "reviewId") int reviewId, @RequestBody ReviewDto reviewDto){
    return new ResponseEntity<>(reviewService.updateReview(pokemonId,reviewId,reviewDto),HttpStatus.CREATED);
  }
  @DeleteMapping("/review/{pokemonId}/{reviewId}")
  public ResponseEntity<String> deleteReview(@PathVariable(name = "pokemonId") int pokemonId, @PathVariable(name = "reviewId") int reviewId){
    reviewService.deleteReview(pokemonId,reviewId);
    return new ResponseEntity<>("Review Deleted successfully",HttpStatus.OK);
  }
}
