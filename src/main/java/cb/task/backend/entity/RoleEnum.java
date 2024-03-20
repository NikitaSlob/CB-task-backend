package cb.task.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleEnum {
    ROLE_USER("USER"),
    ROLE_EMPLOYEE("EMPLOYEE");
    final String authority;
}
