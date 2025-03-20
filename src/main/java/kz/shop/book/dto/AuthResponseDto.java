package kz.shop.book.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class AuthResponseDto {
    private String token;
    private Set<String> roles;
}
