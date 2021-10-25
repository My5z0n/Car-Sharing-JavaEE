package pl.edu.pg.eti.kask.car.user.repository;

import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.car.datastore.DataStore;
import pl.edu.pg.eti.kask.car.repository.Repository;
import pl.edu.pg.eti.kask.car.user.entity.User;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@RequestScoped
@Log
public class UserRepository implements Repository<User, Long> {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }
  //  private DataStore store;

   // @Inject
    //public UserRepository(DataStore store) {
   //     this.store = store;
   // }

   // public Optional<User> find(String login) {
   //     return Optional.ofNullable(em.find(User.class, id));
  //  }

    @Override
    public Optional<User> find(Long id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public void create(User entity) {
        em.persist(entity);
    }

    @Override
    public void delete(User entity) {
        em.remove(em.find(User.class, entity.getLogin()));
    }

    @Override
    public void update(User entity) {
        em.merge(entity);
    }

    public void detach(User entity) {
        em.detach(entity);
    }


   /*
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return store.findAllUsers().stream()
                .filter(user -> user.getLogin().equals(login) && user.getPassword().equals(password))
                .findFirst();
    }
*/
}
