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
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.contextBuilder;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.emptyContext;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.fail;

@TestForSubmission
public class RandomListIteratorTest extends IteratorTest {

    @ParameterizedTest
    @MethodSource("provideTestGet")
    public void testGet(List<Object> invoked, int index, int randomIndex, Object expectedElement, List<Object> expectedList)
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        var values = setupIterator(RandomListIterator.class, invoked, 0);
        RandomListIterator<Object> test = values.component1().component1();

        doReturn(randomIndex).when(test).getRandomIndex(anyInt());

        Context context = contextBuilder()
            .add("invoked", invoked)
            .add("randomIndex", randomIndex)
            .add("index", index)
            .add("expectedElement", expectedElement)
            .add("expectedList", expectedList)
            .build();

        Object actualElement = test.get(index);

        assertEquals(expectedElement, actualElement, context, r -> "Element returned by get does not match expected!");
    }

    @ParameterizedTest
    @MethodSource("provideTestGet")
    public void testGet_index(List<Object> invoked, int index, int randomIndex, Object expectedElement, List<Object> expectedList)
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        var values = setupIterator(RandomListIterator.class, invoked, 0);
        RandomListIterator<Object> test = values.component1().component1();

        doReturn(randomIndex).when(test).getRandomIndex(anyInt());

        test.get(index);

        List<Object> testAfterGet = toList(test);

        Context context = contextBuilder()
            .add("invoked", invoked)
            .add("index", index)
            .add("randomIndex", randomIndex)
            .add("expectedElement", expectedElement)
            .add("expectedList", expectedList)
            .add("actualList", testAfterGet)
            .build();

        int indexAfterGet = testAfterGet.indexOf(invoked.get(index));

        if (indexAfterGet == -1) {
            fail(context, r -> "Element at index %s was not removed from list but not added back in.".formatted(index));
        }

        int indexPrev = Math.max(0, indexAfterGet - 1);
        int indexAft = Math.min(expectedList.size() - 1, indexAfterGet + 1);

        assertEquals(
            expectedList.get(indexPrev),
            testAfterGet.get(indexPrev),
            context,
            r -> "Element at index %s was not added to the correct position of the list. Tested index in resulting list: %s".formatted(
                index,
                indexPrev
            )
        );
        assertEquals(
            expectedList.get(indexAfterGet),
            testAfterGet.get(indexAfterGet),
            context,
            r -> "Element at index %s was not added to the correct position of the list. Tested index in resulting list: %s".formatted(
                index,
                indexAfterGet
            )
        );
        assertEquals(
            expectedList.get(indexAft),
            testAfterGet.get(indexAft),
            context,
            r -> "Element at index %s was not added to the correct position of the list. Tested index in resulting list: %s".formatted(
                index,
                indexAft
            )
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestGet")
    public void testGet_randomIndex(List<Object> invoked, int index, int randomIndex, Object expectedElement,
                                    List<Object> expectedList)
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        var values = setupIterator(RandomListIterator.class, invoked, 0);
        RandomListIterator<Object> test = values.component1().component1();

        doReturn(randomIndex).when(test).getRandomIndex(anyInt());

        test.get(index);

        List<Object> testAfterGet = toList(test);

        Context context = contextBuilder()
            .add("invoked", invoked)
            .add("index", index)
            .add("randomIndex", randomIndex)
            .add("expectedElement", expectedElement)
            .add("expectedList", expectedList)
            .add("actualList", testAfterGet)
            .build();

        int indexAfterGet = testAfterGet.indexOf(invoked.get(randomIndex));

        if (indexAfterGet == -1) {
            fail(
                context,
                r -> "Element at randomIndex (Index %s) was not removed from list but not added back in.".formatted(randomIndex)
            );
        }

        int indexPrev = Math.max(0, indexAfterGet - 1);
        int indexAft = Math.min(expectedList.size() - 1, indexAfterGet + 1);

        assertEquals(
            expectedList.get(indexPrev),
            testAfterGet.get(indexPrev),
            context,
            r -> "Element at randomIndex (Index %s) was not added to the correct position of the list.".formatted(randomIndex)
        );
        assertEquals(
            expectedList.get(indexAfterGet),
            testAfterGet.get(indexAfterGet),
            context,
            r -> "Element at randomIndex (Index %s) was not added to the correct position of the list.".formatted(randomIndex)
        );
        assertEquals(
            expectedList.get(indexAft),
            testAfterGet.get(indexAft),
            context,
            r -> "Element at randomIndex (Index %s) was not added to the correct position of the list.".formatted(randomIndex)
        );
    }

    public static Stream<Arguments> provideTestGet() {
        return Stream.of(
            Arguments.of(
                List.of(1, 2, 3, 4, 5, 6),
                3,
                1,
                4,
                List.of(1, 4, 3, 2, 5, 6)
            ),
            Arguments.of(
                List.of(1, 2, 3, 4, 5, 6),
                0,
                0,
                1,
                List.of(1, 2, 3, 4, 5, 6)
            ),
            Arguments.of(
                List.of(6, 5, 4, 3, 2, 1),
                0,
                0,
                6,
                List.of(6, 5, 4, 3, 2, 1)
            ),
            Arguments.of(
                List.of(1, 2, 3, 4, 5, 6),
                5,
                3,
                6,
                List.of(1, 2, 3, 6, 5, 4)
            ),
            Arguments.of(
                List.of(1, 2, 3, 4, 5, 6),
                0,
                0,
                1,
                List.of(1, 2, 3, 4, 5, 6)
            ),
            Arguments.of(
                List.of(1),
                0,
                0,
                1,
                List.of(1)
            )
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestVA")
    public void testVA(List<Object> invoked, int index, int randomIndex, Object expectedElement, List<Object> expectedList)
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        var values = setupIterator(RandomListIterator.class, invoked, 0);
        RandomListIterator<Object> test = values.component1().component1();
        BidirectionalIterator<Object> iterator = values.component1().component2();

        doReturn(randomIndex).when(test).getRandomIndex(anyInt());

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
                1,
                4,
                List.of(1, 4, 3, 2, 5, 6)
            ),
            Arguments.of(
                List.of(1, 2, 3, 4, 5, 6),
                5,
                4,
                6,
                List.of(1, 2, 3, 4, 6, 5)
            )
        );
    }

}
