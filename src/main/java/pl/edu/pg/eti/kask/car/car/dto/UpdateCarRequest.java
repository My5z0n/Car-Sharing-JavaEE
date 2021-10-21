package pl.edu.pg.eti.kask.car.car.dto;


import lombok.*;
import pl.edu.pg.eti.kask.car.car.entity.Car;

import java.math.BigDecimal;
import java.util.function.BiFunction;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateCarRequest {



    private String brandName;
    private String model;
    private int Year;
    private BigDecimal costPerMinute;
    private Boolean availability;


    public static BiFunction<Car, UpdateCarRequest, Car> dtoToEntityUpdater() {
        return (car, request) -> {
            car.setCostPerMinute(request.getCostPerMinute());
            car.setAvailability(request.getAvailability());
            return car;
        };
    }

}
