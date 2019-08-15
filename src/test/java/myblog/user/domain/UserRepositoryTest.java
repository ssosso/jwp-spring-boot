package myblog.user.domain;

import myblog.user.dto.UserCreatedDto;
import myblog.user.dto.UserUpdatedDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryTest {

    @Test
    void update() {
        UserCreatedDto userCreatedDto = new UserCreatedDto("javajigi", "javajigi@gmail.com", "password");
        User existUser = UserRepository.create(userCreatedDto);

        UserUpdatedDto userUpdatedDto = new UserUpdatedDto("ssosso", "ssossohow@gmail.com", "ssosso_password");
        User user = UserRepository.update(existUser.getId(), userUpdatedDto);

        assertThat(UserRepository.findById(existUser.getId())).isEqualTo(user);
    }

    @Test
    void delete() {
        UserCreatedDto userCreatedDto = new UserCreatedDto("javajigi", "javajigi@gmail.com", "password");
        User existUser = UserRepository.create(userCreatedDto);

        boolean isDeleted = UserRepository.delete(existUser.getId());

        assertThat(isDeleted).isTrue();
        assertThat(UserRepository.findByUserId(existUser.getUserId())).isEmpty();
    }
}
