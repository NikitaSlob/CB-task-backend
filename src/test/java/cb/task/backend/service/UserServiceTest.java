package cb.task.backend.service;

import cb.task.backend.entity.AppUser;
import cb.task.backend.repository.UserRepository;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.Optional;

@SpringBootTest
public class UserServiceTest {

    @MockBean
    UserRepository repository;

    @Autowired
    UserService service;

    @Test
    void testFindByLogin() {
        val user = AppUser.builder()
                .login("login")
                .fullName("fullname")
                .password("password")
                .build();
        Mockito.when(repository.findByLogin("login")).thenReturn(Optional.of(user));
        val result = service.getUserByLogin("login");

        Assertions.assertEquals(result.getFullName(), user.getFullName());
    }

    @Test
    void testFindByLoginErrors() {
        Mockito.when(repository.findByLogin("")).thenThrow(IllegalArgumentException.class);
        Mockito.when(repository.findByLogin(null)).thenThrow(InvalidDataAccessApiUsageException.class);

        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> service.getUserByLogin(""));
        Assertions.assertThrowsExactly(InvalidDataAccessApiUsageException.class,
                () -> service.getUserByLogin(null));
    }

}
