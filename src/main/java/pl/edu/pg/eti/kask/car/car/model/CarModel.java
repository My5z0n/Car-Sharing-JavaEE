package pl.edu.pg.eti.kask.car.car.model;

import lombok.*;
import pl.edu.pg.eti.kask.car.car.entity.Car;
import pl.edu.pg.eti.kask.car.location.Location;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CarModel {

    private String plate;


    private String brandName;
    private String model;
    private int Year;
    private BigDecimal costPerMinute;
    private Boolean availability;

    public static Function<Car, CarModel> entityToModelMapper() {
        return car -> CarModel.builder()
                .plate(car.getPlate())
                .model(car.getModel())
                .brandName(car.getBrandName())
                .Year(car.getYear())
                .costPerMinute(car.getCostPerMinute())
                .availability(car.getAvailability())
                .build();
    }
}
