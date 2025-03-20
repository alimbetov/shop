package kz.shop.book.dto.userInfo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LanguageDTO {
    private  String code; // Код языка (ISO)
    private  String name;


}
