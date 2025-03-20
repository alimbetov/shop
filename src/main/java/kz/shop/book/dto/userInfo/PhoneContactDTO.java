package kz.shop.book.dto.userInfo;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneContactDTO {
    private Long id;
    private String phoneNumber;
    private Boolean isPublic;
    private String type;
}