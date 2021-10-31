package net.pvytykac.admin.controller.admin;

import java.util.List;

/**
 * @author Paly
 * @since 2021-10-30
 */
public class AdminListDto {

    private final List<AdminViewDto> admins;

    public AdminListDto(List<AdminViewDto> admins) {
        this.admins = admins;
    }

    public List<AdminViewDto> getAdmins() {
        return admins;
    }
}
