package pl.edu.pg.eti.kask.car.user.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.car.location.Location;
import pl.edu.pg.eti.kask.car.role.Role;

import java.io.Serializable;
import java.math.BigDecimal;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class User implements Serializable {


    private Long id;

    private String login;


    private String name;


    private BigDecimal balance;


    private Location location;


    private Role role;


    @ToString.Exclude
    private String password;


}
