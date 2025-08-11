package h11;

import h11.mocking.ReflectionUtilsP;
import kotlin.Pair;
import kotlin.Triple;
import org.mockito.stubbing.Answer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

public class IteratorTest extends H11_TestP {

    public static <T extends AbstractSelfOrganizingList> Triple<Pair<T, BidirectionalIterator<Object>>, Object,
        List<List<Object>>> setupIterator(
        Class<T> classToTest, List<Object> elements, int curserPos)
        throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        T list = spy(classToTest.getDeclaredConstructor(Object[].class).newInstance((Object) elements.toArray()));

        BidirectionalIterator<Object> iter = spy(list.iterator());

        List<List<Object>> interactions = new ArrayList<>();

        Answer<?> defaultAnswer = (mockInvocation) -> {
            System.out.println("Invoking " + mockInvocation.getMethod().getName() + " on " + System.identityHashCode(
                mockInvocation.getMock()) + " with list " + System.identityHashCode(list));

            List<Object> mapped = toList(list);
            ListIterator<Object> listIter = mapped.listIterator();

            ListItem<Object> currentCursor = ReflectionUtilsP.getFieldValue(iter, "previouses");
            int currentIndex = 0;
            while (currentCursor != null) {
                currentIndex++;
                currentCursor = currentCursor.next;
            }

            ReflectionUtilsP.setFieldValue(listIter, "cursor", currentIndex);
            ReflectionUtilsP.setFieldValue(listIter, "lastRet", currentIndex - 1);

            Object returned = ListIterator.class.getMethod(
                    mockInvocation.getMethod().getName(),
                    Arrays.stream(mockInvocation.getArguments()).map(ignored -> Object.class).toArray(Class[]::new)
                )
                .invoke(listIter, mockInvocation.getArguments());

            System.out.println("mapped: " + mapped);

            AbstractSelfOrganizingList<Object> data = new MoveToFrontListIterative<>(mapped.toArray());
            list.head = data.head;
            list.tail = data.tail;
            ReflectionUtilsP.setFieldValue(list, "size", data.size());

            System.out.println("actual: " + list);

            ListItem<Object> cursor = list.head;
            if (mockInvocation.getMethod().getName().equals("next") || mockInvocation.getMethod().getName().equals("previous")) {
                ListItem<Object> lastReturned = list.head;
                while (lastReturned != null && lastReturned.key != returned) {
                    lastReturned = lastReturned.next;
                }

                ReflectionUtilsP.setFieldValue(iter, "lastReturned", lastReturned);

                currentIndex = ReflectionUtilsP.getFieldValue(listIter, "cursor");

                while (currentIndex > 0 && cursor != null) {
                    cursor = cursor.next;
                    currentIndex--;
                }

                ReflectionUtilsP.setFieldValue(iter, "cursor", cursor);
            }
            if (mockInvocation.getMethod().getName().equals("add")) {
                ListItem<ListItem<Object>> previous = new ListItem<>(new ListItem<>(mockInvocation.getArguments()[0]));
                previous.next = ReflectionUtilsP.getFieldValue(iter, "previouses");
                ReflectionUtilsP.setFieldValue(iter, "previouses", previous);
            }
            if (mockInvocation.getMethod().getName().equals("next")) {
                ListItem<ListItem<Object>> previous = new ListItem<>(list.head);
                currentIndex = ReflectionUtilsP.<Integer>getFieldValue(listIter, "cursor") - 1;

                while (currentIndex > 0) {
                    ListItem<ListItem<Object>> next = new ListItem<>(previous.key.next);
                    next.next = previous;
                    previous = next;
                    currentIndex--;
                }
                ReflectionUtilsP.setFieldValue(iter, "previouses", previous);
            }
            if (mockInvocation.getMethod().getName().equals("add") || mockInvocation.getMethod().getName().equals("remove")) {
                ReflectionUtilsP.setFieldValue(iter, "lastReturned", null);
            }
            if (mockInvocation.getMethod().getName().equals("previous") || mockInvocation.getMethod()
                .getName()
                .equals("remove")) {
                ReflectionUtilsP.setFieldValue(
                    iter,
                    "previouses",
                    ReflectionUtilsP.<ListItem<Object>>getFieldValue(iter, "previouses").next
                );
            }

            interactions.add(mapped);

            System.out.println("actual end: " + list);
            return returned;
        };

        doAnswer(defaultAnswer).when(iter).hasPrevious();
        doAnswer(defaultAnswer).when(iter).previous();
        doAnswer(defaultAnswer).when(iter).hasNext();
        doAnswer(defaultAnswer).when(iter).next();
        doAnswer(defaultAnswer).when(iter).add(any());
        doAnswer(defaultAnswer).when(iter).remove();

        if (curserPos != 0) {
            ListItem<Object> cursor = list.head;
            while (curserPos > 0 && cursor != null) {
                ReflectionUtilsP.setFieldValue(iter, "lastReturned", cursor);
                ReflectionUtilsP.setFieldValue(iter, "cursor", cursor.next);

                ListItem<ListItem<Object>> previous = new ListItem<>(cursor);
                previous.next = ReflectionUtilsP.getFieldValue(iter, "previouses");
                ReflectionUtilsP.setFieldValue(iter, "previouses", previous);

                cursor = cursor.next;
                curserPos--;
            }
        }

        doReturn(iter).when(list).iterator();

        return new Triple<>(new Pair<>(list, iter), ReflectionUtilsP.getFieldValue(iter, "previouses"), interactions);
    }
}
