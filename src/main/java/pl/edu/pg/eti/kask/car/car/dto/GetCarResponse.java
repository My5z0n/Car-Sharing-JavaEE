package pl.edu.pg.eti.kask.car.car.dto;


import lombok.*;
import pl.edu.pg.eti.kask.car.car.entity.Car;
import pl.edu.pg.eti.kask.car.car.model.CarModel;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetCarResponse {
    private String plate;


    private String brandName;
    private String model;
    private int Year;
    private BigDecimal costPerMinute;
    private Boolean availability;

    public static Function<Car, GetCarResponse> entityToDtoMapper() {
        return car -> GetCarResponse.builder()
                .plate(car.getPlate())
                .model(car.getModel())
                .brandName(car.getBrandName())
                .Year(car.getYear())
                .costPerMinute(car.getCostPerMinute())
                .availability(car.getAvailability())
                .build();
    }
}