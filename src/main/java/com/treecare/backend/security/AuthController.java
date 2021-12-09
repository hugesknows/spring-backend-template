package com.treecare.backend.security;

import com.treecare.backend.security.jwt.JwtUtil;
import com.treecare.backend.security.payload.request.LoginRequest;
import com.treecare.backend.security.payload.request.SignupRequest;
import com.treecare.backend.security.payload.response.JwtResponse;
import com.treecare.backend.services.UserDetail;
import com.treecare.backend.user.User;
import com.treecare.backend.user.UserRepository;
import com.treecare.backend.user.role.ERole;
import com.treecare.backend.user.role.Role;
import com.treecare.backend.user.role.RoleRepository;
import com.treecare.backend.utils.payload.reponse.GenericResponse;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

import javax.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController
{
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest)
    {
        try
        {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwtToken = jwtUtil.generateJwtToken(authentication);

            UserDetail userDetails = (UserDetail) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new JwtResponse(
                    jwtToken,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getFirstName(),
                    userDetails.getLastName(),
                    userDetails.getMobileNumber(),
                    userDetails.getEmailAddress(),
                    roles));
        } catch (Exception e)
        {
            return ResponseEntity.badRequest().body(new JwtResponse("Authentication Error", GenericResponse.STATUS_ERROR));
        }

    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest)
    {
        if (userRepository.existsByUsername(signUpRequest.getUsername()))
        {
            return ResponseEntity
                    .badRequest()
                    .body(new GenericResponse("Error: Username is already taken!", GenericResponse.STATUS_ERROR));
        }

        if (userRepository.existsByEmailAddress(signUpRequest.getEmailAddress()))
        {
            return ResponseEntity
                    .badRequest()
                    .body(new GenericResponse("Error: Email is already in use!", GenericResponse.STATUS_ERROR));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmailAddress(), encoder.encode(signUpRequest.getPassword()), signUpRequest.getMobileNumber());

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null)
        {
            Role userRole = roleRepository.findByName(ERole.ROLE_CARER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else
        {
            for (String role : strRoles)
            {
                switch (role)
                {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "trainer":
                        Role modRole = roleRepository.findByName(ERole.ROLE_TRAINER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_CARER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            }
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new GenericResponse("User registered successfully!", GenericResponse.STATUS_SUCCESS));
    }
}
