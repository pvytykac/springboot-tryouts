package net.pvytykac.admin;

import net.pvytykac.admin.db.Admin;
import net.pvytykac.admin.service.AdminService;
import net.pvytykac.admin.service.impl.AdminServiceImpl;
import net.pvytykac.db.Repository;
import net.pvytykac.db.impl.MapRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author Paly
 * @since 2021-10-30
 */
@SpringBootApplication
public class AdminServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(AdminServiceApp.class, args);
    }

    @Bean
    public Repository<String, Admin> getAdminRepository() {
        return new MapRepositoryImpl<>();
    }

    @Bean
    public AdminService getAdminService(Repository<String, Admin> repository) {
        return new AdminServiceImpl(repository);
    }
}
