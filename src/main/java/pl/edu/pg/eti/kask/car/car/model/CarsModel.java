package pl.edu.pg.eti.kask.car.car.model;

import lombok.*;

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
public class CarsModel {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Car {

        /**
         * Unique id identifying character.
         */
        private String plate;

        /**
         * Name of the character.
         */
        private String brandName;


    }
    @Singular
    private List<Car> cars;

    public static Function<Collection<pl.edu.pg.eti.kask.car.car.entity.Car>, CarsModel> entityToModelMapper() {
        return cars -> {
            CarsModel.CarsModelBuilder model = CarsModel.builder();
            cars.stream()
                    .map(car -> Car.builder()
                            .plate(car.getPlate())
                            .brandName(car.getBrandName())
                            .build())
                    .forEach(model::car);
            return model.build();
        };
    }
}
