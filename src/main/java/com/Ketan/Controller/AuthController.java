package com.Ketan.Controller;

import java.security.Security;
import java.util.Collection;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Ketan.Config.JwtProvider;
import com.Ketan.Repository.CartRepository;
import com.Ketan.Repository.UserRepository;
import com.Ketan.Request.Loginrequest;
import com.Ketan.Respnse.AuthResponse;
import com.Ketan.Service.CustomUserDetailService;
import com.Ketan.model.Cart;
import com.Ketan.model.USER_ROLE;
import com.Ketan.model.User;

import io.jsonwebtoken.Jwt;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private CartRepository cartRepository;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse>createUserHandler(@RequestBody User user) throws Exception{;
        User user1 = userRepository.findByEmail(user.getEmail());
        if (user1 != null){
            throw new Exception("Email already exists");
        }
        User user2 = new User();
        user2.setEmail(user.getEmail());
        user2.setFullname(user.getFullname());
        user2.setRole(user.getRole());
        // user2.setPassword(passwordEncoder.encode(user.getPassword()));
        user2.setPassword(passwordEncoder.encode(user.getPassword()));
        User saved_user = userRepository.save(user2);
        Cart cart = new Cart();
        cart.setCustomer(saved_user);
        cartRepository.save(cart);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(jwt);
        authResponse.setRole(saved_user.getRole());
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse>loginUserHandler(@RequestBody Loginrequest req){
        String email = req.getEmail();
        String password = req.getPassword();
        try{
            Authentication authentication = authenticate(email, password);
            String jwt = jwtProvider.generateToken(authentication);
            AuthResponse authResponse = new AuthResponse();
            authResponse.setToken(jwt);
            // authResponse.setRole(.getRole());
            authResponse.setMessage("Login successful");
            Collection <? extends GrantedAuthority> authorities = authentication.getAuthorities();
            String role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();
            authResponse.setRole(USER_ROLE.valueOf(role));
            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        }
        catch (Exception e){
            throw new BadCredentialsException("Invalid username/password");
        }
    }

    private Authentication authenticate(String email, String password) throws Exception{
        UserDetails userDetails = customUserDetailService.loadUserByUsername(email);
        if (userDetails == null){
            throw new BadCredentialsException("Invalid username");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
