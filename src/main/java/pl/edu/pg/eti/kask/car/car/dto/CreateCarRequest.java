package pl.edu.pg.eti.kask.car.car.dto;


import lombok.*;
import pl.edu.pg.eti.kask.car.car.entity.Car;
import pl.edu.pg.eti.kask.car.car.model.CarModel;

import java.math.BigDecimal;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateCarRequest {
    private String plate;


    private String brandName;
    private String model;
    private int Year;
    private BigDecimal costPerMinute;
    private Boolean availability;

    public static Function<CreateCarRequest, Car> dtoToEntityMapper() {
        return request -> Car.builder()
                .plate(request.getPlate())
                .model(request.getModel())
                .brandName(request.getBrandName())
                .Year(request.getYear())
                .costPerMinute(request.getCostPerMinute())
                .availability(request.getAvailability())
                .build();
    }
}
