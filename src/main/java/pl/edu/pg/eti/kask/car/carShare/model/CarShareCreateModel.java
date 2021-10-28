package pl.edu.pg.eti.kask.car.carShare.model;

import lombok.*;
import pl.edu.pg.eti.kask.car.car.entity.Car;
import pl.edu.pg.eti.kask.car.car.model.CarModel;
import pl.edu.pg.eti.kask.car.carShare.entity.CarShare;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.function.Function;

/**
 * JSF view model class in order to not to use entity classes. Represents new character to be created. Includes oll
 * fields which can be use in character creation.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CarShareCreateModel {


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


    public static Function<CarShareCreateModel, CarShare> modelToEntityMapper(
            Function<String, Car> carFunction) {
        return model -> CarShare.builder()
                .price(model.getPrice())
                .startDate(model.getStartDate())
                .endDate(model.getEndDate())
                .car(carFunction.apply(model.getCar().getPlate()))
                .build();
    }

}
