package pl.edu.pg.eti.kask.car.user.dto;

import lombok.*;
import pl.edu.pg.eti.kask.car.user.entity.User;

import java.util.function.BiFunction;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdatePasswordRequest {


    private String password;


    public static BiFunction<User, UpdatePasswordRequest, User> dtoToEntityUpdater() {
        return (user, request) -> {
            user.setPassword(request.getPassword());
            return user;
        };
    }

}
