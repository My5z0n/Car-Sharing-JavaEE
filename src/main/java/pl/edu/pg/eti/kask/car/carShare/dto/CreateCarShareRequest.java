package pl.edu.pg.eti.kask.car.carShare.dto;


import lombok.*;
import pl.edu.pg.eti.kask.car.car.entity.Car;
import pl.edu.pg.eti.kask.car.car.model.CarModel;
import pl.edu.pg.eti.kask.car.carShare.entity.CarShare;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateCarShareRequest {

    /**
     * Character's strength.
     */
    private BigDecimal price;

    /**
     * Character's constitution.
     */
    private LocalDate startDate;

    /**
     * Character's charisma.
     */
    private LocalDate endDate;

    /**
     * Character's profession.
     */
    private CarModel car;


    public static Function<CreateCarShareRequest, CarShare> dtoToEntityMapper() {
        return request -> CarShare.builder()
                .price(request.getPrice())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
              //  .Car(carFunction.apply(request.getCar().getPlate()))
                .build();
    }
}
