package pl.edu.pg.eti.kask.car.configuration;

import lombok.SneakyThrows;
import pl.edu.pg.eti.kask.car.car.entity.Car;
import pl.edu.pg.eti.kask.car.car.service.CarService;
import pl.edu.pg.eti.kask.car.carShare.entity.CarShare;
import pl.edu.pg.eti.kask.car.carShare.service.CarShareService;
import pl.edu.pg.eti.kask.car.digest.Sha256Utility;
import pl.edu.pg.eti.kask.car.location.Location;
import pl.edu.pg.eti.kask.car.role.Role;
import pl.edu.pg.eti.kask.car.user.entity.Avatar;
import pl.edu.pg.eti.kask.car.user.entity.User;
import pl.edu.pg.eti.kask.car.user.service.AvatarService;
import pl.edu.pg.eti.kask.car.user.service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Listener started automatically on CDI application context initialized. Injects proxy to the services and fills
 * database with default content. When using persistence storage application instance should be initialized only during
 * first run in order to init database with starting data. Good place to create first default admin user.
 */
@ApplicationScoped
public class InitializedData {


    /**
     * Service for users operations.
     */
    private final UserService userService;
    private final AvatarService avatarService;
    private final CarService carService;
    private final CarShareService carShareService;


    @Inject
    public InitializedData(UserService userService, AvatarService avatarService, CarService carService, CarShareService carShareService) {
        this.avatarService = avatarService;
        this.userService = userService;
        this.carService = carService;
        this.carShareService = carShareService;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    /**
     * Initializes database with some example values. Should be called after creating this object. This object should
     * be created only once.
     */
    private synchronized void init() {

        User admin = User.builder()
                .login("adamin1")
                .name("Adam")
                .location(new Location(25, 25))
                .password(Sha256Utility.hash("adminadmin"))
                .balance(new BigDecimal("21.37"))
                .role(Role.ADMIN)
                .build();

        User stephann = User.builder()
                .login("user1")
                .name("Stephan")
                .location(new Location(0, -100))
                .password(Sha256Utility.hash("user1user1"))
                .balance(new BigDecimal("621"))
                .role(Role.USER)
                .build();
        User anton = User.builder()
                .login("SUPERUSER")
                .name("Anton")
                .location(new Location(1, 1))
                .password(Sha256Utility.hash("xxx_xxx_xxx"))
                .balance(new BigDecimal("0"))
                .role(Role.USER)
                .build();
        User dima = User.builder()
                .login("my_login_2")
                .name("Dima")
                .location(new Location(9999, 9999))
                .password(Sha256Utility.hash("123456789"))
                .balance(new BigDecimal("2137"))
                .role(Role.USER)
                .build();

        userService.create(admin);
        userService.create(stephann);
        userService.create(anton);
        userService.create(dima);

        Avatar avatar1 = Avatar.builder()
                .id(1L)
                .image(getResourceAsByteArray("avatar/b.png"))
                .build();
        avatarService.create(avatar1);

        Car car1 = Car.builder()
                .plate("GKA123")
                .availability(Boolean.FALSE)
                .brandName("Toyota")
                .model("Supra")
                .costPerMinute(new BigDecimal(22))
                .Year(2005)
                .Location(new Location(11, 11))
                .build();

        Car car2 = Car.builder()
                .plate("ABC3333")
                .availability(Boolean.FALSE)
                .brandName("Skoda")
                .model("Fabia")
                .costPerMinute(new BigDecimal("33.1"))
                .Year(2008)
                .Location(new Location(99, 99))
                .build();

        Car car3 = Car.builder()
                .plate("ZAZA6647")
                .availability(Boolean.TRUE)
                .brandName("Honda")
                .model("Civic")
                .costPerMinute(new BigDecimal(1))
                .Year(2020)
                .Location(new Location(33, -12))
                .build();

        carService.create(car1);
        carService.create(car2);
        carService.create(car3);
        var date = java.util.Calendar.getInstance().getTime();
        //System.out.println(date.getTime());
        CarShare carshare1 = CarShare.builder()
                .Car(car1)
                .endDate( new GregorianCalendar(2014, Calendar.FEBRUARY, 18).getTime())
                .startDate(new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime())
                .price(new BigDecimal("11.01"))
                .build();
        CarShare carshare2 = CarShare.builder()
                .Car(car1)
                .endDate(new GregorianCalendar(2018, Calendar.AUGUST, 22).getTime())
                .startDate(new GregorianCalendar(2018, Calendar.AUGUST, 9).getTime())
                .price(new BigDecimal("22.01"))
                .build();
        CarShare carshare3 = CarShare.builder()
                .Car(car2)
                .endDate(new GregorianCalendar(2020, Calendar.AUGUST, 4).getTime())
                .startDate(new GregorianCalendar(2020, Calendar.MARCH, 3).getTime())
                .price(new BigDecimal("6666.01"))
                .build();

        carShareService.create(carshare1);
        carShareService.create(carshare2);
        carShareService.create(carshare3);
    }

    /**
     * @param name name of the desired resource
     * @return array of bytes read from the resource
     */
    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            return is.readAllBytes();
        }
    }

}
