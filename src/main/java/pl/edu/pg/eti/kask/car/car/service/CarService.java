package pl.edu.pg.eti.kask.car.car.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.car.car.entity.Car;
import pl.edu.pg.eti.kask.car.car.repository.CarRepository;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for all business actions regarding character's profession entity.
 */
@ApplicationScoped
@NoArgsConstructor//Empty constructor is required for creating proxy while CDI injection.
public class CarService {

    /**
     * Repository for profession entity.
     */
    private CarRepository repository;

    /**
     * @param repository repository for profession entity
     */
    @Inject
    public CarService(CarRepository repository) {
        this.repository = repository;
    }

    /**
     * @param name name of the profession
     * @return container with profession entity
     */
    public Optional<Car> find(String name) {
        return repository.find(name);
    }

    public List<Car> findAll() {
        return repository.findAll();
    }

    public void delete(String car) {
        repository.delete(repository.find(car).orElseThrow());
    }

    /**
     * Stores new profession in the data store.
     *
     * @param profession new profession to be saved
     */
    public boolean create(Car car) {
        var res = repository.find(car.getPlate());
        if(res.isPresent())
        {
            return false;
        }
        else
        {
            repository.create(car);
            return true;
        }

    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public void update(Car car) {
        repository.update(car);
    }
}
