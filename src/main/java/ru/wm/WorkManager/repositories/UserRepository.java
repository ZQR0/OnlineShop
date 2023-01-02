package ru.wm.WorkManager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.wm.WorkManager.entities.UserEntity;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
}
