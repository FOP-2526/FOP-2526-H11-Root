package h11;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.SolutionOnly;

import java.util.function.BiFunction;

/**
 * A recursive implementation of a self-organizing list that moves accessed elements to a random position in the list
 * based on a random index which is smaller than the position of the accessed element.
 *
 * @param <T> the type of elements in the list
 */
public class RandomListRecursive<T> extends RandomList<T> implements SelfOrganizingList<T> {

    /**
     * Creates a new empty list with the given randomizer function.
     *
     * @param randomizer the randomizer function used to generate random indices
     */
    @DoNotTouch
    public RandomListRecursive(BiFunction<Integer, Integer, Integer> randomizer) {
        super(randomizer);
    }

    /**
     * Creates a new empty list with a default randomizer function.
     */
    @DoNotTouch
    public RandomListRecursive() {
    }

    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        checkBounds(index);
        if (index == 0) {
            return head.key;
        }
        int randomIndex = getRandomIndex(index);
        ListItem<ListItem<T>> prevElements = getPreviousElements(head, 0, randomIndex, index, null, null);
        ListItem<T> randomPrev = prevElements.key;
        ListItem<T> getPrev = prevElements.next.key;
        ListItem<T> item = getPrev.next;
        swap(randomPrev, getPrev);
        return item.key;
    }

    /**
     * Recursively traverses the list to find the previous elements at the specified indices.
     *
     * @param cursor      the current node in the list
     * @param i           the current index
     * @param randomIndex the random index to find containing the element to swap with the index
     * @param index       the index to find containing the element to swap with the random index
     * @param randomPrev  the found previous element at the random index
     * @param getPrev     the found previous element at the index
     *
     * @return the previous elements at the specified indices
     */
    @SolutionOnly
    private ListItem<ListItem<T>> getPreviousElements(
        ListItem<T> cursor, int i,
        int randomIndex, int index,
        ListItem<T> randomPrev, ListItem<T> getPrev) {
        if (i == randomIndex - 1) {
            randomPrev = cursor;
        } else if (i == index - 1) {
            getPrev = cursor;
            ListItem<ListItem<T>> result = new ListItem<>(randomPrev);
            result.next = new ListItem<>(getPrev);
            return result;
        }
        return getPreviousElements(cursor.next, i + 1, randomIndex, index, randomPrev, getPrev);
    }
}
