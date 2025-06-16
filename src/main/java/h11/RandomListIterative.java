package h11;

import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.util.function.BiFunction;

/**
 * An iterative implementation of a self-organizing list that moves accessed elements to a random position in the list
 * based on a random index which is smaller than the position of the accessed element.
 *
 * @param <T> the type of elements in the list
 *
 * @author Nhan Huynh
 */
@DoNotTouch
public class RandomListIterative<T> extends RandomList<T> implements SelfOrganizingList<T> {

    /**
     * Creates a new list with the given elements and randomizer function.
     *
     * @param elements   the elements to be added to the list
     * @param randomizer the randomizer function used to generate random indices
     */
    @DoNotTouch
    public RandomListIterative(@NotNull T[] elements, @NotNull BiFunction<Integer, Integer, Integer> randomizer) {
        super(elements, randomizer);
    }

    /**
     * Creates a new empty list with the given randomizer function.
     *
     * @param randomizer the randomizer function used to generate random indices
     */
    @DoNotTouch
    public RandomListIterative(@NotNull BiFunction<Integer, Integer, Integer> randomizer) {
        super(randomizer);
    }

    /**
     * Creates a new list with the given elements and a default randomizer function.
     *
     * @param elements the elements to be added to the list
     */
    @DoNotTouch
    public RandomListIterative(@NotNull T[] elements) {
        super(elements);
    }

    /**
     * Creates a new empty list with a default randomizer function.
     */
    @DoNotTouch
    public RandomListIterative() {
    }

    @StudentImplementationRequired("H11.3")
    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        // TODO H11.3
        checkBounds(index);

        if (index == 0) {
            return head.key;
        }

        int randomIndex = getRandomIndex(index);
        ListItem<T> randomPrev = null;
        ListItem<T> getPrev = null;
        ListItem<T> cursor = head;
        for (int i = 0; i < index; i++) {
            if (i == randomIndex - 1) {
                randomPrev = cursor;
            } else if (i == index - 1) {
                getPrev = cursor;
            }
            cursor = cursor.next;
        }

        // Get the item at the target position
        ListItem<T> item = getPrev.next;

        // Swap the elements between randomPrev and getPrev
        swap(randomPrev, getPrev);

        return item.key;
    }
}
