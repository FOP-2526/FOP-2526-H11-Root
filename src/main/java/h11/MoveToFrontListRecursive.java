package h11;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.SolutionOnly;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * A recursive implementation of a self-organizing list that moves accessed elements to the front of the list.
 *
 * @param <T> the type of elements in the list
 *
 * @author Nhan Huynh
 */
@DoNotTouch
public class MoveToFrontListRecursive<T> extends MoveToFrontList<T> implements SelfOrganizingList<T> {

    /**
     * Creates a new empty list.
     */
    @DoNotTouch
    public MoveToFrontListRecursive() {

    }

    @StudentImplementationRequired("H11.1")
    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        // TODO H11.1
        checkBounds(index);
        if (index == 0) {
            return head.key;
        }
        return get(head, index);
    }

    /**
     * Recursively retrieves the element at the specified index and moves it to the front of the list.
     *
     * @param previous the previous element in the list to adjust the references
     * @param index    the index of the item to retrieve
     *
     * @return the element at the specified index
     */
    @SolutionOnly
    private T get(ListItem<T> previous, int index) {
        if (index == 1) {
            ListItem<T> newHead = previous.next;
            moveToFront(previous, newHead);
            return newHead.key;
        }
        return get(previous.next, index - 1);  // Recurse to the next item
    }
}
