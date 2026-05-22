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

    /** Default constructor (used by serialization). */
    public Admin() {
    }

    /**
     * Full-state constructor.
     *
     * @param id            administrator id (typically {@code EMP-####})
     * @param firstName     first name
     * @param lastName      last name
     * @param email         contact email
     * @param plainPassword plain-text password (hashed before storage)
     * @param dateOfBirth   date of birth
     * @param salary        gross monthly salary
     * @param dateHired     hire date
     * @param department    organisational unit
     */
    public Admin(String id, String firstName, String lastName, String email,
                 String plainPassword, LocalDate dateOfBirth,
                 double salary, LocalDate dateHired, String department) {
        super(id, firstName, lastName, email, plainPassword, dateOfBirth,
              salary, dateHired, department);
    }

    /**
     * Registers a new user in the system and writes an audit-log entry.
     *
     * @param u user to add (no-op if {@code null})
     */
    public void addUser(User u) {
        if (u == null) return;
        UniversitySystem sys = UniversitySystem.getInstance();
        sys.addUser(u);
        sys.addLog(this, "ADD_USER", "Added user " + u.getId());
    }

    /**
     * Removes a user by id and writes an audit-log entry.
     *
     * @param id user id to remove (no-op if {@code null})
     */
    public void removeUser(String id) {
        if (id == null) return;
        UniversitySystem sys = UniversitySystem.getInstance();
        sys.removeUser(id);
        sys.addLog(this, "REMOVE_USER", "Removed user " + id);
    }

    /**
     * Replaces an existing user with the supplied object (identity matched by
     * id). If the user does not exist it is added.
     *
     * @param u user to replace/insert (no-op if {@code null} or has no id)
     */
    public void updateUser(User u) {
        if (u == null || u.getId() == null) return;
        UniversitySystem sys = UniversitySystem.getInstance();
        sys.removeUser(u.getId());
        sys.addUser(u);
        sys.addLog(this, "UPDATE_USER", "Updated user " + u.getId());
    }

    /**
     * Returns the audit log.
     *
     * @return unmodifiable view of every {@link LogEntry} recorded so far
     */
    public List<LogEntry> viewLogs() {
        List<LogEntry> logs = UniversitySystem.getInstance().getLogs();
        return logs == null ? Collections.emptyList() : Collections.unmodifiableList(logs);
    }

    /**
     * Returns this user's role.
     * @return {@link Role#ADMIN}
     */
    @Override
    public Role getRole() {
        return Role.ADMIN;
    }
}
