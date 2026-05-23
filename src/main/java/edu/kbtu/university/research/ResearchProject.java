package edu.kbtu.university.research;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.kbtu.university.exceptions.NotAResearcherException;
import edu.kbtu.university.users.Researcher;
import edu.kbtu.university.users.User;

/**
 * A research project (topic + roster of researchers + papers produced
 * under the project). Per ТЗ, only users implementing {@link Researcher}
 * may participate.
 */
public class ResearchProject implements Serializable {

    private static final long serialVersionUID = 1L;

    private String topic;
    private List<ResearchPaper> publishedPapers;
    private List<Researcher> participants;
    private Researcher leader;

    /** Default constructor (initializes the collaborator lists). */
    public ResearchProject() {
        this.publishedPapers = new ArrayList<>();
        this.participants = new ArrayList<>();
    }

    /**
     * Convenience constructor.
     *
     * @param topic  research topic
     * @param leader project leader (may be {@code null} until appointed)
     */
    public ResearchProject(String topic, Researcher leader) {
        this();
        this.topic = topic;
        this.leader = leader;
        if (leader != null) {
            participants.add(leader);
        }
    }

    /**
     * Returns the topic.
     * @return research topic
     */
    public String getTopic() { return topic; }

    /**
     * Updates the topic.
     * @param topic new topic
     */
    public void setTopic(String topic) { this.topic = topic; }

    /**
     * Returns the leader.
     * @return the project leader, or {@code null} if not appointed
     */
    public Researcher getLeader() { return leader; }

    /**
     * Assigns a project leader and ensures they are in the participants list.
     * @param leader new leader
     */
    public void setLeader(Researcher leader) {
        this.leader = leader;
        if (leader != null && !participants.contains(leader)) {
            participants.add(leader);
        }
    }

    /**
     * Returns the participants.
     * @return unmodifiable view of the participants
     */
    public List<Researcher> getParticipants() {
        return Collections.unmodifiableList(participants);
    }

    /**
     * Returns the published papers.
     * @return unmodifiable view of the published papers
     */
    public List<ResearchPaper> getPublishedPapers() {
        return Collections.unmodifiableList(publishedPapers);
    }

    /**
     * Adds a participant to the project. Per ТЗ, only researchers may join —
     * a non-{@link Researcher} throws {@link NotAResearcherException}.
     *
     * @param u user to add
     * @throws NotAResearcherException if {@code u} does not implement
     *         {@link Researcher}
     * @throws IllegalArgumentException if {@code u} is {@code null}
     */
    public void addParticipant(User u) {
        if (u == null) {
            throw new IllegalArgumentException("user must not be null");
        }
        if (!(u instanceof Researcher)) {
            throw new NotAResearcherException(
                "User " + u.getId() + " is not a researcher and cannot join a research project");
        }
        Researcher r = (Researcher) u;
        if (!participants.contains(r)) {
            participants.add(r);
        }
    }

    /**
     * Adds a paper to the project's output and to every participant's
     * research profile (the project author list is the union of joining
     * researchers, not stored on the paper itself).
     *
     * @param p paper to add (no-op if {@code null})
     */
    public void addPaper(ResearchPaper p) {
        if (p == null) return;
        if (!publishedPapers.contains(p)) {
            publishedPapers.add(p);
        }
        for (Researcher r : participants) {
            r.publishPaper(p);
        }
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "ResearchProject{topic=" + topic
                + ", participants=" + participants.size()
                + ", papers=" + publishedPapers.size() + "}";
    }
}
