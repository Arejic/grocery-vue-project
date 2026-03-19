package com.tuapp.grocery_backend.controller;

import com.tuapp.grocery_backend.model.Usuario;
import com.tuapp.grocery_backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth") 
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    // Login usando JSON en el body
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        return authService.login(email, password)
                .map(usuario -> ResponseEntity.ok(Map.of(
                        "message", "Login correcto",
                        "user", Map.of(
                                "id", usuario.getId(),
                                "nombre", usuario.getNombre(),
                                "email", usuario.getEmail(),
                                "imagen", usuario.getImagen(),
                                "rol", usuario.getRol()
                        )
                )))
                .orElse(ResponseEntity.status(401).body(
                        Map.of("message", "Usuario o contraseña incorrectos")
                ));
    }

    // Registro de usuario
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String nombre = body.get("nombre");
        String email = body.get("email");
        String password = body.get("password");
        String imagen = body.get("imagen");

        if (nombre == null || email == null || password == null) {
            return ResponseEntity.badRequest().body(
                    Map.of("message", "Faltan datos requeridos")
            );
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setImagen(imagen);

        Usuario savedUser = authService.registrar(usuario, password);

        return ResponseEntity.ok(Map.of(
                "message", "Usuario registrado correctamente",
                "user", Map.of(
                        "id", savedUser.getId(),
                        "nombre", savedUser.getNombre(),
                        "email", savedUser.getEmail(),
                        "imagen", savedUser.getImagen(),
                        "rol", savedUser.getRol()
                )
        ));
    }
}