package edu.kbtu.university.research;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class ByPagesComparator implements Comparator<ResearchPaper> {

    /**
     * Default constructor
     */
    public ByPagesComparator() {
    }

    /**
     * @param a 
     * @param b 
     * @return
     */
    public int compare(ResearchPaper a, ResearchPaper b) {
        return Integer.compare(
                b == null ? 0 : b.getPages(),
                a == null ? 0 : a.getPages());
    }

}
