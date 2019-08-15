package myblog.user.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {
    @Test
    void update() {
        User actualUser = new User(1, "javajigi", "javajigi@gmail.com", "password");
        User expectedUser = new User(1, "ssosso", "ssossohow@gmail.com", "ssosso_password");

        assertThat(actualUser.update(expectedUser)).isEqualTo(expectedUser);
    }
}
