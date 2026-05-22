package edu.kbtu.university.research;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ResearchProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<ResearchPaper> papers;
    private List<ResearchProject> projects;
    private int hIndex;

    public ResearchProfile() {
        this.papers = new ArrayList<>();
        this.projects = new ArrayList<>();
        this.hIndex = 0;
    }

    public List<ResearchPaper> getPapers() {
        return papers == null ? Collections.emptyList() : papers;
    }

    public List<ResearchProject> getProjects() {
        return projects == null ? Collections.emptyList() : projects;
    }

    public int getHIndex() {
        return calculateHIndex();
    }

    public int calculateHIndex() {
        if (papers == null || papers.isEmpty()) {
            hIndex = 0;
            return hIndex;
        }
        List<Integer> citations = new ArrayList<>();
        for (ResearchPaper paper : papers) {
            citations.add(paper == null ? 0 : paper.getCitations());
        }
        citations.sort(Collections.reverseOrder());
        int h = 0;
        for (int i = 0; i < citations.size(); i++) {
            int candidate = i + 1;
            if (citations.get(i) >= candidate) {
                h = candidate;
            } else {
                break;
            }
        }
        hIndex = h;
        return hIndex;
    }

    public void addPaper(ResearchPaper p) {
        if (p == null) return;
        if (papers == null) papers = new ArrayList<>();
        if (!papers.contains(p)) {
            papers.add(p);
            calculateHIndex();
        }
    }

    public void addProject(ResearchProject pr) {
        if (pr == null) return;
        if (projects == null) projects = new ArrayList<>();
        if (!projects.contains(pr)) {
            projects.add(pr);
        }
    }

    public void printPapers(Comparator<ResearchPaper> c) {
        if (papers == null) return;
        Comparator<ResearchPaper> comparator = c == null ? Comparator.naturalOrder() : c;
        papers.stream().sorted(comparator).forEach(System.out::println);
    }
}
