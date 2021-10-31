package net.pvytykac.admin.controller.admin;

import net.pvytykac.admin.db.Admin;
import net.pvytykac.admin.service.AdminService;
import net.pvytykac.jsonpatch.JsonPatch;
import net.pvytykac.util.MimeType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Paly
 * @since 2021-10-30
 */
@RestController
public class AdminController {

    private final AdminService service;

    public AdminController(AdminService service) {
        this.service = service;
    }

    @GetMapping(value = "/v1/admin", produces = MimeType.JSON)
    public AdminListDto getAdmins() {
        Collection<Admin> admins = service.list();

        return new AdminListDto(admins.stream()
                .map(this::convertToListViewDto)
                .collect(Collectors.toList()));
    }

    @PostMapping(value = "/v1/admin", produces = MimeType.JSON)
    public ResponseEntity<?> createAdmin(@RequestBody @NotNull @Valid CreateAdminDto payload) {
        String id = service.createAdmin(payload.getUsername(), payload.getEmail(), payload.getFirstName(), payload.getLastName());

        return ResponseEntity.created(URI.create(id))
                .build();
    }

    @GetMapping(value = "/v1/admin/{id}", produces = MimeType.JSON)
    public ResponseEntity<AdminDetailDto> getAdmin(@PathVariable(name = "id") String id) {
        return service.getById(id)
                .map(this::convertToDetailViewDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping(value = "/v1/admin/{id}", consumes = MimeType.JSON_PATCH)
    public ResponseEntity<?> updateAdmin(@RequestBody @NotNull @Valid JsonPatch payload) {
        return ResponseEntity.internalServerError()
                .body(Map.of("msg", "not implemented yet"));
    }

    @DeleteMapping(value = "/v1/admin/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable(name = "id") String id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private AdminViewDto convertToListViewDto(Admin admin) {
        return new AdminViewDto(admin.getId(),
                admin.getUsername(),
                formatDisplayName(admin),
                admin.getEmail(),
                admin.getStatus());
    }

    private AdminDetailDto convertToDetailViewDto(Admin admin) {
        return new AdminDetailDto(
                admin.getId(),
                admin.getUsername(),
                formatDisplayName(admin),
                admin.getEmail(),
                admin.getStatus(),
                new PreferencesDto(admin.getPreferences().getLocale(), admin.getPreferences().getTimezone()));
    }

    private String formatDisplayName(Admin admin) {
        return String.format("%s %s", admin.getFirstName(), admin.getLastName());
    }
}
