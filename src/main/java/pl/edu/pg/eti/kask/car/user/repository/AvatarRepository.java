package pl.edu.pg.eti.kask.car.user.repository;

import pl.edu.pg.eti.kask.car.datastore.DataStore;
import pl.edu.pg.eti.kask.car.repository.Repository;
import pl.edu.pg.eti.kask.car.user.entity.Avatar;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.annotation.MultipartConfig;
import java.util.List;
import java.util.Optional;



@Dependent
public class AvatarRepository implements Repository<Avatar, Long> {


   // private DataStore store;
/*
    @Inject
    public AvatarRepository(DataStore store) {
        this.store = store;
    }
*/
   private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Avatar> find(Long id) {
        //return store.findAvatar(id);
        return Optional.ofNullable(em.find(Avatar.class, id));
    }

    @Override
    public List<Avatar> findAll() {
        return null;
    }

    @Override
    public void create(Avatar entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Avatar entity) {
        em.remove(em.find(Avatar.class, entity.getId()));
    }

    @Override
    public void update(Avatar entity) {
        em.merge(entity);
    }
}
