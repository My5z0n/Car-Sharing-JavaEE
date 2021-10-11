package pl.edu.pg.eti.kask.car.configuration;

import lombok.SneakyThrows;
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


    @Inject
    public InitializedData(UserService userService,AvatarService avatarService) {
        this.avatarService = avatarService;
        this.userService = userService;
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
