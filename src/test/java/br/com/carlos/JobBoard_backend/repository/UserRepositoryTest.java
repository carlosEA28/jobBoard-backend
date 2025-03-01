package br.com.carlos.JobBoard_backend.repository;

import br.com.carlos.JobBoard_backend.dto.CreateUserDto;
import br.com.carlos.JobBoard_backend.entity.UserEntity;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.UUID;


@Slf4j
@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    private UserEntity createUser(CreateUserDto dto) {
        UserEntity newUser = new UserEntity();
        newUser.setFirstName(dto.firstName());
        newUser.setSecondName(dto.secondName());
        newUser.setEmail(dto.email());
        newUser.setPassword(dto.password());
        this.entityManager.persist(newUser);
        return newUser;
    }

    @Test
    @DisplayName("Should get user successfully from database")
    void findByEmailSuccess() {
        CreateUserDto data = new CreateUserDto("Carlos", "Eduardo", "test@test.com", "1234");
        this.createUser(data);

        Optional<UserEntity> foundedUser = this.userRepository.findByEmail(data.email());
        assertThat(foundedUser.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get user from database when user does not exist")
    void findByEmailFail() {

        String email = "test@test,com";

        Optional<UserEntity> foundedUser = this.userRepository.findByEmail(email);
        assertThat(foundedUser.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Should get user successfully from database by user id")
    void findById() {
        CreateUserDto data = new CreateUserDto("Carlos", "Eduardo", "test@test.com", "1234");
        UserEntity createdUser = this.createUser(data);

        Optional<UserEntity> foundedUser = this.userRepository.findById(createdUser.getUserId());
        assertThat(foundedUser.isPresent()).isTrue();

    }


}