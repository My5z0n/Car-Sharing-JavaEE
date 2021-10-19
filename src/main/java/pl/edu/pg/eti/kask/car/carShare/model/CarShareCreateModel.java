package pl.edu.pg.eti.kask.car.carShare.model;

import lombok.*;
import pl.edu.pg.eti.kask.car.car.entity.Car;
import pl.edu.pg.eti.kask.car.car.model.CarModel;
import pl.edu.pg.eti.kask.car.carShare.entity.CarShare;
import pl.edu.pg.eti.kask.car.user.entity.User;


import java.math.BigDecimal;
import java.util.Date;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

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
    private Date startDate;

    /**
     * Character's charisma.
     */
    private Date endDate;

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
                .Car(carFunction.apply(model.getCar().getPlate()))
                .build();
    }

}
