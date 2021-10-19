package pl.edu.pg.eti.kask.car.carShare.repository;


import pl.edu.pg.eti.kask.car.car.entity.Car;
import pl.edu.pg.eti.kask.car.carShare.entity.CarShare;
import pl.edu.pg.eti.kask.car.datastore.DataStore;
import pl.edu.pg.eti.kask.car.repository.Repository;
import pl.edu.pg.eti.kask.car.serialization.CloningUtility;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Dependent
public class CarShareRepository implements Repository<CarShare, Long> {

    /**
     * Underlying data store. In future should be replaced with database connection.
     */
    private DataStore store;

    /**
     * @param store data store
     */
    @Inject
    public CarShareRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<CarShare> find(Long id) {
        return store.findCarShare(id);
    }

    @Override
    public List<CarShare> findAll() {
        return store.findAllCarShares();
    }

    @Override
    public void create(CarShare entity) {
        store.createCarShare(entity);
        //throw new UnsupportedOperationException("Operation not implemented.");
    }

    @Override
    public void delete(CarShare entity) {
        store.deleteCarShare(entity.getId());
    }

    @Override
    public void update(CarShare entity) {
        store.updateCarShare(entity);
    }

    /**
     * Seeks for single user's character.
     *
     * @param id   character's id
     * @param user characters's owner
     * @return container (can be empty) with character
     */
 /*   public Optional<CarShare> findByIdAndUser(Long id, User user) {
        return store.getCharacterStream()
                .filter(character -> character.getUser().equals(user))
                .filter(character -> character.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }
*/
    /**
     * Seeks for all user's characters.
     *
     * @param car characters' owner
     * @return list (can be empty) of user's characters
     */
    public List<CarShare> findAllByCars(String plate) {
        return store.getCarShareStream()
                .filter(carShare -> carShare.getCar().getPlate().equals(plate))
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }

}
