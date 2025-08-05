package h11;

import h11.mocking.WhiteBoxTestUtilsP;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.reflections.BasicMethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.BasicPackageLink;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;
import spoon.reflect.code.CtForEach;
import spoon.reflect.code.CtLoop;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.contextBuilder;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.emptyContext;

@TestForSubmission
public class MoveToFrontListIteratorTest extends IteratorTest {

    @ParameterizedTest
    @MethodSource("provideTestGet_add")
    public void testGet_add(List<Object> invoked, int index, Object expectedElement, List<Object> expectedList)
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        MoveToFrontListIterator<?> test = setupIterator(MoveToFrontListIterator.class, invoked, 0).component1().component1();

        Context context = contextBuilder()
            .add("invoked", invoked)
            .add("index", index)
            .add("expectedElement", expectedElement)
            .add("expectedList", expectedList)
            .build();

        Object actualElement = test.get(index);

        assertEquals(
            expectedList.getFirst(),
            toList(test).getFirst(),
            context,
            r -> "Element was not corretly added to the front!"
        );
    }

    public static Stream<Arguments> provideTestGet_add() {
        return Stream.of(
            Arguments.of(
                List.of(1, 2, 3, 4, 5, 6),
                3,
                4,
                List.of(4, 1, 2, 3, 5, 6)
            ),
            Arguments.of(
                List.of(1, 2, 3, 4, 5, 6),
                0,
                1,
                List.of(1, 2, 3, 4, 5, 6)
            ),
            Arguments.of(
                List.of(6, 5, 4, 3, 2, 1),
                0,
                6,
                List.of(6, 5, 4, 3, 2, 1)
            ),
            Arguments.of(
                List.of(1, 2, 3, 4, 5, 6),
                5,
                6,
                List.of(6, 1, 2, 3, 4, 5)
            ),
            Arguments.of(
                List.of(1),
                0,
                1,
                List.of(1)
            )
        );


    }

    @ParameterizedTest
    @MethodSource("provideTestGet_remove")
    public void testGet_remove(List<Object> invoked, int index, Object expectedElement, List<Object> expectedList)
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        MoveToFrontListIterator<Integer> test =
            setupIterator(MoveToFrontListIterator.class, invoked, 0).component1().component1();

        Context context = contextBuilder()
            .add("invoked", invoked)
            .add("index", index)
            .add("expectedElement", expectedElement)
            .add("expectedList", expectedList)
            .build();

        Object actualElement = test.get(index);

        List<Integer> sorted = toList(test).subList(1, test.size());
        Collections.sort(sorted);
        assertEquals(sorted, toList(test).subList(1, test.size()), context, r -> "The element was not removed correctly!");
    }

    public static Stream<Arguments> provideTestGet_remove() {
        return Stream.of(
            Arguments.of(
                List.of(1, 2, 3, 100, 5, 6),
                3,
                100,
                List.of(100, 1, 2, 3, 5, 6)
            ),
            Arguments.of(
                List.of(100, 2, 3, 4, 5, 6),
                0,
                100,
                List.of(100, 2, 3, 4, 5, 6)
            ),
            Arguments.of(
                List.of(100, 41, 42, 43, 44, 45),
                0,
                100,
                List.of(100, 41, 42, 43, 44, 45)
            ),
            Arguments.of(
                List.of(1, 2, 3, 4, 5, 0),
                5,
                0,
                List.of(0, 1, 2, 3, 4, 5)
            ),
            Arguments.of(
                List.of(1, 2),
                0,
                1,
                List.of(1, 2)
            )
        );


    }

    @ParameterizedTest
    @MethodSource("provideTestVA")
    public void testVA(List<Object> invoked, int index, Object expectedElement, List<Object> expectedList)
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        var values = setupIterator(MoveToFrontListIterator.class, invoked, 0);
        MoveToFrontListIterator<Object> test = values.component1().component1();
        BidirectionalIterator<Object> iterator = values.component1().component2();

        Object actualElement = test.get(index);

        verify(iterator, atLeastOnce()).remove();
        verify(iterator, atLeastOnce()).add(anyInt());
        verify(iterator, atLeastOnce()).next();
        verify(iterator, atLeastOnce()).previous();

        List<CtLoop> loops = WhiteBoxTestUtilsP.getCtElements(
            (List) BasicPackageLink.of("h11")
                .getTypes()
                .stream()
                .map(TypeLink::reflection)
                .map(it -> it)
                .toList(),
            CtForEach.class,
            BasicMethodLink.of(MoveToFrontListRecursive.class.getDeclaredMethod("get", int.class)).getCtElement()
        ).toList();
        assertEquals(0, loops.size(), emptyContext(), r -> "get() contains loops.");

    }

    public static Stream<Arguments> provideTestVA() {
        return Stream.of(
            Arguments.of(
                List.of(1, 2, 3, 4, 5, 6),
                3,
                4,
                List.of(1, 3, 5, 6)
            ),
            Arguments.of(
                List.of(1, 2, 3, 4, 5, 6),
                5,
                6,
                List.of(1, 2, 3, 4, 6, 5)
            )
        );
    }
}
