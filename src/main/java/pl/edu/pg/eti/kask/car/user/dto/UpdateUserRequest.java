package pl.edu.pg.eti.kask.car.user.dto;

import lombok.*;
import pl.edu.pg.eti.kask.car.location.Location;
import pl.edu.pg.eti.kask.car.user.entity.User;

import java.util.function.BiFunction;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateUserRequest {


    private String name;


    private Location location;


    public static BiFunction<User, UpdateUserRequest, User> dtoToEntityUpdater() {
        return (user, request) -> {
            user.setName(request.getName());
            user.setLocation(request.getLocation());
            return user;
        };
    }

}
