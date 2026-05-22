package edu.kbtu.university.news;

import java.io.*;
import java.util.*;

import edu.kbtu.university.users.NewsObserver;

/**
 * 
 */
public class NewsService implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor
     */
    public NewsService() {
        this.subscribers = new ArrayList<>();
    }

    /**
     * 
     */
    private List<NewsObserver> subscribers;



    /**
     * @param o
     */
    public void subscribe(NewsObserver o) {
        if (o == null) return;
        if (subscribers == null) subscribers = new ArrayList<>();
        if (!subscribers.contains(o)) subscribers.add(o);
    }

    /**
     * @param o
     */
    public void unsubscribe(NewsObserver o) {
        if (subscribers != null) subscribers.remove(o);
    }

    /**
     * @param n
     */
    public void publish(News n) {
        notifySubscribers(n);
    }

    /**
     * @param n
     */
    public void notifySubscribers(News n) {
        if (n == null || subscribers == null) return;
        for (NewsObserver subscriber : new ArrayList<>(subscribers)) {
            subscriber.update(n);
        }
    }

}
