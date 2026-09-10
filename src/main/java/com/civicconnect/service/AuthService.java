package com.civicconnect.service;

import com.civicconnect.dto.AuthDTOs.*;
import com.civicconnect.entity.Department;
import com.civicconnect.entity.User;
import com.civicconnect.entity.Ward;
import com.civicconnect.repository.DepartmentRepository;
import com.civicconnect.repository.UserRepository;
import com.civicconnect.repository.WardRepository;
import com.civicconnect.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private WardRepository wardRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return JwtResponse.builder()
                .token(jwt)
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole())
                .departmentId(user.getDepartment() != null ? user.getDepartment().getId() : null)
                .departmentName(user.getDepartment() != null ? user.getDepartment().getName() : null)
                .wardId(user.getWard() != null ? user.getWard().getId() : null)
                .wardName(user.getWard() != null ? user.getWard().getWardName() : null)
                .build();
    }

    public String registerUser(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Error: Email is already in use!");
        }

        Department dept = null;
        if (registerRequest.getDepartmentId() != null) {
            dept = departmentRepository.findById(registerRequest.getDepartmentId()).orElse(null);
        }

        Ward ward = null;
        if (registerRequest.getWardId() != null) {
            ward = wardRepository.findById(registerRequest.getWardId()).orElse(null);
        }

        String role = registerRequest.getRole();
        if (role == null || role.isEmpty()) {
            role = "ROLE_CITIZEN";
        } else if (!role.startsWith("ROLE_")) {
            role = "ROLE_" + role.toUpperCase();
        }

        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .fullName(registerRequest.getFullName())
                .phone(registerRequest.getPhone())
                .role(role)
                .department(dept)
                .ward(ward)
                .build();

        userRepository.save(user);
        return "User registered successfully!";
    }
}
