package edu.kbtu.university.users;

import java.io.*;
import java.util.*;

import edu.kbtu.university.news.News;

/**
 * 
 */
public interface NewsObserver {


    /**
     * @param news
     */
    public void update(News news);

}