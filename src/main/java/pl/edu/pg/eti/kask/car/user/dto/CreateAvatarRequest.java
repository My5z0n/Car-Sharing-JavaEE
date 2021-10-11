package pl.edu.pg.eti.kask.car.user.dto;

import lombok.*;
import pl.edu.pg.eti.kask.car.user.entity.Avatar;

import java.util.function.Function;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateAvatarRequest {


    private Long id;

    private byte[] image;

    public static Function<CreateAvatarRequest, Avatar> dtoToEntityMapper() {
        return request -> Avatar.builder()
                .id(request.getId())
                .image(request.getImage())
                .build();
    }

}
