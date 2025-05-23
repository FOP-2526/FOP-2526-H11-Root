package h11;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.SolutionOnly;
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

    /**
     * Creates a list with the given elements.
     *
     * @param elements the elements to be added to the list
     */
    @DoNotTouch
    public TransposeListRecursive(@NotNull T[] elements) {
        super(elements);
    }

    @StudentImplementationRequired("H11.2")
    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        // TODO H11.2
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
    @SolutionOnly
    private T get(@NotNull ListItem<T> previous, @Nullable ListItem<T> beforePrevious, int index) {
        if (index == 1) {
            transpose(previous, beforePrevious);
            return previous.key;
        }
        return get(previous.next, previous, index - 1);
    }
}
