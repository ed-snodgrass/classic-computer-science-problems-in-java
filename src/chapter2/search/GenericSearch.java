package chapter2.search;

import java.util.List;

public class GenericSearch {
    public static <T extends Comparable<T>> boolean linearContains(List<T> list, T key) {
        for (T item : list) {
            if (item.compareTo(key) == 0) {
                return true;
            }
        }
        return false;
    }

    public static <T extends Comparable<T>> boolean binaryContains(List<T> sortedList, T key) {
        int low = 0;
        int high = sortedList.size() - 1;
        while (low <= high) {
            int middle = (low + high) / 2;
            int comparison = sortedList.get(middle).compareTo(key);
            if (comparison < 0) {
                low = middle + 1;
            } else if (comparison > 0) {
                high = middle - 1;
            } else {
                return true;
            }
        }
        return false;
    }
}
