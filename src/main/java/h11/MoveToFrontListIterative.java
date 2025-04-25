package h11;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * An iterative implementation of a self-organizing list that moves accessed elements to the front of the list.
 *
 * @param <T> the type of elements in the list
 *
 * @author Nhan Huynh
 */
@DoNotTouch
public class MoveToFrontListIterative<T> extends MoveToFrontList<T> implements SelfOrganizingList<T> {

    /**
     * Creates a new empty list.
     */
    @DoNotTouch
    public MoveToFrontListIterative() {

    }

    @StudentImplementationRequired("H11.1")
    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        // TODO H11.1
        checkBounds(index);
        if (index == 0) {
            return head.key;
        }
        ListItem<T> cursor = head;
        for (int i = 1; i < index; i++) {
            cursor = cursor.next;
        }
        ListItem<T> newHead = cursor.next;
        moveToFront(cursor, newHead);
        return newHead.key;
    }
}
