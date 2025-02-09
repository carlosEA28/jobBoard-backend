package br.com.carlos.JobBoard_backend.repository;

import br.com.carlos.JobBoard_backend.dto.LoggedUserDto;
import br.com.carlos.JobBoard_backend.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findById(UUID userId);


}
