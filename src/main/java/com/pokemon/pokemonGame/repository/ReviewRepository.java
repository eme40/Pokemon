package com.pokemon.pokemonGame.repository;

import com.pokemon.pokemonGame.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
  List<Review> findByPokemonId(int pokemonId);
}
