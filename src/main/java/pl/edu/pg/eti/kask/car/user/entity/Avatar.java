package pl.edu.pg.eti.kask.car.user.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Avatar implements Serializable {


    private Long id;

    private Boolean haveAvatar;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private byte[] image;


}
