package pl.edu.pg.eti.kask.car.carShare.model;

import lombok.*;
import pl.edu.pg.eti.kask.car.carShare.entity.CarShare;

import java.math.BigDecimal;
import java.util.Date;
import java.util.function.BiFunction;
import java.util.function.Function;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CarShareEditModel {
    private Date startDate;
    private Date endDate;
    private BigDecimal price;


    public static Function<CarShare, CarShareEditModel> entityToModelMapper() {
        return carShare -> CarShareEditModel.builder()
                .startDate(carShare.getStartDate())
                .endDate(carShare.getEndDate())
                .price(carShare.getPrice())
                .build();
    }
    public static BiFunction<CarShare, CarShareEditModel, CarShare> modelToEntityUpdater() {
        return (carShare, request) -> {
            carShare.setPrice(request.getPrice());
            carShare.setStartDate(request.getStartDate());
            carShare.setEndDate(request.getEndDate());
            return carShare;
        };
    }
}
