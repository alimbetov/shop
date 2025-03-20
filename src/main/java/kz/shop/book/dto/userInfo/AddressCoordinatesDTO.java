package kz.shop.book.dto.userInfo;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressCoordinatesDTO {
    private Long id;

    private  String title;
    private Double latitude;
    private Double longitude;


}