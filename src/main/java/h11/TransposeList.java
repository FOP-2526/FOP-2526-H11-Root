package h11;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.SolutionOnly;

/**
 * A self-organizing list that transposes accessed elements with the previous element in the list moving it one
 * position to the front.
 *
 * @param <T> the type of elements in the list
 *
 * @author Nhan Huynh
 */
@DoNotTouch
public abstract class TransposeList<T> extends AbstractSelfOrganizingList<T> implements SelfOrganizingList<T> {

    /**
     * Creates a new empty list.
     */
    @DoNotTouch
    public TransposeList() {
    }

    /**
     * Transposes the accessed element with the previous element in the list moving it one position to the front.
     *
     * @param previous       the previous element in the list to adjust the references
     * @param previousBefore the element before the previous element in the list to switch places with previous
     */
    @SolutionOnly
    protected void transpose(ListItem<T> previous, ListItem<T> previousBefore) {
        if (previous.next == tail) {
            tail = previous;
        }

        if (previousBefore == null) {
            // Case: Swap head and head.next
            head = previous.next;
            previous.next = head.next;
            head.next = previous;
        } else {
            // Case: Move the node one position to the front
            previousBefore.next = previous.next;
            previous.next = previous.next.next;
            previousBefore.next.next = previous;
        }
    }

    @DoNotTouch
    @Override
    public Strategy strategy() {
        return Strategy.TRANSPOSE;
    }
}
