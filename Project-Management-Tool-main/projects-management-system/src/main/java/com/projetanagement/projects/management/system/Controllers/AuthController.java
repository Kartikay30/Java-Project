package com.projetanagement.projects.management.system.Controllers;


import com.projetanagement.projects.management.system.Config.JwtProvider;
import com.projetanagement.projects.management.system.Models.User;
import com.projetanagement.projects.management.system.Request.LoginRequest;
import com.projetanagement.projects.management.system.Response.AuthResponse;
import com.projetanagement.projects.management.system.repository.UserRepository;
import com.projetanagement.projects.management.system.service.Coustomimplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.userdetails.UserDetails;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

  @Autowired
    private PasswordEncoder passwordEncoder;

  @Autowired
    private Coustomimplementation coustomimplementation;
    @PostMapping("/signup")
    public ResponseEntity<?> createUserHandler(@RequestBody User user) {
        // Using null check with existing findByEmail
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }

        User newUser = new User();
        newUser.setFullName(user.getFullName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setProjectSize(0);

        User savedUser = userRepository.save(newUser);
        Authentication authentication=new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = JwtProvider.generateToken(authentication);
        AuthResponse res = new AuthResponse();
        res.setMessage("SignUp Success");
        res.setJwt(jwt);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/signing")
    public ResponseEntity<AuthResponse> signing(@RequestBody LoginRequest loginRequest) {
        try {
            // 1. Load the user details
            UserDetails userDetails = coustomimplementation.loadUserByUsername(loginRequest.getEmail());

            if(userDetails == null){
                throw new BadCredentialsException("Invalid Useranme");
            }

            // 2. Verify the password matches
            if (!passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
                throw new BadCredentialsException("Invalid password");
            }

            // 3. Create authentication token
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            // 4. Generate JWT token
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = JwtProvider.generateToken(authentication);

            // 5. Return success response
            AuthResponse res = new AuthResponse();
            res.setJwt(jwt);
            res.setMessage("Login successful");
            return ResponseEntity.ok(res);

        } catch (UsernameNotFoundException e) {
            AuthResponse res = new AuthResponse();
            res.setMessage("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        } catch (BadCredentialsException e) {
            AuthResponse res = new AuthResponse();
            res.setMessage("Invalid password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
        } catch (Exception e) {
            AuthResponse res = new AuthResponse();
            res.setMessage("Login failed");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }
}
