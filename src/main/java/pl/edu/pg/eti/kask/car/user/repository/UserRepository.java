package pl.edu.pg.eti.kask.car.user.repository;

import pl.edu.pg.eti.kask.car.datastore.DataStore;
import pl.edu.pg.eti.kask.car.repository.Repository;
import pl.edu.pg.eti.kask.car.user.entity.User;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;


@Dependent
public class UserRepository implements Repository<User, Long> {


    private DataStore store;

    @Inject
    public UserRepository(DataStore store) {
        this.store = store;
    }

    public Optional<User> find(String login) {
        return store.findUser(login);
    }

    @Override
    public Optional<User> find(Long id) {
        return store.findUser(id);
    }

    @Override
    public List<User> findAll() {
        return store.findAllUsers();
    }

    @Override
    public void create(User entity) {
        store.createUser(entity);
    }

    @Override
    public void delete(User entity) {
        store.deleteUser(entity.getId());
    }

    @Override
    public void update(User entity) {
        store.updateUser(entity);
    }

    public Optional<User> findByLoginAndPassword(String login, String password) {
        return store.findAllUsers().stream()
                .filter(user -> user.getLogin().equals(login) && user.getPassword().equals(password))
                .findFirst();
    }

}
