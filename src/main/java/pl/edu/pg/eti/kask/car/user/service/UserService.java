package pl.edu.pg.eti.kask.car.user.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.car.user.entity.User;
import pl.edu.pg.eti.kask.car.user.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;


@ApplicationScoped
@NoArgsConstructor//Empty constructor is required for creating proxy while CDI injection.
public class UserService {


    private UserRepository repository;


    @Inject
    public UserService(UserRepository repository) {
        this.repository = repository;
    }


    public Optional<User> find(Long id) {
        return repository.find(id);
    }


    public Optional<User> find(String login) {
        return repository.find(login);
    }


    public Optional<User> find(String login, String password) {
        return repository.findByLoginAndPassword(login, password);
    }


    public void create(User user) {
        repository.create(user);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public void update(User user) {
        repository.update(user);
    }

    public void delete(Long user) {
        repository.delete(repository.find(user).orElseThrow());
    }
}
