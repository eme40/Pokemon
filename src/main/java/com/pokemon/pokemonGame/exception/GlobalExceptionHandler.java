package com.pokemon.pokemonGame.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(PokemonNotFoundException.class)
  public ResponseEntity<ErrorObject> handlePokemonNotFoundException(PokemonNotFoundException ex, WebRequest request){
    ErrorObject errorObject = new ErrorObject();
    errorObject.setMessage(ex.getMessage());
    errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
    errorObject.setTimeStamp(new Date());

    return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ReviewNotFoundException.class)
  public ResponseEntity<ErrorObject> handleReviewNotFoundException(ReviewNotFoundException ex, WebRequest request){
    ErrorObject errorObject = new ErrorObject();
    errorObject.setMessage(ex.getMessage());
    errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
    errorObject.setTimeStamp(new Date());

    return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.NOT_FOUND);
  }
}
