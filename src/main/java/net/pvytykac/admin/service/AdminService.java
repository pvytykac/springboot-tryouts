package net.pvytykac.admin.service;

import net.pvytykac.admin.db.Admin;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Paly
 * @since 2021-10-30
 */
public interface AdminService {

    @NonNull Collection<Admin> list();

    @NonNull Optional<Admin> getById(String id);

    @NonNull String createAdmin(String username, String email, String firstName, String lastName);

    boolean delete(String id);
}
