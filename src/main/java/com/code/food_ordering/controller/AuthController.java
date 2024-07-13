package com.code.food_ordering.controller;


import com.code.food_ordering.config.JWTProvider;
import com.code.food_ordering.dto.AuthResponseDto;
import com.code.food_ordering.dto.LoginRequestDto;
import com.code.food_ordering.entity.Cart;
import com.code.food_ordering.entity.ROLE;
import com.code.food_ordering.entity.User;
import com.code.food_ordering.repository.CartRepository;
import com.code.food_ordering.repository.UserRepository;
import com.code.food_ordering.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CartRepository cartRepository;
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto>createUser(@RequestBody User user) throws Exception {
        User alreadyExistingUser = userRepository.findByEmail(user.getEmail());
        if(alreadyExistingUser != null){
            throw new Exception("This email is already in use");
        }
        User createUser = new User();

        createUser.setPassword(passwordEncoder.encode(user.getPassword()));
        createUser.setEmail(user.getEmail());
        createUser.setFullName(user.getFullName());
        createUser.setRole(user.getRole());
        User savedUser = userRepository.save(createUser);

        Cart cart = new Cart();

        cart.setCustomer(savedUser);
        cartRepository.save(cart);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

       String jwt = jwtProvider.generateToken(authentication);
       AuthResponseDto responseDto = new AuthResponseDto();
       responseDto.setJwt(jwt);
       responseDto.setRole(user.getRole());
       responseDto.setMessage("User created successfully!");


        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> loginUser(@RequestBody LoginRequestDto req){
        String email = req.getEmail();
        String password = req.getPassword();

        Authentication authentication = authenticateUser(email, password);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String role = authorities.isEmpty()?null:authorities.iterator().next().getAuthority();

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponseDto responseDto = new AuthResponseDto();
        responseDto.setMessage("Login successful");
        responseDto.setJwt(jwt);
        responseDto.setRole(ROLE.valueOf(role));
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    private Authentication authenticateUser(String email, String password) {

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
        if(userDetails == null){
            throw new BadCredentialsException("Invalid email or password");
        }

        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Invalid email or password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }


}
