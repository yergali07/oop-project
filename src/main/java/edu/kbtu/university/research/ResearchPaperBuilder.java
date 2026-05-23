package edu.kbtu.university.research;

import java.io.*;
import java.time.*;
import java.util.*;

import edu.kbtu.university.users.Researcher;

/**
 * Builder (pattern <strong>Builder</strong>) for {@link ResearchPaper}.
 * Each setter returns {@code this} for fluent chaining; {@link #build()}
 * returns the assembled paper and resets the internal state so the same
 * builder instance can be reused.
 *
 * <p>Example:
 * <pre>{@code
 * ResearchPaper p = new ResearchPaperBuilder()
 *     .title("OOP Patterns")
 *     .authors(List.of(teacher))
 *     .journal("IEEE")
 *     .citations(42)
 *     .build();
 * }</pre>
 */
public class ResearchPaperBuilder {

    /**
     * Default constructor
     */
    public ResearchPaperBuilder() {
        this.paper = new ResearchPaper();
    }

    /**
     * 
     */
    private ResearchPaper paper;

    /**
     * @param t 
     * @return
     */
    public ResearchPaperBuilder title(String t) {
        paper.setTitle(t);
        return this;
    }

    /**
     * @param a 
     * @return
     */
    public ResearchPaperBuilder authors(List<Researcher> a) {
        paper.setAuthors(a);
        return this;
    }

    /**
     * @param j 
     * @return
     */
    public ResearchPaperBuilder journal(String j) {
        paper.setJournal(j);
        return this;
    }

    /**
     * @param d 
     * @return
     */
    public ResearchPaperBuilder doi(String d) {
        paper.setDoi(d);
        return this;
    }

    /**
     * @param d 
     * @return
     */
    public ResearchPaperBuilder datePublished(LocalDate d) {
        paper.setDatePublished(d);
        return this;
    }

    /**
     * @param n 
     * @return
     */
    public ResearchPaperBuilder citations(int n) {
        paper.setCitations(n);
        return this;
    }

    /**
     * @param n
     * @return
     */
    public ResearchPaperBuilder pages(int n) {
        paper.setPages(n);
        return this;
    }

    /**
     * Sets the abstract.
     * @param a abstract text
     * @return this builder
     */
    public ResearchPaperBuilder abstractText(String a) {
        paper.setAbstractText(a);
        return this;
    }

    /**
     * Sets the keyword list.
     * @param k keyword list (copied defensively by the paper)
     * @return this builder
     */
    public ResearchPaperBuilder keywords(List<String> k) {
        paper.setKeywords(k == null ? new ArrayList<>() : new ArrayList<>(k));
        return this;
    }

    /**
     * @return
     */
    public ResearchPaper build() {
        ResearchPaper built = paper;
        paper = new ResearchPaper();
        return built;
    }

}
