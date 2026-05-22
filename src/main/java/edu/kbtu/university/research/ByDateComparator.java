package edu.kbtu.university.research;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class ByDateComparator implements Comparator<ResearchPaper> {

    /**
     * Default constructor
     */
    public ByDateComparator() {
    }

    /**
     * @param a 
     * @param b 
     * @return
     */
    public int compare(ResearchPaper a, ResearchPaper b) {
        if (a == null && b == null) return 0;
        if (a == null) return 1;
        if (b == null) return -1;
        if (a.getDatePublished() == null && b.getDatePublished() == null) return 0;
        if (a.getDatePublished() == null) return 1;
        if (b.getDatePublished() == null) return -1;
        return b.getDatePublished().compareTo(a.getDatePublished());
    }

}
