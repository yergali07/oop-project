package edu.kbtu.university.news;

import java.io.*;
import java.util.*;

import edu.kbtu.university.users.NewsObserver;

/**
 * 
 */
public class NewsService {

    /**
     * Default constructor
     */
    public NewsService() {
    }

    /**
     * 
     */
    private List<NewsObserver> subscribers;



    /**
     * @param o
     */
    public void subscribe(NewsObserver o) {
        // TODO implement here
    }

    /**
     * @param o
     */
    public void unsubscribe(NewsObserver o) {
        // TODO implement here
    }

    /**
     * @param n
     */
    public void publish(News n) {
        // TODO implement here
    }

    /**
     * @param n
     */
    public void notifySubscribers(News n) {
        // TODO implement here
    }

}