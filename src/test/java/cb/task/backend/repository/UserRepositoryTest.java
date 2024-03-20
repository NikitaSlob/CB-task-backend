package cb.task.backend.repository;

import cb.task.backend.entity.AppUser;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest

public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void verifyRepository() {
        val user = AppUser.builder()
                .fullName("name")
                .login("login")
                .password("password")
                .roles(null)
                .build();

        Assertions.assertNull(user.getId());
        userRepository.save(user);
        Assertions.assertNotNull(user.getId());
    }

    @Test
    void testFindByLoginRequest() {
        val user = AppUser.builder()
                .fullName("name")
                .login("login")
                .password("password")
                .roles(null)
                .build();

        userRepository.save(user);

        val appUser = userRepository.findByLogin("login");
        Assertions.assertTrue(appUser.stream().anyMatch(user1 -> user1.getFullName().equals(user.getFullName())));

    }
}
