package edu.kbtu.university.users;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Objects;

import edu.kbtu.university.enums.Role;
import edu.kbtu.university.exceptions.AuthenticationException;

/**
 * Base class for every actor in the university information system.
 * Stores identity, contact, and an SHA-256 hash of the user's password.
 */
public abstract class User implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String id;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String passwordHash;
    protected LocalDate dateOfBirth;

    public User() {
    }

    public User(String id, String firstName, String lastName, String email,
                String plainPassword, LocalDate dateOfBirth) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = hash(plainPassword);
        this.dateOfBirth = dateOfBirth;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFullName() {
        return (firstName == null ? "" : firstName) + " " + (lastName == null ? "" : lastName);
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public void setPassword(String plainPassword) {
        this.passwordHash = hash(plainPassword);
    }

    /**
     * Verifies the supplied plain-text password against the stored hash.
     */
    public boolean verifyPassword(String pwd) {
        if (pwd == null || passwordHash == null) return false;
        return passwordHash.equals(hash(pwd));
    }

    /**
     * Attempts to log this user in. Returns {@code true} when the password
     * matches; throws {@link AuthenticationException} otherwise so the
     * caller (UniversitySystem / Main) can react.
     */
    public boolean login(String pwd) throws AuthenticationException {
        if (!verifyPassword(pwd)) {
            throw new AuthenticationException("Invalid credentials for user " + id);
        }
        return true;
    }

    /**
     * Logout hook. Currently a no-op; UniversitySystem may override this
     * behavior by clearing its session reference.
     */
    public void logout() {
    }

    /**
     * Each concrete user reports its role for menu dispatch and reporting.
     */
    public abstract Role getRole();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User other = (User) o;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{id=" + id + ", name=" + getFullName() + ", email=" + email + "}";
    }

    private static String hash(String s) {
        if (s == null) return null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(s.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder(bytes.length * 2);
            for (byte b : bytes) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 not available", e);
        }
    }
}
