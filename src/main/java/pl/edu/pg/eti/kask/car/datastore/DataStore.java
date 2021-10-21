package pl.edu.pg.eti.kask.car.datastore;

import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.car.car.entity.Car;
import pl.edu.pg.eti.kask.car.carShare.entity.CarShare;
import pl.edu.pg.eti.kask.car.serialization.CloningUtility;
import pl.edu.pg.eti.kask.car.user.entity.Avatar;
import pl.edu.pg.eti.kask.car.user.entity.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import javax.inject.Inject;
import io.xlate.inject.Property;
import io.xlate.inject.PropertyResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * For the sake of simplification instead of using real database this example is using an data source object which
 * should be put in servlet context in a single instance. In order to avoid
 * {@link java.util.ConcurrentModificationException} all methods are synchronized. Normally synchronization would be
 * carried on by the database server.
 */
@Log
@ApplicationScoped

public class DataStore {


    @Inject
    @Property(resolveEnvironment = true,
            resource = @PropertyResource("classpath:/app.properties"))
        private  String DATA_SAVE ;


    /**
     * Set of all users.
     */
    private Set<User> users = new HashSet<>();

    private Set<Avatar> avatars = new HashSet<>();

    private Set<Car> cars = new HashSet<>();

    private Set<CarShare> carShares = new HashSet<>();

    /**
     * Seeks for single user.
     *
     * @param login user's login
     * @return container (can be empty) with user
     */
    public synchronized Optional<User> findUser(String login) {
        return users.stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized Optional<User> findUser(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }

    /**
     * Seeks for all users.
     *
     * @return collection of all users
     */
    public synchronized List<User> findAllUsers() {
        return users.stream()
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }

    /**
     * Stores new user.
     *
     * @param user new user to be stored
     * @throws IllegalArgumentException if user with provided login already exists
     */
    public synchronized void createUser(User user) throws IllegalArgumentException {
        findUser(user.getLogin()).ifPresentOrElse(
                original -> {
                    throw new IllegalArgumentException(
                            String.format("The user login \"%s\" is not unique", user.getLogin()));
                },
                () -> {
                    user.setId(findAllUsers().stream()
                            .mapToLong(User::getId)
                            .max().orElse(0) + 1);
                    users.add(CloningUtility.clone(user));
                });
    }

    public synchronized void deleteUser(Long id) throws IllegalArgumentException {
        findUser(id).ifPresentOrElse(
                original -> users.remove(original),
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The user with id \"%d\" does not exist", id));
                });
    }

    public synchronized void updateUser(User user) throws IllegalArgumentException {
        findUser(user.getId()).ifPresentOrElse(
                original -> {
                    users.remove(original);
                    users.add(CloningUtility.clone(user));
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The user with id \"%d\" does not exist", user.getId()));
                });
    }

    public byte[] getAvatarBytes(Long id) {
        System.out.println(DATA_SAVE);
        Path filePath = Paths.get(DATA_SAVE + id.toString() + ".png");
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public void setAvatarBytes(Long id, byte[] bytes) {
        Path filePath = Paths.get(DATA_SAVE + id.toString() + ".png");
        try {
            Files.write(filePath, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delAvatarBytes(Long id) {
        Path filePath = Paths.get(DATA_SAVE + id.toString() + ".png");
        try {
            Files.delete(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized Optional<Avatar> findAvatar(Long id) {
        var retAvatar = avatars.stream()
                .filter(avatar -> avatar.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
        retAvatar.ifPresent(avatar ->
                avatar.setImage(CloningUtility.clone(getAvatarBytes(id)))
        );
        return retAvatar;
    }

    public synchronized void createAvatar(Avatar avatar) throws IllegalArgumentException {
        var bytes = CloningUtility.clone(avatar.getImage());
        setAvatarBytes(avatar.getId(), bytes);
        avatar.setImage(new byte[0]);
        avatars.add(avatar);
    }

    public synchronized void deleteAvatar(Long id) throws IllegalArgumentException {
        findAvatar(id).ifPresentOrElse(
                original -> {
                    avatars.remove(original);
                    delAvatarBytes(original.getId());
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The avatar with id \"%d\" does not exist", id));
                });
    }

    public synchronized void updateAvatar(Avatar avatar) throws IllegalArgumentException {
        findAvatar(avatar.getId()).ifPresentOrElse(
                original -> {
                    var bytes = CloningUtility.clone(avatar.getImage());
                    delAvatarBytes(original.getId());
                    setAvatarBytes(original.getId(), bytes);
                    avatars.remove(original);
                    avatar.setImage(new byte[0]);
                    avatars.add(CloningUtility.clone(avatar));
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The avatar with id \"%d\" does not exist", avatar.getId()));
                });
    }

    public synchronized List<Car> findAllCars() {
        return cars.stream().map(CloningUtility::clone).collect(Collectors.toList());
    }

    public synchronized Optional<Car> findCar(String id) {
        return cars.stream()
                .filter(character -> character.getPlate().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized Optional<CarShare> findCarShare(Long id) {
        return carShares.stream()
                .filter(carShare -> carShare.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized List<CarShare> findAllCarShares() {
        return carShares.stream().map(CloningUtility::clone).collect(Collectors.toList());
    }

    public synchronized void deleteCarShare(Long id) {
        findCarShare(id).ifPresentOrElse(
                original -> carShares.remove(original),
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The Carshare with id \"%d\" does not exist", id));
                });
    }

    public synchronized void updateCarShare(CarShare entity) {
        findCarShare(entity.getId()).ifPresentOrElse(
                original -> {
                    carShares.remove(original);
                    carShares.add(entity);
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The carShare with id \"%d\" does not exist", entity.getId()));
                });
    }

    public Stream<CarShare> getCarShareStream() {
        return carShares.stream();
    }

    public synchronized void deleteCar(String id) throws IllegalArgumentException {
            var liest = getCarShareStream()
                    .filter(carShare -> carShare.getCar().getPlate().equals(id))
                    .map(CloningUtility::clone)
                    .collect(Collectors.toList());
        for (CarShare carShares:liest) {
            deleteCarShare(carShares.getId());
        }

        findCar(id).ifPresentOrElse(
                original -> cars.remove(original),
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The car with id \"%s\" does not exist", id));
                });
    }
    public synchronized void createCar(Car car) throws IllegalArgumentException {
        cars.add(car);
    }

    public synchronized void createCarShare(CarShare carShare) throws IllegalArgumentException {
        carShare.setId(findAllCarShares().stream().mapToLong(CarShare::getId).max().orElse(0) + 1);
        carShares.add(carShare);
    }

    public void deleteAllCars() {
        var carlist = this.findAllCars();
        for (Car car: carlist){
            deleteCar(car.getPlate());
        }
    }

    public void updateCar(Car entity) {
        findCar(entity.getPlate()).ifPresentOrElse(
                original -> {
                    cars.remove(original);
                    cars.add(CloningUtility.clone(entity));
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The user with id \"%s\" does not exist", entity.getPlate()));
                });
    }
}
