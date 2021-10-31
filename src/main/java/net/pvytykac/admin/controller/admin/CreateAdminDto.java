package net.pvytykac.admin.controller.admin;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author Paly
 * @since 2021-10-30
 */
public class CreateAdminDto {

    @NotBlank
    private final String firstName;

    @NotBlank
    private final String lastName;

    @NotBlank
    private final String username;

    @Email
    @NotBlank
    private final String email;

    public CreateAdminDto(String firstName, String lastName, String username, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
