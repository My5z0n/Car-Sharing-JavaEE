package pl.edu.pg.eti.kask.car.user.repository;

import pl.edu.pg.eti.kask.car.datastore.DataStore;
import pl.edu.pg.eti.kask.car.repository.Repository;
import pl.edu.pg.eti.kask.car.user.entity.Avatar;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.servlet.annotation.MultipartConfig;
import java.util.List;
import java.util.Optional;


@MultipartConfig()
@Dependent
public class AvatarRepository implements Repository<Avatar, Long> {


    private DataStore store;

    @Inject
    public AvatarRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Avatar> find(Long id) {
        return store.findAvatar(id);
    }

    @Override
    public List<Avatar> findAll() {
        return null;
    }

    @Override
    public void create(Avatar entity) {
        store.createAvatar(entity);

    }

    @Override
    public void delete(Avatar entity) {
        store.deleteAvatar(entity.getId());

    }

    @Override
    public void update(Avatar entity) {
        store.updateAvatar(entity);

    }
}
