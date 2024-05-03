package com.pokemon.pokemonGame.controllers;

import com.pokemon.pokemonGame.dto.AuthResponseDto;
import com.pokemon.pokemonGame.dto.LoginDto;
import com.pokemon.pokemonGame.dto.RegisterDto;
import com.pokemon.pokemonGame.model.Roles;
import com.pokemon.pokemonGame.model.UserEntity;
import com.pokemon.pokemonGame.repository.RoleRepository;
import com.pokemon.pokemonGame.repository.UserRepository;
import com.pokemon.pokemonGame.security.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private AuthenticationManager authenticationManager;
  private UserRepository userRepository;
  private RoleRepository roleRepository;
  private PasswordEncoder passwordEncoder;
  private JwtGenerator jwtGenerator;
  @Autowired
  public AuthController(AuthenticationManager authenticationManager,
                        UserRepository userRepository, RoleRepository roleRepository,
                        PasswordEncoder passwordEncoder, JwtGenerator jwtGenerator) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtGenerator = jwtGenerator;
  }
  @PostMapping("/register")
  ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
    if (userRepository.existsByUsername(registerDto.getUsername())){
      return new ResponseEntity<>("Username is taken", HttpStatus.BAD_REQUEST);
    }
    UserEntity user = new UserEntity();
    user.setUsername(registerDto.getUsername());
    user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

    Roles roles = roleRepository.findByName("USER").get();
    user.setRoles(Collections.singletonList(roles));

    userRepository.save(user);
    return new ResponseEntity<>("User registered success",HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto){
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String token = jwtGenerator.generateToken(authentication);
    return new ResponseEntity<>(new AuthResponseDto(token),HttpStatus.OK);
  }

}
