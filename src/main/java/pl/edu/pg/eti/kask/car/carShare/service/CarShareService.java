package pl.edu.pg.eti.kask.car.carShare.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.car.car.entity.Car;
import pl.edu.pg.eti.kask.car.carShare.entity.CarShare;
import pl.edu.pg.eti.kask.car.carShare.repository.CarShareRepository;
import pl.edu.pg.eti.kask.car.user.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
@NoArgsConstructor//Empty constructor is required for creating proxy while CDI injection.
public class CarShareService {

    /**
     * Repository for character entity.
     */
    private CarShareRepository carShareRepository;

    /**
     * Repository for user entity.
     */
    private UserRepository userRepository;


    /**
     * @param characterRepository repository for character entity
     * @param userRepository      repository for user entity
     */
    @Inject
    public CarShareService(CarShareRepository characterRepository, UserRepository userRepository) {
        this.carShareRepository = characterRepository;
        this.userRepository = userRepository;
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
    public void create(CarShare carShare) {
        carShareRepository.create(carShare);
    }



    /**
     * Updates existing character.
     *
     * @param carShare character to be updated
     */
    public void update(CarShare carShare) {
        carShareRepository.update(carShare);
    }

    /**
     * Deletes existing character.
     *
     * @param carShare existing character's id to be deleted
     */
    public void delete(Long carShare) {
        carShareRepository.delete(carShareRepository.find(carShare).orElseThrow());
    }


}
