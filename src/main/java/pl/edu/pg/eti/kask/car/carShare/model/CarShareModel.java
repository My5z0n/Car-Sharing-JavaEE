package pl.edu.pg.eti.kask.car.carShare.model;

import lombok.*;
import pl.edu.pg.eti.kask.car.carShare.entity.CarShare;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.function.Function;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CarShareModel {
    private Long id;
    private String car;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal price;


    public static Function<CarShare, CarShareModel> entityToModelMapper() {
        return carShare -> CarShareModel.builder()
                .id(carShare.getId())
                .car(carShare.getCar().getPlate())
                .startDate(carShare.getStartDate())
                .endDate(carShare.getEndDate())
                .price(carShare.getPrice())
                .build();
    }
}
