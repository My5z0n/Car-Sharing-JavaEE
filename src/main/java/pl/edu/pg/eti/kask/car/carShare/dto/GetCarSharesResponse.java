package pl.edu.pg.eti.kask.car.carShare.dto;

import lombok.*;
import pl.edu.pg.eti.kask.car.carShare.entity.CarShare;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetCarSharesResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class CarShare {
        private Long id;
        private LocalDate startDate;
    }

    @Singular
    private List<CarShare> carShares;

    public static Function<Collection<pl.edu.pg.eti.kask.car.carShare.entity.CarShare>, GetCarSharesResponse> entityToDtoMapper() {
        return carShares -> {
            GetCarSharesResponseBuilder response = GetCarSharesResponse.builder();
            carShares.stream()
                    .map(carShare -> CarShare.builder()
                            .id(carShare.getId())
                            .startDate(carShare.getStartDate())
                            .build())
                    .forEach(response::carShare);
            return response.build();
        };
    }

}
