package kz.shop.book.dto.userInfo;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LanguageDTO {
    private  String code; // Код языка (ISO)
    private String name;


}