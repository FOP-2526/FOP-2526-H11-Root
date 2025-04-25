package h11;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.util.function.BiFunction;

/**
 * An iterator-based implementation of a self-organizing list that moves accessed elements to a random position in the
 * list based on a random index which is smaller than the position of the accessed element.
 *
 * @param <T> the type of elements in the list
 *
 * @author Nhan Huynh
 */
@DoNotTouch
public class RandomListIterator<T> extends RandomList<T> implements SelfOrganizingList<T> {

    /**
     * Creates a new empty list with the given randomizer function.
     *
     * @param randomizer the randomizer function used to generate random indices
     */
    @DoNotTouch
    public RandomListIterator(BiFunction<Integer, Integer, Integer> randomizer) {
        super(randomizer);
    }

    /**
     * Creates a new empty list with a default randomizer function.
     */
    @DoNotTouch
    public RandomListIterator() {

    }

    @StudentImplementationRequired("H11.4.4")
    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        checkBounds(index);

        // Special case when the list is just one element
        if (index == 0) {
            return head.key;
        }

        BidirectionalIterator<T> it = iterator();
        T randomElement = null;

        // Traverse to the random index position
        int randomIndex = getRandomIndex(index);
        for (int i = 0; i <= randomIndex; i++) {
            randomElement = it.next();
        }
        it.remove();

        // Traverse to the nextIndex position
        int nextIndex = index - randomIndex;
        T actualElement = null;
        for (int i = 0; i < nextIndex; i++) {
            actualElement = it.next();
        }
        it.remove();

        // Add the random element back to the list at the new position
        it.add(randomElement);

        // Move backwards to the original position of the actual element and add the actual element back to the list
        for (int i = 0; i < nextIndex; i++) {
            it.previous();
        }
        it.add(actualElement);

        return actualElement;
    }
}
