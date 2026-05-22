package edu.kbtu.university.users;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.kbtu.university.enums.Role;
import edu.kbtu.university.research.ResearchPaper;
import edu.kbtu.university.research.ResearchProfile;
import edu.kbtu.university.research.ResearchProject;

/**
 * Employee whose role is exclusively research — does not teach, but
 * publishes and joins projects.
 */
public class ResearchEmployee extends Employee implements Researcher {

    private static final long serialVersionUID = 1L;

    private ResearchProfile profile;

    public ResearchEmployee() {
        this.profile = new ResearchProfile();
    }

    public ResearchEmployee(String id, String firstName, String lastName, String email,
                            String plainPassword, LocalDate dateOfBirth,
                            double salary, LocalDate dateHired, String department) {
        super(id, firstName, lastName, email, plainPassword, dateOfBirth,
              salary, dateHired, department);
        this.profile = new ResearchProfile();
    }

    public ResearchProfile getProfile() { return profile; }
    public void setProfile(ResearchProfile profile) { this.profile = profile; }

    @Override
    public List<ResearchPaper> getPapers() {
        return profile == null ? Collections.emptyList() : profile.getPapers();
    }

    @Override
    public List<ResearchProject> getProjects() {
        return profile == null ? Collections.emptyList() : profile.getProjects();
    }

    @Override
    public int getHIndex() {
        return profile == null ? 0 : profile.getHIndex();
    }

    @Override
    public void publishPaper(ResearchPaper p) {
        if (profile != null && p != null) profile.addPaper(p);
    }

    @Override
    public void joinProject(ResearchProject pr) {
        if (profile != null && pr != null) profile.addProject(pr);
    }

    @Override
    public void printPapers(Comparator<ResearchPaper> c) {
        if (profile != null) profile.printPapers(c);
    }

    @Override
    public Role getRole() {
        return Role.RESEARCH_EMPLOYEE;
    }
}
