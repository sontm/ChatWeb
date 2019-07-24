package sansanvn.web.chatweb.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import sansanvn.web.chatweb.dto.JwtAuthenticationResponse;
import sansanvn.web.chatweb.dto.LoginRequest;
import sansanvn.web.chatweb.security.JwtTokenProvider;

@RestController
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	 JwtTokenProvider tokenProvider;
	
		// Use sontm, naruto, cuongnv, 123 for User ROle
	  // Use admin, 123 for Admin role
	  @PostMapping("/api/auth/login")
	  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
	  		System.out.println("Controller:Signin with pwd:" + loginRequest.getPassword());
	  		System.out.println("  Bc Pwd:" + passwordEncoder.encode(loginRequest.getPassword()));
	  		Authentication authentication = authenticationManager.authenticate(
		          new UsernamePasswordAuthenticationToken(
		                  loginRequest.getUsername(),
		                  loginRequest.getPassword()
		          )
  				);
		
		  SecurityContextHolder.getContext().setAuthentication(authentication);
		
		  String jwt = tokenProvider.generateToken(authentication);
		  System.out.println("    Controller:Created jwt:" + jwt);
	      return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
	  }
	  
//    @PostMapping("/signup")
//    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
//        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
//            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
//                    HttpStatus.BAD_REQUEST);
//        }
//
//        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
//            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
//                    HttpStatus.BAD_REQUEST);
//        }
//
//        // Creating user's account
//        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
//                signUpRequest.getEmail(), signUpRequest.getPassword());
//
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//
//        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
//                .orElseThrow(() -> new AppException("User Role not set."));
//
//        user.setRoles(Collections.singleton(userRole));
//
//        User result = userRepository.save(user);
//
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentContextPath().path("/users/{username}")
//                .buildAndExpand(result.getUsername()).toUri();
//
//        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
//    }
}
