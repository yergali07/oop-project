package edu.kbtu.university.research;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class ByCitationsComparator implements Comparator<ResearchPaper> {

    /**
     * Default constructor
     */
    public ByCitationsComparator() {
    }

    /**
     * @param a 
     * @param b 
     * @return
     */
    public int compare(ResearchPaper a, ResearchPaper b) {
        return Integer.compare(
                b == null ? 0 : b.getCitations(),
                a == null ? 0 : a.getCitations());
    }

}
