package edu.kbtu.university.enums;

/**
 * Room category used by {@link edu.kbtu.university.system.ScheduleGenerator}
 * to place lessons in compatible facilities:
 * <ul>
 *   <li>{@code LECTURE_HALL} — large room for one-way lectures</li>
 *   <li>{@code LAB} — hands-on lab with equipment</li>
 *   <li>{@code SEMINAR_ROOM} — small group discussion</li>
 * </ul>
 */
public enum RoomType {
    LECTURE_HALL,
    LAB,
    SEMINAR_ROOM
}
