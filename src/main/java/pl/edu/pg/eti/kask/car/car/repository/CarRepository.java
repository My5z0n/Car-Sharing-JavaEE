package pl.edu.pg.eti.kask.car.car.repository;


import pl.edu.pg.eti.kask.car.car.entity.Car;
import pl.edu.pg.eti.kask.car.datastore.DataStore;
import pl.edu.pg.eti.kask.car.repository.Repository;


import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Repository for profession entity. Repositories should be used in business layer (e.g.: in services).
 */
@RequestScoped
public class CarRepository implements Repository<Car, String> {

    /**
     * Underlying data store. In future should be replaced with database connection.
     */
   // private DataStore store;

    /**
     * @param store data store
     */
    /*
    @Inject
    public CarRepository(DataStore store) {
        this.store = store;
    }
*/
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }


    @Override
    public Optional<Car> find(String id) {
        return Optional.ofNullable(em.find(Car.class, id));
    }

    @Override
    public List<Car> findAll() {
        return em.createQuery("select cc from Car cc", Car.class).getResultList();
    }

    @Override
    public void create(Car entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Car entity) {
        var aa = em.find(Car.class, entity.getPlate());
        em.remove(aa);
    }

    @Override
    public void update(Car entity) {
        em.merge(entity);
    }


    public void deleteAll() {
        em.createQuery("delete FROM Car c", Car.class);
    }
}
