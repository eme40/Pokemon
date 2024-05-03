package com.pokemon.pokemonGame.controllers;

import com.pokemon.pokemonGame.dto.PokemonDto;

import com.pokemon.pokemonGame.dto.PokemonResponse;
import com.pokemon.pokemonGame.services.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class PokemonController {
  private PokemonService pokemonService;

  @Autowired
  public PokemonController(PokemonService pokemonService) {
    this.pokemonService = pokemonService;
  }

  @PostMapping("pokemon/create")
  public ResponseEntity<PokemonDto> createPokemon(@RequestBody PokemonDto pokemonDto){
    return new  ResponseEntity<>(pokemonService.createPokemon(pokemonDto), HttpStatus.CREATED);
  }

  @GetMapping("pokemon")
  public ResponseEntity<List<PokemonDto>> getAllPokemon(){
    return new ResponseEntity<>(pokemonService.getAllPokemons(), HttpStatus.OK);
  }

  @GetMapping("pokemon/{id}")
  public ResponseEntity<PokemonDto> getPokemonById(@PathVariable int id){
    return ResponseEntity.ok(pokemonService.getPokemonById(id));
  }
  @PutMapping("pokemon/update/{id}")
  public ResponseEntity<PokemonDto> updatePokemon(@PathVariable("id") int pokemonId, @RequestBody PokemonDto pokemonDto){
    return new ResponseEntity<>(pokemonService.updatePokemonById(pokemonId,pokemonDto),HttpStatus.OK);
  }

  @DeleteMapping("pokemon/delete/{id}")
  public ResponseEntity<String> deletePokemon(@PathVariable int pokemonId){
    pokemonService.deletePokemon(pokemonId);
    return new ResponseEntity<>("Pokemon Delete",HttpStatus.OK);
  }

  @GetMapping("pagination")
  public ResponseEntity<PokemonResponse> pagination(
          @RequestParam (value = "pageNo", defaultValue = "0", required = false) int pageNo,
          @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize){
    return new ResponseEntity<>(pokemonService.pagination(pageNo,pageSize),HttpStatus.OK);
  }
}
