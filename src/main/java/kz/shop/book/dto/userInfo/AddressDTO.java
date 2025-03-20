package kz.shop.book.dto.userInfo;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDTO {
    private Long id;
    private Boolean isPublic;
    private String streetAddress;
    private String postalCode;
    private Double latitude;
    private Double longitude;
    private String cityCode; // 🔥 Вместо City entity, храним код города

}