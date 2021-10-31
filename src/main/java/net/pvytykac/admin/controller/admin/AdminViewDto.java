package net.pvytykac.admin.controller.admin;

import net.pvytykac.admin.db.AdminStatus;

/**
 * @author Paly
 * @since 2021-10-30
 */
public class AdminViewDto {

    private final String id;
    private final String username;
    private final String displayName;
    private final String email;
    private final AdminStatus status;

    public AdminViewDto(String id, String username, String displayName, String email, AdminStatus status) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.email = email;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public AdminStatus getStatus() {
        return status;
    }
}
