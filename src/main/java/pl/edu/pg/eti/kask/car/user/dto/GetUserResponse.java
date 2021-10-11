package pl.edu.pg.eti.kask.car.user.dto;

import lombok.*;
import pl.edu.pg.eti.kask.car.location.Location;
import pl.edu.pg.eti.kask.car.user.entity.User;

import java.math.BigDecimal;
import java.util.function.Function;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetUserResponse {


    private String login;


    private String name;


    private BigDecimal balance;


    private Location location;


    public static Function<User, GetUserResponse> entityToDtoMapper() {
        return user -> GetUserResponse.builder()
                .name(user.getName())
                .balance(user.getBalance())
                .location(user.getLocation())
                .login(user.getLogin())
                .build();
    }

}
