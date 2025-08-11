package h11;

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
    private final AbstractSelfOrganizingList<T> list;

    /**
     * The last element returned by a call to {@code next()} or {@code previous()}.
     * This field is used to determine whether a subsequent call to {@code remove()}
     * is valid. A call to {@code remove()} is only allowed if it directly follows a
     * call to {@code next()} or {@code previous()}, and it cannot be called twice in a row
     * without an intervening cursor movement.
     * In case of a call to {@code add()}, this field is set to {@code null} since no removal is possible after adding
     * an element.
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
    BidirectionalListIterator(AbstractSelfOrganizingList<T> list) {
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
        previous.next = previouses;
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
        while (cursor != null && previousCursor != null && previousCursor.key.next != cursor) {
            previousCursor = previousCursor.next;
        }
        ListItem<T> newItem = new ListItem<>(element);
        addPrevious(newItem);

        if (previousCursor == null) {
            // Case: Adding to the front of the list
            newItem.next = list.head;
            list.head = newItem;
        } else if (cursor == null) {
            // Case: Adding to the end of the list
            ListItem<T> previousItem = previousCursor.key;
            previousItem.next = newItem;
            list.tail = newItem;
        } else {
            // Case: Adding to the middle of the list
            ListItem<T> previousItem = previousCursor.key;
            newItem.next = previousItem.next;
            previousItem.next = newItem;
        }
        // No removal available after adding an element
        lastReturned = null;
        list.size++;
    }

    @StudentImplementationRequired("H11.4.1")
    @Override
    public void remove() {
        // TODO H11.4.1
        // An element needs to be returned (present) before we can remove it
        if (lastReturned == null) {
            throw new IllegalStateException();
        }
        if (lastReturned == list.head) {
            // Case: Removing the head
            // Second element becomes the new head
            list.head = list.head.next;
            // Since the head does not contain a previous element, adjust the previouses
            previouses = null;
        } else if (lastReturned == list.tail) {
            // Case: Removing the last element
            ListItem<T> newTail = previouses.next.key;
            newTail.next = null;
            previouses = previouses.next;
            list.tail = newTail;
        } else {
            // Case: Removing an element in the middle
            // Previous of the removed element should skip the removed element
            ListItem<T> previousRemoveItem = previouses.next.key;
            previousRemoveItem.next = previousRemoveItem.next.next;
            previouses = previouses.next;
        }
        // No removal available after adding an element
        lastReturned = null;
        list.size--;
    }
}


