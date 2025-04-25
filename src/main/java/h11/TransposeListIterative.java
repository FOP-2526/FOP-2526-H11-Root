package h11;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * An iterative implementation of a self-organizing list that transposes accessed elements with their previous element.
 *
 * @param <T> the type of elements in the list
 *
 * @author Nhan Huynh
 */
@DoNotTouch
public class TransposeListIterative<T> extends TransposeList<T> implements SelfOrganizingList<T> {

    /**
     * Creates a new empty list.
     */
    @DoNotTouch
    public TransposeListIterative() {
    }

    @StudentImplementationRequired("H11.2")
    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        checkBounds(index);
        if (index == 0) {
            return head.key;
        }
        ListItem<T> cursor = head;
        ListItem<T> previous = null;
        for (int i = 1; i < index; i++) {
            previous = cursor;
            cursor = cursor.next;
        }
        transpose(cursor, previous);
        return cursor.key;
    }
}
