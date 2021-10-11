package pl.edu.pg.eti.kask.car.user.dto;

import lombok.*;
import pl.edu.pg.eti.kask.car.location.Location;
import pl.edu.pg.eti.kask.car.role.Role;
import pl.edu.pg.eti.kask.car.user.entity.User;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * PSOT user request. Contains only fields that can be set during user creation. User is defined in
 * {@link pl.edu.pg.eti.kask.car.user.entity.User}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateUserRequest {


    private String login;


    private String name;


    private BigDecimal balance;


    private Location location;


    private String password;


    private Role role;

    public static Function<CreateUserRequest, User> dtoToEntityMapper() {
        return request -> User.builder()
                .login(request.getLogin())
                .name(request.getName())
                .location(request.getLocation())
                .balance(request.getBalance())
                .password(request.getPassword())
                .role(request.getRole())
                .build();
    }

}
