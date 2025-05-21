package h11;

import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.SolutionOnly;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * This class provides a skeletal implementation of the {@link SelfOrganizingList} interface to minimize the effort
 * required to implement this interface by implementing common operations.
 *
 * @param <T> the type of elements in this list
 *
 * @author Nhan Huynh
 */
@DoNotTouch
public abstract class AbstractSelfOrganizingList<T> implements SelfOrganizingList<T> {

    /**
     * The reference to the head (first element) of the list.
     */
    @DoNotTouch
    protected @Nullable ListItem<T> head;

    /**
     * The reference to the tail (last element) of the list.
     */
    @DoNotTouch
    protected @Nullable ListItem<T> tail;

    /**
     * The number of elements in the list.
     */
    @DoNotTouch
    private int size;

    /**
     * Creates an empty list.
     */
    @DoNotTouch
    public AbstractSelfOrganizingList() {

    }

    @DoNotTouch
    @Override
    public int size() {
        return size;
    }

    @DoNotTouch
    @Override
    public void addFirst(T key) {
        ListItem<T> newHead = new ListItem<>(key);
        newHead.next = head;
        if (tail == null) {
            tail = newHead;
        }
        head = newHead;
        size++;
    }

    @DoNotTouch
    @Override
    public void addLast(T key) {
        ListItem<T> newItem = new ListItem<>(key);
        if (tail == null) {
            head = newItem;
        } else {
            tail.next = newItem;
        }
        tail = newItem;
        size++;
    }

    @DoNotTouch
    @Override
    public void remove(T key) throws NoSuchElementException {
        if (head == null) {
            throw new NoSuchElementException();
        }
        if (head.key.equals(key)) {
            head = head.next;
            size--;
            return;
        }
        for (ListItem<T> cursor = head; cursor.next != null; cursor = cursor.next) {
            if (cursor.next.key.equals(key)) {
                if (cursor.next == tail) {
                    tail = cursor;
                }
                cursor.next = cursor.next.next;
                size--;
                return;
            }
        }
        throw new NoSuchElementException();
    }

    /**
     * Checks if the given index is within the bounds of the list.
     *
     * @param index the index to check
     *
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    @SolutionOnly
    protected void checkBounds(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException(index);
        }
    }

    @DoNotTouch
    @Override
    public BidirectionalIterator<T> iterator() {
        return new BidirectionalListIterator();
    }

    /**
     * An implementation of {@link BidirectionalIterator} that allows iterating over the list in both directions for
     * a {@link SelfOrganizingList}.
     */
    class BidirectionalListIterator implements BidirectionalIterator<T> {

        /**
         * The last element returned by a call to {@code next()} or {@code previous()}.
         * This field is used to determine whether a subsequent call to {@code remove()}
         * is valid. A call to {@code remove()} is only allowed if it directly follows a
         * call to {@code next()} or {@code previous()}, and it cannot be called twice in a row
         * without an intervening cursor movement.
         */
        @Nullable ListItem<T> lastReturned;

        /**
         * The cursor for the current position in the list.
         */
        @Nullable ListItem<T> cursor = head;

        /**
         * The previous references of the cursor for the reverse iteration.
         */
        @Nullable ListItem<ListItem<T>> previouses;

        @StudentImplementationRequired("H11.4.1")
        @Override
        public boolean hasPrevious() {
            // TODO H11.4.1
            return previouses != null;
        }

        @StudentImplementationRequired("H11.4.1")
        @Override
        public T previous() {
            // TODO H11.4.1
            // If we have no previous element, we cannot return anything
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            cursor = previouses.key;
            // Used to keep track of the last returned element for removal
            lastReturned = cursor;
            // Remove the previous element from the reverse cursor since we are moving back
            previouses = previouses.next;
            return cursor.key;
        }

        @StudentImplementationRequired("H11.4.1")
        @Override
        public boolean hasNext() {
            return cursor != null;
        }

        /**
         * Adds the given item to the previouses list for reverse iteration.
         *
         * @param item the item to add
         */
        @SolutionOnly
        private void addPrevious(ListItem<T> item) {
            // Stack-like behavior for the reverse cursor, elements are added to the front
            ListItem<ListItem<T>> previous = new ListItem<>(item);
            if (previouses != null) {
                previous.next = previouses;
            }
            previouses = previous;
        }

        @StudentImplementationRequired("H11.4.1")
        @Override
        public T next() {
            // TODO H11.4.1
            // If we have no next element, we cannot return anything
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            // Remember the previous element if we want to move back
            addPrevious(cursor);
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
            ListItem<ListItem<T>> previousCursor = previouses;
            // cursor == null only occurs when we are adding at the end of the list
            // previousCursor is used if we are adding multiple elements before moving to the next element
            // Therefore, we need to retrieve the correct insertion position
            while (cursor != null && previousCursor != null && !previousCursor.key.next.equals(cursor)) {
                previousCursor = previousCursor.next;
            }
            ListItem<T> newItem = new ListItem<>(element);
            addPrevious(newItem);

            if (cursor == null) {
                // Case: Adding to the end of the list
                ListItem<T> previousItem = previousCursor.key;
                previousItem.next = newItem;
                tail = newItem;
            } else if (previousCursor == null) {
                // Case: Adding to the front of the list
                newItem.next = head;
                head = newItem;
            } else {
                // Case: Adding to the middle of the list
                ListItem<T> previousItem = previousCursor.key;
                newItem.next = previousItem.next;
                previousItem.next = newItem;
            }
            // No removal available after adding an element
            lastReturned = null;
            size++;
        }

        @StudentImplementationRequired("H11.4.1")
        @Override
        public void remove() {
            // TODO H11.4.1
            // An element needs to be returned (present) before we can remove it
            if (lastReturned == null) {
                throw new IllegalStateException();
            }
            if (lastReturned == head) {
                // Case: Removing the head
                // Second element becomes the new head
                head = head.next;
                // Since the head does not contain a previous element, adjust the previouses
                previouses = null;
            } else if (lastReturned == tail) {
                // Case: Removing the last element
                ListItem<T> newTail = previouses.next.key;
                newTail.next = null;
                previouses = previouses.next;
                tail = newTail;
            } else {
                // Case: Removing an element in the middle
                // Previous of the removed element should skip the removed element
                ListItem<T> previousRemoveItem = previouses.next.key;
                previousRemoveItem.next = previousRemoveItem.next.next;
                previouses = previouses.next;
            }
            // No removal available after adding an element
            lastReturned = null;
            size--;
        }
    }

    @DoNotTouch
    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof AbstractSelfOrganizingList<?> that
            && size == that.size
            && Objects.equals(head, that.head)
            && Objects.equals(tail, that.tail);
    }

    @DoNotTouch
    @Override
    public int hashCode() {
        return Objects.hash(head, tail, size);
    }

    @DoNotTouch
    @Override
    public String toString() {
        if (head == null) {
            return "[]";
        }
        StringBuilder builder = new StringBuilder(size);
        builder.append("[");
        builder.append(head.key);
        for (ListItem<T> cursor = head.next; cursor != null; cursor = cursor.next) {
            builder.append(", ").append(cursor.key);
        }
        builder.append("]");
        return builder.toString();
    }
}
