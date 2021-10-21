package pl.edu.pg.eti.kask.car.car.repository;


import pl.edu.pg.eti.kask.car.car.entity.Car;
import pl.edu.pg.eti.kask.car.datastore.DataStore;
import pl.edu.pg.eti.kask.car.repository.Repository;


import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Repository for profession entity. Repositories should be used in business layer (e.g.: in services).
 */
@Dependent
public class CarRepository implements Repository<Car, String> {

    /**
     * Underlying data store. In future should be replaced with database connection.
     */
    private DataStore store;

    /**
     * @param store data store
     */
    @Inject
    public CarRepository(DataStore store) {
        this.store = store;
    }


    @Override
    public Optional<Car> find(String id) {
        return store.findCar(id);
    }

    @Override
    public List<Car> findAll() {
        return store.findAllCars();
    }

    @Override
    public void create(Car entity) {
        store.createCar(entity);
    }

    @Override
    public void delete(Car entity) {
        store.deleteCar(entity.getPlate());
    }

    @Override
    public void update(Car entity) {
        store.updateCar(entity);
    }


    public void deleteAll() {
        store.deleteAllCars();
    }
}
