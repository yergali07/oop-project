package edu.kbtu.university.users;

import edu.kbtu.university.news.News;

/**
 * Observer (pattern <strong>Observer</strong>) for the news subsystem.
 * Implementations receive a callback whenever the {@code NewsService}
 * publishes a new item.
 */
public interface NewsObserver {

    /**
     * Notification hook invoked by the news service when a new item is
     * published.
     *
     * @param news the news item being delivered
     */
    void update(News news);
}
