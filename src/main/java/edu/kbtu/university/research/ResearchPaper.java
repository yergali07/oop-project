package edu.kbtu.university.research;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.kbtu.university.users.Researcher;

public class ResearchPaper implements Comparable<ResearchPaper>, Serializable {

    private static final long serialVersionUID = 1L;

    private String title;
    private List<Researcher> authors = new ArrayList<>();
    private String journal;
    private String doi;
    private LocalDate datePublished;
    private int citations;
    private int pages;
    private String abstractText;
    private List<String> keywords = new ArrayList<>();

    public ResearchPaper() {
    }

    @Override
    public int compareTo(ResearchPaper o) {
        if (o == null) return -1;
        return Integer.compare(o.citations, citations);
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public List<Researcher> getAuthors() { return authors; }
    public void setAuthors(List<Researcher> authors) {
        this.authors = authors == null ? new ArrayList<>() : new ArrayList<>(authors);
    }

    public String getJournal() { return journal; }
    public void setJournal(String journal) { this.journal = journal; }

    public String getDoi() { return doi; }
    public void setDoi(String doi) { this.doi = doi; }

    public LocalDate getDatePublished() { return datePublished; }
    public void setDatePublished(LocalDate datePublished) { this.datePublished = datePublished; }

    public int getCitations() { return citations; }
    public void setCitations(int citations) { this.citations = Math.max(0, citations); }

    public int getPages() { return pages; }
    public void setPages(int pages) { this.pages = Math.max(0, pages); }

    public String getAbstractText() { return abstractText; }
    public void setAbstractText(String abstractText) { this.abstractText = abstractText; }

    public List<String> getKeywords() { return keywords; }
    public void setKeywords(List<String> keywords) {
        this.keywords = keywords == null ? new ArrayList<>() : new ArrayList<>(keywords);
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, citations=%d, pages=%d",
                title, journal, datePublished, citations, pages);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResearchPaper)) return false;
        ResearchPaper that = (ResearchPaper) o;
        return Objects.equals(title, that.title) && Objects.equals(doi, that.doi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, doi);
    }
}
