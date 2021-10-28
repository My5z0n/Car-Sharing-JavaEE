package pl.edu.pg.eti.kask.car.carShare.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.car.car.entity.Car;

import javax.persistence.*;
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
@Entity
@Table(name="CarShare")
public class CarShare implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "CAR_PLATE")
    private Car car;

    private LocalDate startDate;

    private LocalDate  endDate;

    private BigDecimal price;
}
