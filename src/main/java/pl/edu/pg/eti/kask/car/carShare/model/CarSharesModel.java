package pl.edu.pg.eti.kask.car.carShare.model;

import lombok.*;
import pl.edu.pg.eti.kask.car.carShare.entity.CarShare;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Function;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CarSharesModel {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class CarShare {

        private Long id;
        private String car;



    }


    @Singular
    private List<CarShare> carShares;
    public static Function<Collection<pl.edu.pg.eti.kask.car.carShare.entity.CarShare>, CarSharesModel> entityToModelMapper() {
        return carShares -> {
            CarSharesModel.CarSharesModelBuilder model = CarSharesModel.builder();
            carShares.stream()
                    .map(carShare -> CarShare.builder()
                            .id(carShare.getId())
                            .car(carShare.getCar().getPlate())
                            .build())
                    .forEach(model::carShare);
            return model.build();
        };
    }
}
