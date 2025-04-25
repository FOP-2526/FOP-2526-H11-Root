package h11;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * A recursive implementation of a self-organizing list that transposes accessed elements with their previous element.
 *
 * @param <T> the type of elements in the list
 *
 * @author Nhan Huynh
 */
@DoNotTouch
public class TransposeListRecursive<T> extends TransposeList<T> implements SelfOrganizingList<T> {

    /**
     * Creates a new empty list.
     */
    @DoNotTouch
    public TransposeListRecursive() {

    }

    @StudentImplementationRequired("H11.2")
    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        checkBounds(index);
        if (index == 0) {
            return head.key;
        }
        return get(head, null, index);
    }

    /**
     * Recursively retrieves the element at the specified index and transposes it with its previous element.
     *
     * @param previous       the previous element in the list to adjust the references
     * @param beforePrevious the element before the previous element in the list to switch places with previous
     * @param index          the index of the element to retrieve
     *
     * @return the element at the specified index
     */
    private T get(ListItem<T> previous, ListItem<T> beforePrevious, int index) {
        if (index == 1) {
            transpose(previous, beforePrevious);
            return previous.key;
        }
        return get(previous.next, previous, index - 1);
    }
}
