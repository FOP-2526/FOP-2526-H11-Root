package h11;

import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.SolutionOnly;

/**
 * A self-organizing list that moves accessed elements to the front of the list.
 *
 * @param <T> the type of elements in the list
 *
 * @author Nhan Huynh
 */
@DoNotTouch
public abstract class MoveToFrontList<T> extends AbstractSelfOrganizingList<T> implements SelfOrganizingList<T> {

    /**
     * Creates a new empty list.
     */
    @DoNotTouch
    public MoveToFrontList() {
    }

    /**
     * Creates a list with the given elements.
     *
     * @param elements the elements to be added to the list
     */
    @DoNotTouch
    public MoveToFrontList(@NotNull T[] elements) {
        super(elements);
    }

    /**
     * Moves the accessed element to the front of the list.
     *
     * @param previous the previous element in the list to adjust the references
     * @param newHead  the new head of the list
     */
    @SolutionOnly
    protected void moveToFront(@NotNull ListItem<T> previous, @NotNull ListItem<T> newHead) {
        if (newHead == tail) {
            tail = previous;
        }
        previous.next = newHead.next;
        newHead.next = head;
        head = newHead;
    }

    @DoNotTouch
    @Override
    public @NotNull Strategy strategy() {
        return Strategy.MOVE_TO_FRONT;
    }
}
