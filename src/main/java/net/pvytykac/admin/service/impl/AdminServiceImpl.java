package net.pvytykac.admin.service.impl;

import net.pvytykac.admin.db.Admin;
import net.pvytykac.admin.db.AdminStatus;
import net.pvytykac.admin.db.Preferences;
import net.pvytykac.admin.service.AdminService;
import net.pvytykac.db.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Paly
 * @since 2021-10-30
 */
public class AdminServiceImpl implements AdminService {

    private static final Logger LOG = LoggerFactory.getLogger(AdminServiceImpl.class);

    private static final Preferences DEFAULT_PREFERENCES = new Preferences("en_US", "UTC");

    private final Repository<String, Admin> repository;

    public AdminServiceImpl(Repository<String, Admin> repository) {
        this.repository = repository;
    }

    @Override
    public @NonNull Collection<Admin> list() {
        LOG.debug("Fetching admins");

        return repository.listAll();
    }

    @Override
    public @NonNull Optional<Admin> getById(String id) {
        LOG.debug("Fetching admin '{}'", id);
        return repository.findById(id);
    }

    @NonNull
    @Override
    public String createAdmin(String username, String email, String firstName, String lastName) {
        repository.insert(new Admin(username, firstName, lastName, email, DEFAULT_PREFERENCES, null, null,
                AdminStatus.CREATED));

        return username;
    }

    @Override
    public boolean delete(String id) {
        return repository.deleteById(id).isPresent();
    }
}
