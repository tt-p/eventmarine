package yte.intern.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yte.intern.model.UserLogin;
import yte.intern.model.UserProfile;
import yte.intern.validation.TcNo;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    // ToDo tcNo validation
    @TcNo
    private Long tcNo;

    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email address")
    private String email;

    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Surname is required")
    private String surname;

    @Past(message = "Invalid Birth Date")
    private LocalDate birthDate;

    @NotEmpty(message = "Username is required")
    private String username;

    // ToDo password validation
    @NotEmpty(message = "Password is required")
    private String password;

    @NotEmpty(message = "Password Confirmation is required")
    private String confirmPassword;

    @AssertTrue(message = "Password did not match")
    public boolean isPasswordConfirmed() {
        if (password != null && confirmPassword != null)
            return password.equals(confirmPassword);
        else
            return true;
    }

    @AssertFalse(message = "Admin is not a proper username")
    public boolean isNotAdmin() {
        if (username != null && !username.equals(""))
            return username.equalsIgnoreCase("admin");
        else
            return false;
    }

    public UserProfile getUserProfile() {
        return new UserProfile(tcNo, email, name, surname, birthDate);
    }

    public UserLogin getUserLogin() {
        return new UserLogin(username, password);
    }

}
