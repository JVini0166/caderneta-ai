package com.cadernetaai.cadernetaai.controller;

import com.cadernetaai.cadernetaai.domain.User;
import com.cadernetaai.cadernetaai.domain.Workspace;
import com.cadernetaai.cadernetaai.domain.WorkspaceUser;
import com.cadernetaai.cadernetaai.repository.WorkspaceRepository;
import com.cadernetaai.cadernetaai.repository.WorkspaceUserRepository;
import com.cadernetaai.cadernetaai.security.JwtService;
import com.cadernetaai.cadernetaai.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    public static class RegisterRequest {
        @NotBlank
        public String fullName;
        @Email @NotBlank
        public String email;
        @NotBlank
        public String password;
        // novos campos para já vincular a um workspace com uma role
        public Long workspaceId;
        public String role; // SYSTEM_ADMIN, MAYOR_ACCESS, ...
    }

    public static class LoginRequest {
        @Email @NotBlank
        public String email;
        @NotBlank
        public String password;
        public Long workspaceId; // opcional: define o workspace de contexto
    }

    public static class TokenResponse {
        public String token;
        public TokenResponse(String token) { this.token = token; }
    }

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceUserRepository workspaceUserRepository;

    public AuthController(UserService userService,
                          AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          WorkspaceRepository workspaceRepository,
                          WorkspaceUserRepository workspaceUserRepository) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.workspaceRepository = workspaceRepository;
        this.workspaceUserRepository = workspaceUserRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@Valid @RequestBody RegisterRequest req) {
        User user = userService.register(req.fullName, req.email, req.password);
        if (req.workspaceId != null && req.role != null && !req.role.isBlank()) {
            Workspace ws = workspaceRepository.findById(req.workspaceId).orElseThrow();
            WorkspaceUser link = new WorkspaceUser();
            link.setWorkspace(ws);
            link.setUser(user);
            link.setRole(req.role);
            link.setStatus("active");
            workspaceUserRepository.save(link);
        }
        java.util.Map<String, Object> claims = new java.util.HashMap<>();
        claims.put("uid", user.getId());
        claims.put("email", user.getEmail());
        claims.put("fullName", user.getFullName());
        if (req.workspaceId != null) {
            workspaceUserRepository.findByWorkspace_IdAndUser_Id(req.workspaceId, user.getId())
                .ifPresent(link -> claims.put("workspace", java.util.Map.of(
                        "id", req.workspaceId,
                        "role", link.getRole()
                )));
        }
        String token = jwtService.generateToken(user.getEmail(), claims);
        return ResponseEntity.ok(new TokenResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest req) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.email, req.password));
        SecurityContextHolder.getContext().setAuthentication(auth);
        User user = userService.findByEmail(req.email).orElseThrow();
        java.util.Map<String, Object> claims = new java.util.HashMap<>();
        claims.put("uid", user.getId());
        claims.put("email", user.getEmail());
        claims.put("fullName", user.getFullName());
        Long wsId = req.workspaceId;
        if (wsId == null) {
            // tenta pegar o primeiro vínculo caso o cliente não envie workspace
            workspaceUserRepository.findByUserId(user.getId()).stream().findFirst()
                .ifPresent(link -> {
                    claims.put("workspace", java.util.Map.of(
                            "id", link.getWorkspace().getId(),
                            "role", link.getRole()
                    ));
                });
        } else {
            workspaceUserRepository.findByWorkspace_IdAndUser_Id(wsId, user.getId())
                .ifPresent(link -> claims.put("workspace", java.util.Map.of(
                        "id", wsId,
                        "role", link.getRole()
                )));
        }
        String token = jwtService.generateToken(user.getEmail(), claims);
        return ResponseEntity.ok(new TokenResponse(token));
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> me() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(Map.of("user", auth.getName()));
    }
}
