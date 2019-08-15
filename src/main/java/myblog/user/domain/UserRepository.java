package myblog.user.domain;

import myblog.user.dto.UserCreatedDto;
import myblog.user.dto.UserUpdatedDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    private static List<User> users = new ArrayList<>();

    public static User create(UserCreatedDto createdDto) {
        User user = new User(users.size() + 1,
                createdDto.getUserId(),
                createdDto.getEmail(),
                createdDto.getPassword());
        users.add(user);
        return user;
    }

    public static User findById(int id) {
        return users.get(id - 1);
    }

    public static Optional<User> findByUserId(String userId) {
        return users.stream()
                .filter(u -> u.matchUserId(userId))
                .findFirst();
    }

    public static User update(int id, UserUpdatedDto userUpdatedDto) {
        User existUser = users.stream()
                .filter(user -> user.matchId(id))
                .findFirst().get();
        return existUser.update(userUpdatedDto.toUser(id));
    }

    public static Boolean delete(int id) {
        User existUser = users.stream()
                .filter(user -> user.matchId(id))
                .findFirst().get();
        return users.remove(existUser);
    }
}
