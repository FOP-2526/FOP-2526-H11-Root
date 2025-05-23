package h11;

import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * An iterator-based implementation of a self-organizing list that transposes accessed elements with their previous element.
 *
 * @param <T> the type of elements in the list
 *
 * @author Nhan Huynh
 */
@DoNotTouch
public class TransposeListIterator<T> extends TransposeList<T> implements SelfOrganizingList<T> {

    /**
     * Creates a new empty list.
     */
    @DoNotTouch
    public TransposeListIterator() {
    }

    /**
     * Creates a list with the given elements.
     *
     * @param elements the elements to be added to the list
     */
    @DoNotTouch
    public TransposeListIterator(@NotNull T[] elements) {
        super(elements);
    }

    @StudentImplementationRequired("H11.4.3")
    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        // TODO H11.4.3
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
