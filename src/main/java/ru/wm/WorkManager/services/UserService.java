package ru.wm.WorkManager.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.wm.WorkManager.dto.UserDTO;
import ru.wm.WorkManager.entities.UserEntity;
import ru.wm.WorkManager.repositories.UserRepository;
import ru.wm.WorkManager.services.interfaces.UserServiceInterface;
import ru.wm.WorkManager.utils.DateProvider;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService implements UserServiceInterface {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DateProvider dateProvider;

    @Override
    public Optional<UserEntity> findById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return this.repository.findAll();
    }

    @Override
    public UserEntity findByEmail(String email) {
        return this.repository.findByEmail(email);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return this.repository.findByUsername(username);
    }

    @Override
    public void register(UserDTO dto) {
        // implementation soon
    }
}
