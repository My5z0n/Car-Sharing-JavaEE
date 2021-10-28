package pl.edu.pg.eti.kask.car.car.entity;

import javax.persistence.*;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.car.carShare.entity.CarShare;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode()
@Entity
@Table(name="Car")
public class Car implements Serializable {
    //ID
    @Id
    @Column(name="CAR_PLATE")
    String plate;


    String brandName;
    String model;
    int Year;
    BigDecimal costPerMinute;
    Boolean availability;



    @ToString.Exclude//It's common to exclude lists from toString
    @EqualsAndHashCode.Exclude
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "car",
            cascade = {CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<CarShare> CarSharesXXX;

}
