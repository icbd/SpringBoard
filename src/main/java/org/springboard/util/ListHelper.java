package org.springboard.util;

import java.util.List;
import java.util.Random;

public class ListHelper {

    private static Random rand = new Random();

    /**
     * Fetch a random item from List;
     * Return null if list is empty.
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> T sample(List<T> list) {
        if (list.size() == 0) {
            return null;
        }

        return list.get(rand.nextInt(list.size()));
    }
}
