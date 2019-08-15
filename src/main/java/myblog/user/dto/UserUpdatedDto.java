package myblog.user.dto;

import myblog.user.domain.User;

public class UserUpdatedDto {
    private String userId;
    private String email;
    private String password;

    private UserUpdatedDto() {
    }

    public UserUpdatedDto(String userId, String email, String password) {
        this.userId = userId;
        this.email = email;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public User toUser(int id) {
        return new User(id, userId, email, password);
    }
}
