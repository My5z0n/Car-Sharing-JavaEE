package pl.edu.pg.eti.kask.car.carShare.dto;

import lombok.*;
import pl.edu.pg.eti.kask.car.carShare.entity.CarShare;

import java.math.BigDecimal;
import java.util.Date;
import java.util.function.Function;
import java.util.function.Supplier;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetCarShareResponse {

    private Long id;
    private String car;
    private Date startDate;
    private Date endDate;
    private BigDecimal price;


    public static Function<CarShare, GetCarShareResponse> entityToDtoMapper() {
        return car -> GetCarShareResponse.builder()
                .id(car.getId())
                .car(car.getCar().getPlate())
                .startDate(car.getStartDate())
                .endDate(car.getEndDate())
                .price(car.getPrice())
                .build();
    }


}
