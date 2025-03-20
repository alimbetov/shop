package kz.shop.book.controllers;


import kz.shop.book.dto.AuthRequestDto;
import kz.shop.book.dto.AuthResponseDto;
import kz.shop.book.dto.UserDto;
import kz.shop.book.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto request) {
        return userService.login(request);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody AuthRequestDto request) {
        return ResponseEntity.ok(userService.createUser(request));
    }


    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUserRoles(@RequestHeader(name = "Authorization", required = false) String authHeader) {
        // Проверяем, передан ли заголовок Authorization
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        // Извлекаем сам токен (убираем "Bearer ")
        String token = authHeader.substring(7);

        // Возвращаем роли пользователя, используя токен
        return ResponseEntity.ok(userService.getUSerRoleListByTokken(token));
    }
}
