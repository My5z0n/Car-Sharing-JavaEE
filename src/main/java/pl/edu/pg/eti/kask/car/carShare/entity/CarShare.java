package pl.edu.pg.eti.kask.car.carShare.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.car.car.entity.Car;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CarShare implements Serializable {
    private Long id;
    private Car Car;
    private LocalDate startDate;
    private LocalDate  endDate;
    private BigDecimal price;
}
