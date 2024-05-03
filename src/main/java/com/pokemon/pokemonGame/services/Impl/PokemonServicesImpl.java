package com.pokemon.pokemonGame.services.Impl;

import com.pokemon.pokemonGame.dto.PokemonDto;
import com.pokemon.pokemonGame.dto.PokemonResponse;
import com.pokemon.pokemonGame.exception.PokemonNotFoundException;
import com.pokemon.pokemonGame.model.Pokemon;
import com.pokemon.pokemonGame.repository.PokemonRepository;
import com.pokemon.pokemonGame.services.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokemonServicesImpl implements PokemonService {

  private PokemonRepository pokemonRepository;
  @Autowired
  public PokemonServicesImpl(PokemonRepository pokemonRepository) {
    this.pokemonRepository = pokemonRepository;
  }


    @Override
  public PokemonDto createPokemon(PokemonDto pokemonDto) {
    Pokemon newPokemon = mapToEntity(pokemonDto);
    Pokemon savePokemon = pokemonRepository.save(newPokemon);
    return mapToDto(savePokemon);
  }

  //  @Override
//  public PokemonDto createPokemon(PokemonDto pokemonDto) {
//    Pokemon newPokemon = Pokemon.builder()
//            .type(pokemonDto.getType())
//            .name(pokemonDto.getName())
//            .build();
//    Pokemon savePokemon = pokemonRepository.save(newPokemon);
//
//    PokemonDto pokemonResponse = PokemonDto.builder()
//            .id(savePokemon.getId())
//            .name(savePokemon.getName())
//            .type(savePokemon.getType())
//            .build();
//    return pokemonResponse;
//  }

  @Override
  public List<PokemonDto> getAllPokemons() {
    List<Pokemon> pokemons = pokemonRepository.findAll();
    return pokemons.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
  }
  @Override
  public PokemonDto getPokemonById(int id) {
    Pokemon  pokemon = pokemonRepository.findById(id).orElseThrow(()-> new PokemonNotFoundException("Pokemon with ID: " + id + " does not exist"));
    return mapToDto(pokemon);
  }
  @Override
  public PokemonDto updatePokemonById(int id, PokemonDto pokemonDto) {
    Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(() -> new PokemonNotFoundException("pokemon does not exist"));
    pokemon.setName(pokemonDto.getName());
    pokemon.setType(pokemonDto.getType());

    Pokemon updatedpokemon = pokemonRepository.save(pokemon);

    return mapToDto(updatedpokemon);
  }
  //  @Override
//  public PokemonDto updatePokemonById(int id, PokemonDto pokemonDto) {
//    Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(() -> new PokemonNotFoundException("pokemon does not exist"));
//    mapToEntity(pokemonDto);
//    Pokemon updatedpokemon = pokemonRepository.save(pokemon);
//    return mapToDto(updatedpokemon);
//  }
  @Override
  public void deletePokemon(int id) {
    Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(()-> new PokemonNotFoundException("Pokemon could not be deleted"));
    pokemonRepository.delete(pokemon);
  }
  @Override
  public PokemonResponse pagination(int pageNo, int pageSize) {
    Pageable pageable = PageRequest.of(pageNo,pageSize);
    Page<Pokemon> pokemons = pokemonRepository.findAll(pageable);
    List<Pokemon> listOfPokemon = pokemons.getContent();
    List<PokemonDto> contents =  listOfPokemon.stream().map((p)-> mapToDto(p)).collect(Collectors.toList());

    PokemonResponse pokemonResponse = PokemonResponse.builder()
            .content(contents)
            .pageNo(pokemons.getNumber())
            .pageSize(pokemons.getSize())
            .totalElement(pokemons.getTotalElements())
            .totalPages(pokemons.getTotalPages())
            .last(pokemons.isLast())
            .build();
    return pokemonResponse;
  }

  public PokemonDto mapToDto(Pokemon pokemon){
    PokemonDto pokemonDto = PokemonDto.builder()
            .id(pokemon.getId())
            .name(pokemon.getName())
            .type(pokemon.getType())
            .build();
    return pokemonDto;
   }

   public Pokemon mapToEntity(PokemonDto pokemonDto){
    Pokemon pokemon = Pokemon.builder()
            .type(pokemonDto.getType())
            .name(pokemonDto.getName())
            .build();
    return pokemon;
   }


}
