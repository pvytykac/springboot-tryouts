package net.pvytykac.admin.controller.admin;

import net.pvytykac.admin.db.AdminStatus;

/**
 * @author Paly
 * @since 2021-10-30
 */
public class AdminDetailDto extends AdminViewDto {

    private final PreferencesDto preferences;

    public AdminDetailDto(String id, String username, String displayName, String email, AdminStatus status,
                          PreferencesDto preferences) {
        super(id, username, displayName, email, status);
        this.preferences = preferences;
    }

    public PreferencesDto getPreferences() {
        return preferences;
    }
}
