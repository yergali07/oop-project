package edu.kbtu.university.users;

import java.io.Serializable;

import edu.kbtu.university.news.News;

/**
 * Observer (pattern <strong>Observer</strong>) for the news subsystem.
 * Implementations receive a callback whenever the {@code NewsService}
 * publishes a new item.
 *
 * <p>{@code Serializable} so that {@link edu.kbtu.university.news.NewsService}'s
 * subscriber list round-trips through {@link
 * edu.kbtu.university.system.DataStorage#serialize(edu.kbtu.university.system.UniversitySystem)}.
 */
public interface NewsObserver extends Serializable {

    /**
     * Notification hook invoked by the news service when a new item is
     * published.
     *
     * @param news the news item being delivered
     */
    void update(News news);
}
