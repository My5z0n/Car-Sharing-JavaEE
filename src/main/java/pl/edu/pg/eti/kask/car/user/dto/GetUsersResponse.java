package pl.edu.pg.eti.kask.car.user.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetUsersResponse {


    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class User {


        private Long id;


        private String name;

        private String login;


        private BigDecimal balance;
    }


    @Singular
    private List<User> users;

    public static Function<Collection<pl.edu.pg.eti.kask.car.user.entity.User>, GetUsersResponse> entityToDtoMapper() {
        return users -> {
            GetUsersResponse.GetUsersResponseBuilder response = GetUsersResponse.builder();
            users.stream()
                    .map(user -> User.builder()
                            .id(user.getId())
                            .login(user.getLogin())
                            .name(user.getName())
                            .balance(user.getBalance())
                            .build())
                    .forEach(response::user);
            return response.build();
        };
    }

}
