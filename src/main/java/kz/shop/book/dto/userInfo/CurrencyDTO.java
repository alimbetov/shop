package kz.shop.book.dto.userInfo;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrencyDTO {
    private String code;
    private String name;
}
