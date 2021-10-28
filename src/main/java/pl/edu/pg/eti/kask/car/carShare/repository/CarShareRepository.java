package pl.edu.pg.eti.kask.car.carShare.repository;


import pl.edu.pg.eti.kask.car.car.entity.Car;
import pl.edu.pg.eti.kask.car.carShare.entity.CarShare;
import pl.edu.pg.eti.kask.car.datastore.DataStore;
import pl.edu.pg.eti.kask.car.repository.Repository;
import pl.edu.pg.eti.kask.car.serialization.CloningUtility;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestScoped
public class CarShareRepository implements Repository<CarShare, Long> {

    /**
     * Underlying data store. In future should be replaced with database connection.
     */
    //private DataStore store;

    /**
     * @param store data store
     */
    /*
    @Inject
    public CarShareRepository(DataStore store) {
        this.store = store;
    }
*/
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<CarShare> find(Long id) {
        if (id == null)
        {
            System.out.println("NULL");
            return Optional.empty();
        }
        else {
            return Optional.ofNullable(em.find(CarShare.class, id));
        }


    }

    @Override
    public List<CarShare> findAll() {
        return em.createQuery("select c from CarShare c", CarShare.class).getResultList();
    }

    @Override
    public void create(CarShare entity) {
        em.persist(entity);
        //throw new UnsupportedOperationException("Operation not implemented.");
    }

    @Override
    public void delete(CarShare entity) {
        em.remove(em.find(CarShare.class, entity.getId()));
    }

    @Override
    public void update(CarShare entity) {
        em.merge(entity);
    }


 /*   public Optional<CarShare> findByIdAndUser(Long id, User user) {
        return store.getCharacterStream()
                .filter(character -> character.getUser().equals(user))
                .filter(character -> character.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }
*/

    public List<CarShare> findAllByCars(String plate) {
        var aa = Optional.ofNullable(em.find(Car.class, plate));
        if (aa.isPresent()){
            return em.createQuery("select c from CarShare c where c.car = :car", CarShare.class)
                    .setParameter("car", aa.get())
                    .getResultList();
        }
        else {
            return new ArrayList<CarShare>();
        }


    }

    public Optional<CarShare> findAllByCarsAndId(String plate, Long id) {
        var aa = Optional.ofNullable(em.find(Car.class, plate));
        if (aa.isPresent()){
            return em.createQuery("select c from CarShare c where c.car = :car and c.id = :id", CarShare.class)
                    .setParameter("car", aa.get())
                    .setParameter("id", id)
                    .getResultList().stream().findFirst();
        }
        else {
            return Optional.empty();
        }

    }

    public void clear() {
        em.clear();
    }
}
