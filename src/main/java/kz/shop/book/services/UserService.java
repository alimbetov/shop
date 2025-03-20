package kz.shop.book.services;

import jakarta.annotation.PostConstruct;
import kz.shop.book.dto.AuthRequestDto;
import kz.shop.book.dto.AuthResponseDto;
import kz.shop.book.dto.UserDto;
import kz.shop.book.entities.userinfo.Profile;
import kz.shop.book.entities.Role;
import kz.shop.book.entities.User;
import kz.shop.book.enums.CurrencyName;
import kz.shop.book.enums.LanguageName;
import kz.shop.book.enums.RoleName;
import kz.shop.book.repository.userinfo.ProfileRepository;
import kz.shop.book.repository.RoleRepository;
import kz.shop.book.repository.UserRepository;
import kz.shop.book.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       ProfileRepository profileRepository,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.profileRepository = profileRepository;
    }


    @PostConstruct
    public void initRoles() {
        RoleName[] predefinedRoles = RoleName.values();

        for (RoleName roleName : predefinedRoles) {
            if (roleRepository.findByName(roleName).isEmpty()) {
                Role role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
                System.out.println("Создана роль: " + roleName);
            }
        }
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<UserDto> searchUsersByUsername(String query) {
        return userRepository.findAllByUsernameContainingIgnoreCase(query)
                .stream()
                .map(this::convertToDto)
                .toList();
    }


    public UserDto createUser(AuthRequestDto authRequestDto) {
        User user = new User();
        Set<Role> setRoles = new HashSet<>();
        var userRole = roleRepository.findByName(RoleName.ROLE_USER);
        userRole.ifPresent(setRoles::add);

        user.setUsername(authRequestDto.getEmail());
        user.setRoles(setRoles);
        user.setBlocked(false);
        user.setPassword(passwordEncoder.encode(authRequestDto.getPassword()));

        // ✅ Создаём профиль без ручного назначения `id`
        Profile profile = Profile.builder()
                .profileName(UUID.randomUUID().toString())
                .preferredLanguage(LanguageName.RU)
                .preferredCurrency(CurrencyName.KZT)
                .isPublic(false)
                .emailIsPublic(false)
                .instagramIsPublic(false)
                .telegramIsPublic(false)
                .websiteIsPublic(false)
                .trial(false)
                .user(user) // Связываем профиль с пользователем
                .build();

        user.setProfile(profile); // ✅ Привязываем профиль к `User`
        var savedUser = userRepository.save(user); // ✅ Сохраняем пользователя (Hibernate сам сохранит `Profile`)

        return convertToDto(savedUser);
    }

    public List<String> getUSerRoleListByTokken(String tokken) {
        return jwtUtil.getRolesFromToken(tokken).stream().toList();
    }

    public Profile getProfileByToken(String tokken) {
        var username = jwtUtil.getUsername(tokken);
        var user = findByEmail(username).orElse(null);
        if (user != null) {
            var profile = profileRepository.findByUserId(user.getId());
            if (profile.isPresent()) {
                return profile.get();
            }
        }
        return null;
    }

    public List<String> getAllRoles() {
        return List.of(Arrays.toString(RoleName.values()));
    }

    // ✅ Добавить роль пользователю
    public UserDto addRoleToUser(Long userId, String roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        Role role = roleRepository.findByName(RoleName.valueOf(roleName))
                .orElseThrow(() -> new RuntimeException("Роль не найдена"));

        user.getRoles().add(role);
        return convertToDto(userRepository.save(user));
    }

    // ✅ Удалить роль у пользователя
    public UserDto removeRoleFromUser(Long userId, String roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        Role role = roleRepository.findByName(RoleName.valueOf(roleName))
                .orElseThrow(() -> new RuntimeException("Роль не найдена"));

        user.getRoles().remove(role);
        return convertToDto(userRepository.save(user));
    }


    public ResponseEntity<AuthResponseDto> login(AuthRequestDto request) {
        Optional<User> userOptional = userRepository.findByUsername(request.getEmail());
        if (userOptional.isEmpty() || !passwordEncoder.matches(request.getPassword(), userOptional.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        User user = userOptional.get();
        String token = jwtUtil.createToken(user.getUsername(), user.getRoles());
        Set<String> roleNames = user.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());

        var resp = AuthResponseDto.builder()
                .token(token)
                .roles(roleNames)
                .build();
        return ResponseEntity.ok(resp);
    }

    public UserDto updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        user.setRoles(userDto.getRoles().stream()
                .map(roleName -> roleRepository.findByName(RoleName.valueOf(roleName))
                        .orElseThrow(() -> new RuntimeException("Роль не найдена: " + roleName)))
                .collect(Collectors.toSet()));
        user.setBlocked(userDto.isBlocked());
        return convertToDto(userRepository.save(user));
    }

    public UserDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        return convertToDto(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        Set<String> roleNames = user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet());
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRoles(roleNames);
        dto.setBlocked(user.isBlocked());
        return dto;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByUsername(email);
    }

    public UserDto findUserDtoByEmail(String email) {
        var entity = findByEmail(email).orElse(null);
        if (entity == null) return null;
        return convertToDto(entity);
    }

}
