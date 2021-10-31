package net.pvytykac.admin.db;

import net.pvytykac.db.Entity;

/**
 * @author Paly
 * @since 2021-10-30
 */
public class Admin implements Entity<String> {

    private final String username;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final Preferences preferences;
    private final String pwHash;
    private final String pwSalt;
    private final AdminStatus status;

    public Admin(String username, String firstName, String lastName, String email, Preferences preferences,
                 String pwHash, String pwSalt, AdminStatus status) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.preferences = preferences;
        this.pwHash = pwHash;
        this.pwSalt = pwSalt;
        this.status = status;
    }

    @Override
    public String getId() {
        return username;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public String getPwHash() {
        return pwHash;
    }

    public String getPwSalt() {
        return pwSalt;
    }

    public AdminStatus getStatus() {
        return status;
    }
}
