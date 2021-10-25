package pl.edu.pg.eti.kask.car.carShare.dto;


import lombok.*;
import pl.edu.pg.eti.kask.car.car.model.CarModel;
import pl.edu.pg.eti.kask.car.carShare.entity.CarShare;

import java.math.BigDecimal;
import java.time.LocalDate;
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
public class UpdateCarShareRequest {

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



    public static BiFunction<CarShare, UpdateCarShareRequest, CarShare> dtoToEntityUpdater() {
        return (carShare, request) -> {
            carShare.setPrice(request.getPrice());
            carShare.setStartDate(request.getStartDate());
            carShare.setEndDate(request.getEndDate());
            return carShare;
        };
    }
}
