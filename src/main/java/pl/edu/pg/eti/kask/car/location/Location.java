package pl.edu.pg.eti.kask.car.location;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Location implements Serializable {

    public Location() {
        this.xCord = 0;
        this.yCord = 0;
    }

    public Location(Integer xCord, Integer yCord) {
        this.xCord = xCord;
        this.yCord = yCord;
    }

    private Integer xCord;


    private Integer yCord;


}
