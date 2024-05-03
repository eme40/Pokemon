package com.pokemon.pokemonGame.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "review")
public class Review {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String title;
  private String content;
  private int stars;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "pokemon_id")
  private Pokemon pokemon;
}
