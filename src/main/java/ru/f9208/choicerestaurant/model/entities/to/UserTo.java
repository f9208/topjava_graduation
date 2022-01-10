package ru.f9208.choicerestaurant.model.entities.to;

import ru.f9208.choicerestaurant.model.entities.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.beans.ConstructorProperties;
import java.io.Serial;
import java.io.Serializable;

public class UserTo implements Serializable {
    @Serial
    private static final long serialVersionUID = 4483316976263340171L;
    private Integer id;
    @NotBlank(message = "введите имя!")
    private String name;
    @NotBlank(message = "введите имейл!")
    @Email(message = "инвалидный имейл")
    private String email;
    @NotBlank(message = "не должно быть пустыыыым!")
    @Size(min = 4, max = 25, message = "слишком короткий!")
    private String password;

    public UserTo() {
    }

    @ConstructorProperties({"name", "email", "password"})
    public UserTo(Integer id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public UserTo(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
