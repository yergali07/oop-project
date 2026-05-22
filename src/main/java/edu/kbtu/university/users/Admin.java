package edu.kbtu.university.users;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import edu.kbtu.university.enums.Role;
import edu.kbtu.university.system.LogEntry;
import edu.kbtu.university.system.UniversitySystem;

/**
 * System administrator. Manages user lifecycle and audits logs through the
 * central {@link UniversitySystem} singleton.
 */
public class Admin extends Employee {

    private static final long serialVersionUID = 1L;

    public Admin() {
    }

    public Admin(String id, String firstName, String lastName, String email,
                 String plainPassword, LocalDate dateOfBirth,
                 double salary, LocalDate dateHired, String department) {
        super(id, firstName, lastName, email, plainPassword, dateOfBirth,
              salary, dateHired, department);
    }

    public void addUser(User u) {
        if (u == null) return;
        UniversitySystem sys = UniversitySystem.getInstance();
        sys.addUser(u);
        sys.addLog(this, "ADD_USER", "Added user " + u.getId());
    }

    public void removeUser(String id) {
        if (id == null) return;
        UniversitySystem sys = UniversitySystem.getInstance();
        sys.removeUser(id);
        sys.addLog(this, "REMOVE_USER", "Removed user " + id);
    }

    /**
     * Replaces an existing user with the supplied object (identity matched by
     * id). If the user does not exist it is added.
     */
    public void updateUser(User u) {
        if (u == null || u.getId() == null) return;
        UniversitySystem sys = UniversitySystem.getInstance();
        sys.removeUser(u.getId());
        sys.addUser(u);
        sys.addLog(this, "UPDATE_USER", "Updated user " + u.getId());
    }

    public List<LogEntry> viewLogs() {
        List<LogEntry> logs = UniversitySystem.getInstance().getLogs();
        return logs == null ? Collections.emptyList() : Collections.unmodifiableList(logs);
    }

    @Override
    public Role getRole() {
        return Role.ADMIN;
    }
}
