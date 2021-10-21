package pl.edu.pg.eti.kask.car.car.dto;

import lombok.*;
import pl.edu.pg.eti.kask.car.car.entity.Car;
import pl.edu.pg.eti.kask.car.car.model.CarsModel;

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
public class GetCarsResponse {

    @Singular
    private List<String> cars;

    public static Function<Collection<Car>, GetCarsResponse> entityToDtoMapper() {
        return cars -> {
            GetCarsResponse.GetCarsResponseBuilder response = GetCarsResponse.builder();
            cars.stream()
                    .map(Car::getPlate)
                    .forEach(response::car);
            return response.build();
        };
    }
}
