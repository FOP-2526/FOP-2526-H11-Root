package h11;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.SolutionOnly;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;

/**
 * A random implementation of a self-organizing list that moves accessed elements to the front of the list based on a
 * random index which is smaller than the position of the accessed element.
 *
 * @param <T> the type of elements in the list
 */
public abstract class RandomList<T> extends AbstractSelfOrganizingList<T> implements SelfOrganizingList<T> {

    /**
     * The randomizer function used to generate random indices.
     */
    private final BiFunction<Integer, Integer, Integer> randomizer;

    /**
     * Creates a new empty list with the given randomizer function.
     *
     * @param randomizer the randomizer function used to generate random indices
     */
    public RandomList(BiFunction<Integer, Integer, Integer> randomizer) {
        this.randomizer = randomizer;
    }

    /**
     * Creates a new empty list with a default randomizer function.
     */
    public RandomList() {
        this((min, max) -> ThreadLocalRandom.current().nextInt(min, max));
    }

    /**
     * Generates a random index between 0 (inclusive) and max (exclusive).
     *
     * @param max the upper bound for the random index
     *
     * @return a random index between 0 (inclusive) and max (exclusive)
     */
    protected int getRandomIndex(int max) {
        return randomizer.apply(0, max);
    }

    /**
     * Swaps the elements at the given indices in the list.
     *
     * @param minPrev the previous element before the minimum index
     * @param maxPrev the previous element before the maximum index
     */
    @SolutionOnly
    protected void swap(ListItem<T> minPrev, ListItem<T> maxPrev) {
        if (maxPrev == head) {
            // Case: Swap head and head.next (Transpose)
            ListItem<T> min = head;
            ListItem<T> max = head.next;
            final ListItem<T> maxNext = max.next;
            min.next = maxNext;
            max.next = min;
            head = max;
        } else if (minPrev == null) {
            // Case: Swap head and any other element
            ListItem<T> min = head;
            ListItem<T> minNext = head.next;
            ListItem<T> max = maxPrev.next;
            ListItem<T> maxNext = max.next;
            if (max == tail) {
                tail = min;
            }
            maxPrev.next = min;
            min.next = maxNext;
            max.next = minNext;
            head = max;
        } else if (minPrev.next == maxPrev) {
            // Case: Swap two adjacent elements (Transpose)
            ListItem<T> min = minPrev.next;
            ListItem<T> minNext = min.next;
            ListItem<T> max = maxPrev.next;
            ListItem<T> maxNext = max.next;
            if (max == tail) {
                tail = min;
            }
            minPrev.next = max;
            maxPrev.next = maxNext;
            minNext.next = maxPrev;
        } else {
            // Swap non-adjacent elements
            ListItem<T> min = minPrev.next;
            ListItem<T> minNext = min.next;
            ListItem<T> max = maxPrev.next;
            ListItem<T> maxNext = max.next;
            maxPrev.next = min;
            min.next = maxNext;
            max.next = minNext;
            minPrev.next = max;
        }
    }

    @DoNotTouch
    @Override
    public Strategy strategy() {
        return Strategy.RANDOM;
    }
}
