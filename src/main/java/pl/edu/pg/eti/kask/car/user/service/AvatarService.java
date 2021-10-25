package pl.edu.pg.eti.kask.car.user.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.car.user.entity.Avatar;
import pl.edu.pg.eti.kask.car.user.repository.AvatarRepository;
import pl.edu.pg.eti.kask.car.user.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;


@ApplicationScoped
@NoArgsConstructor
public class AvatarService {


    private UserRepository userRepository;
    private AvatarRepository avatarRepository;


    @Inject
    public AvatarService(UserRepository userRepository, AvatarRepository avatarRepository) {
        this.userRepository = userRepository;
        this.avatarRepository = avatarRepository;
    }
    @Transactional
    public Optional<Avatar> find(Long id) {
        return avatarRepository.find(id);
    }
    @Transactional
    public void create(Avatar avatar) {
        avatarRepository.create(avatar);
    }
    @Transactional
    public void update(Long id, InputStream is) {
        avatarRepository.find(id).ifPresent(avatar -> {
            try {
                var bytes = is.readAllBytes();
                avatar.setImage(bytes);
                avatarRepository.update(avatar);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Transactional
    public void delete(Avatar avatar) throws IOException {
        avatarRepository.delete(avatarRepository.find(avatar.getId()).orElseThrow());
    }
}
