package h11;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.SolutionOnly;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.util.NoSuchElementException;

/**
 * An implementation of {@link BidirectionalIterator} that allows iterating over the list in both directions for
 * a {@link SelfOrganizingList}.
 */
class BidirectionalListIterator<T> implements BidirectionalIterator<T> {

    /**
     * The list to iterate over.
     */
    private final @NotNull AbstractSelfOrganizingList<T> list;

    /**
     * The last element returned by a call to {@code next()} or {@code previous()}.
     * This field is used to determine whether a subsequent call to {@code remove()}
     * is valid. A call to {@code remove()} is only allowed if it directly follows
     * a call to {@code next()} or {@code previous()}, and it cannot be called twice
     * in a row without an intervening cursor movement.
     * <p>
     * After a call to {@code add()}, this field is set to {@code null} to prevent
     * an immediate {@code remove()}. After a call to {@code remove()}, it is also
     * set to {@code null} because the previously returned element no longer exists
     * in the list.
     */
    @Nullable ListItem<T> lastReturned;

    /**
     * The cursor for the current position in the list.
     */
    @Nullable ListItem<T> cursor;

    /**
     * The previous references of the cursor for the reverse iteration.
     */
    @Nullable ListItem<ListItem<T>> previouses;

    /**
     * Creates a new iterator for the given list.
     *
     * @param list the list to iterate over
     */
    BidirectionalListIterator(@NotNull AbstractSelfOrganizingList<T> list) {
        this.list = list;
        this.cursor = list.head;
    }

    @StudentImplementationRequired("H11.4.1")
    @Override
    public boolean hasPrevious() {
        // TODO H11.4.1
        return previouses != null;
    }

    @StudentImplementationRequired("H11.4.1")
    @Override
    public T previous() throws  NoSuchElementException {
        // TODO H11.4.1
        // If we have no previous element, we cannot return anything
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }

        ListItem<T> previous = previouses.key;
        // Remove the previous element from the reverse cursor since we are moving back (pop operation)
        previouses = previouses.next;
        // Move the cursor to the previous element since we are moving back
        cursor = previous;
        // Used to keep track of the last returned element for removal
        lastReturned = previous;
        return previous.key;
    }

    @StudentImplementationRequired("H11.4.1")
    @Override
    public boolean hasNext() {
        return cursor != null;
    }

    /**
     * Adds the given item to the beginning of the previouses list for reverse iteration.
     *
     * @param item the item to add
     */
    @SolutionOnly
    private void addFirstPrevious(ListItem<T> item) {
        // Stack-like behavior for the reverse cursor, elements are added to the front
        ListItem<ListItem<T>> top = new ListItem<>(item);
        top.next = previouses;
        previouses = top;
    }

    @StudentImplementationRequired("H11.4.1")
    @Override
    public T next() throws  NoSuchElementException {
        // TODO H11.4.1
        // If we have no next element, we cannot return anything
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        // Remember the previous element if we want to move back
        addFirstPrevious(cursor);
        // Used to keep track of the last returned element for removal
        lastReturned = cursor;
        // Move the cursor to the next element since we are moving forward
        cursor = cursor.next;
        return lastReturned.key;
    }

    @StudentImplementationRequired("H11.4.1")
    @Override
    public void add(T element) {
        // TODO H11.4.1
        ListItem<T> newItem = new ListItem<>(element);

        // Predecessor of the insertion point is top(previouses), if available.
        // If previouses is null, we are inserting at the beginning of the list.
        ListItem<T> parent = (previouses == null) ? null : previouses.key;

        if (parent == null) {
            newItem.next = list.head;
            list.head = newItem;
        } else {
            // Insertion after parent
            newItem.next = cursor;
            parent.next = newItem;

        }
        if (cursor == null) {
            // If we are inserting at the end of the list, we need to update the tail
            list.tail = newItem;
        }

        // New added item is now the previous for the next call to previous()
        addFirstPrevious(newItem);
        // No removal available after adding an element
        lastReturned = null;
        list.size++;
    }

    @StudentImplementationRequired("H11.4.1")
    @Override
    public void remove() throws IllegalStateException{
        // TODO H11.4.1
        // An element needs to be returned (present) before we can remove it
        if (lastReturned == null) {
            throw new IllegalStateException();
        }

        // We need the predecessor of lastReturned to unlink it.
        // Distinguish whether lastReturned came from previous() (then cursor == lastReturned)
        // or from next() (then cursor != lastReturned).
        boolean lastWasFromPrevious = (cursor == lastReturned);

        // lastNext for the iterator position
        ListItem<T> lastNext = lastReturned.next;

        // Predecessor of lastReturned in the list.
        ListItem<T> parent;

        if (lastWasFromPrevious) {
            /*
             * Example list:
             *   head → [A] → [B] → [C] → [D] → null
             *
             * Suppose the iterator had already moved forward to C:
             *   cursor       = D
             *   lastReturned = C
             *   previouses   = [C] (top), [B], [A]
             *
             * After calling previous():    // step back to B
             *   cursor       = B
             *   lastReturned = B
             *   previouses   = [A] (top)
             *
             * Before calling remove():
             *   cursor       = B
             *   lastReturned = B
             *   previouses   = [A] (top)
             *
             * After calling remove():
             *   head         = A
             *   cursor       = C         // skips over removed element B
             *   lastReturned = null
             *   previouses   = [A] (top) // unchanged top = predecessor
             *
             * => previous(): predecessor is on top of the stack.
             *    Removal links predecessor (A) to lastReturned.next (C).
             */
            parent = (previouses == null) ? null : previouses.key;

            // Unlink lastReturned
            if (parent == null) {
                // Remove from head since there is no predecessor
                list.head = lastNext;
            } else {
                parent.next = lastNext;
            }

            cursor = lastNext;
        } else {
            /*
             * Example list:
             *   head → [A] → [B] → [C] → [D] → null
             *
             * Iterator has just moved forward to B:
             *   cursor       = C
             *   lastReturned = B
             *   previouses   = [B] (top), [A]
             *
             * Before calling remove():
             *   cursor       = C
             *   lastReturned = B
             *   previouses   = [B] (top), [A]
             *
             * After calling remove():
             *   head         = A
             *   cursor       = C         // stays at next element
             *   lastReturned = null
             *   previouses   = [A] (top) // top (B) was popped since it was removed
             *
             * => next(): lastReturned is on top of the stack.
             *    The predecessor is directly below top (A),
             *    and is linked to lastReturned.next (C).
             */

            parent = (previouses.next == null) ? null : previouses.next.key;
            // Unlink lastReturned
            if (parent == null) {
                // Remove from head since there is no predecessor
                list.head = lastNext;
            } else {
                parent.next = lastNext;
            }

            // Pop the top (== lastReturned) from the predecessor stack since this element was removed.
            if (previouses.key == lastReturned) {
                previouses = previouses.next;
            }
        }

        if (lastReturned == list.tail) {
            // If lastReturned was the tail, update the tail to the predecessor
            list.tail = parent;
        }

        lastReturned = null;
        list.size--;
    }
}


