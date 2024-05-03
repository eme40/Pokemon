package com.pokemon.pokemonGame.model;


import jakarta.persistence.*;
import lombok.*;


import java.util.ArrayList;
import java.util.List;


@Builder
@Entity
@Table(name = "pokemon")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Pokemon {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private String type;

  @OneToMany(orphanRemoval = true, mappedBy = "pokemon", cascade = CascadeType.ALL)
  private List<Review> review = new ArrayList<>();
}
