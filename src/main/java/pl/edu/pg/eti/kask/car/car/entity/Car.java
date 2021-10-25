package pl.edu.pg.eti.kask.car.car.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.car.location.Location;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode()
public class Car implements Serializable {
    //ID
    String plate;


    String brandName;
    String model;
    int Year;
    BigDecimal costPerMinute;
    Boolean availability;

}
