package h11;

import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.util.Objects;

/**
 * A list item wraps a single element in a linked list data structure to allow linking of multiple items together.
 *
 * @param <T> the type of the key
 *
 * @author Nhan Huynh
 */
@DoNotTouch
public final class ListItem<T> {

    /**
     * The key of the list item.
     */
    @DoNotTouch
    T key;

    /**
     * The reference to the next element in the list.
     */
    @DoNotTouch
    @Nullable
    ListItem<T> next;

    /**
     * Creates an empty list item.
     */
    @DoNotTouch
    public ListItem() {

    }

    /**
     * Creates a list item with the given key.
     *
     * @param key the key of the list item
     */
    @DoNotTouch
    public ListItem(T key) {
        this.key = key;
    }

    @DoNotTouch
    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof ListItem<?> listItem
            && Objects.equals(key, listItem.key)
            && Objects.equals(next, listItem.next);
    }

    @DoNotTouch
    @Override
    public int hashCode() {
        return Objects.hash(key, next);
    }

    @DoNotTouch
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[").append(key).append("|");
        ListItem<T> cursor = next;
        while (cursor != null) {
            builder.append(cursor.key);
            builder.append(" -> ");
            cursor = cursor.next;
        }
        builder.append("null]");
        return builder.toString();
    }
}
