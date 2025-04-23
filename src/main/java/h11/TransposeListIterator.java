package h11;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * An iterator-based implementation of a self-organizing list that transposes accessed elements with their previous element.
 *
 * @param <T> the type of elements in the list
 */
public class TransposeListIterator<T> extends TransposeList<T> implements SelfOrganizingList<T> {

    /**
     * Creates a new empty list.
     */
    @DoNotTouch
    public TransposeListIterator() {
    }

    @StudentImplementationRequired
    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        checkBounds(index);
        BidirectionalIterator<T> it = iterator();
        if (index == 0) {
            return it.next();
        }
        T element = null;
        for (int i = 0; i <= index; i++) {
            element = it.next();
        }
        it.remove();
        it.previous();
        it.add(element);
        return element;
    }
}
