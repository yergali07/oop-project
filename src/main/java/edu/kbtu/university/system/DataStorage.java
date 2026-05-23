package edu.kbtu.university.system;

import java.io.*;
import java.util.*;

/**
 * Persistence helper for the {@link UniversitySystem} singleton. Reads
 * and writes the entire system graph to a serialized blob on disk
 * (default path {@code university-system.ser}).
 */
public class DataStorage {

    private static final String DEFAULT_FILE = "university-system.ser";

    /**
     * Default constructor
     */
    public DataStorage() {
    }

    /**
     * @param s
     */
    public static void serialize(UniversitySystem s) {
        if (s == null) {
            throw new IllegalArgumentException("system must not be null");
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DEFAULT_FILE))) {
            out.writeObject(s);
        } catch (IOException e) {
            throw new IllegalStateException("Could not save university system state", e);
        }
    }

    /**
     * @return
     */
    public static UniversitySystem deserialize() {
        File file = new File(DEFAULT_FILE);
        if (!file.exists()) {
            return null;
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (UniversitySystem) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalStateException("Could not load university system state", e);
        }
    }

}
