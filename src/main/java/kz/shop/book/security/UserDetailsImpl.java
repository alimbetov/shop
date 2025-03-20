package kz.shop.book.security;

import kz.shop.book.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private final Long id;
    private final String username;
    private final String password;
    private final boolean isBlocked;
    private final Set<GrantedAuthority> authorities;

    public UserDetailsImpl(User user) {
        this.id = user.getId();  // ✅ Добавляем `getId()`
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.isBlocked = user.isBlocked();  // ✅ Добавляем `isBlocked()`
        this.authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name())) // ✅ Получаем роли
                .collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Изменить логику, если у тебя есть дата истечения
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isBlocked; // ✅ Если пользователь заблокирован, вернуть `false`
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Можно добавить проверку на срок действия пароля
    }

    @Override
    public boolean isEnabled() {
        return !isBlocked; // ✅ Если `isBlocked = true`, значит аккаунт НЕ активен
    }
}
