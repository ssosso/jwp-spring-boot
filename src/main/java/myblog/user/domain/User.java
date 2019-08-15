package myblog.user.domain;

import java.util.Objects;

public class User {
    private int id;

    private String userId;

    private String email;

    private String password;

    private User() {
    }

    public User(int id, String userId, String email, String password) {
        this.id = id;
        this.userId = userId;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
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

    public boolean matchId(int id) {
        return this.id == id;
    }

    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }

    public boolean matchUserId(String userId) {
        return this.userId.equals(userId);
    }

    public User update(User user) {
        this.userId = user.userId;
        this.email = user.email;
        this.password = user.password;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(userId, user.userId) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, email, password);
    }
}
