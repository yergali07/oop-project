package edu.kbtu.university.users;

import java.util.Comparator;
import java.util.List;

import edu.kbtu.university.research.ResearchPaper;
import edu.kbtu.university.research.ResearchProject;

/**
 * Mix-in interface for any user that can publish papers and join research
 * projects. Per ТЗ, {@code Teacher}s with the rank {@code PROFESSOR} and
 * {@code ResearchEmployee}s must implement this; {@code Student}s may
 * optionally implement it (typically masters and PhDs do).
 */
public interface Researcher {

    /**
     * @return list of papers authored by this researcher
     */
    List<ResearchPaper> getPapers();

    /**
     * @return list of research projects this researcher participates in
     */
    List<ResearchProject> getProjects();

    /**
     * @return the researcher's h-index (max {@code h} with at least
     *         {@code h} papers having {@code >= h} citations)
     */
    int getHIndex();

    /**
     * Adds a paper to the researcher's profile.
     *
     * @param p paper to publish
     */
    void publishPaper(ResearchPaper p);

    /**
     * Joins a research project.
     *
     * @param pr project to join
     */
    void joinProject(ResearchProject pr);

    /**
     * Prints the researcher's papers in the order defined by the comparator
     * (pattern <strong>Strategy</strong>).
     *
     * @param c comparator that orders the papers
     */
    void printPapers(Comparator<ResearchPaper> c);
}
