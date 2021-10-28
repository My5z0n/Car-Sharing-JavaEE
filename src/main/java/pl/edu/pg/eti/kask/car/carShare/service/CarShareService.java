package pl.edu.pg.eti.kask.car.carShare.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.car.car.entity.Car;
import pl.edu.pg.eti.kask.car.car.repository.CarRepository;
import pl.edu.pg.eti.kask.car.carShare.entity.CarShare;
import pl.edu.pg.eti.kask.car.carShare.repository.CarShareRepository;
import pl.edu.pg.eti.kask.car.serialization.CloningUtility;
import pl.edu.pg.eti.kask.car.user.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
@NoArgsConstructor//Empty constructor is required for creating proxy while CDI injection.
public class CarShareService {

    /**
     * Repository for character entity.
     */
    private CarShareRepository carShareRepository;
    private CarRepository carRepository;

    /**
     * Repository for user entity.
     */
    private UserRepository userRepository;


    /**
     * @param characterRepository repository for character entity
     * @param userRepository      repository for user entity
     */
    @Inject
    public CarShareService(CarShareRepository characterRepository, UserRepository userRepository, CarRepository carRepository) {
        this.carShareRepository = characterRepository;
        this.userRepository = userRepository;
        this.carRepository= carRepository;
    }

    /**
     * Finds single character.
     *
     * @param id character's id
     * @return container with character
     */

    public Optional<CarShare> find(Long id) {
        return carShareRepository.find(id);
    }

    /**
     * @return all available characters of the authenticated user
     */

    public Optional<CarShare> findForCallerPrincipal(Long id) {
       // return carShareRepository.findByIdAndUser(id, userRepository.find(userContext.getPrincipal()).orElseThrow());
        throw new IllegalArgumentException("Not implemented");
    }

    /**
     * @return all available characters
     */

    public List<CarShare> findAll() {
        return carShareRepository.findAll();
    }

    /**
     * @return all available characters of the authenticated user
     */

    public List<CarShare> findAllByCar(String plate) {
        return carShareRepository.findAllByCars(plate);
    }

    /**
     * Creates new character.
     *
     * @param carShare new character
     */
    @Transactional
    public boolean create(CarShare carShare) {
        var res = carShareRepository.find(carShare.getId());
        if(res.isPresent())
        {
            return false;
        }
        else
        {
            carShareRepository.create(carShare);
            carRepository.find(carShare.getCar().getPlate()).ifPresent(car1 -> car1.getCarSharesXXX().add(carShare));
            return true;
        }
    }



    /**
     * Updates existing character.
     *
     * @param carShare character to be updated
     */
    @Transactional
    public void update(CarShare carShare) {
        carShareRepository.update(carShare);

    }

    /**
     * Deletes existing character.
     *
     * @param carShare existing character's id to be deleted
     */
    @Transactional
    public void delete(Long carShare) {
        var carshar = carShareRepository.find(carShare).orElseThrow();
        carshar.getCar().getCarSharesXXX().remove(carshar);
        carShareRepository.delete(carshar);

    }



    public Optional<CarShare> findAllByCarAndId(String plate, Long id) {
        return carShareRepository.findAllByCarsAndId(plate,id);

    }

    @Transactional
    public void deleteShares(List<CarShare> carShares) {

        for (CarShare carShare : carShares)
        {
            var carshar = carShareRepository.find(carShare.getId()).orElseThrow();
            carshar.getCar().getCarSharesXXX().remove(carshar);
            carShareRepository.delete(carshar);
        }
    }

    @Transactional
    public void clear() {
        carShareRepository.clear();
    }
}

