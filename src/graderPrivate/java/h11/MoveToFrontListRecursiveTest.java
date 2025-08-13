package h11;

import h11.mocking.WhiteBoxTestUtilsP;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.reflections.BasicMethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.BasicPackageLink;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;
import spoon.reflect.code.CtLoop;

import java.util.List;
import java.util.stream.Stream;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.contextBuilder;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.emptyContext;

@TestForSubmission
public class MoveToFrontListRecursiveTest extends H11_TestP {

    @ParameterizedTest
    @MethodSource("provideTestGet")
    public void testGet(List<Object> invoked, int index, Object expectedElement, List<Object> expectedList) {
        MoveToFrontListRecursive<?> test = new MoveToFrontListRecursive<>(invoked.toArray());

        Context context = contextBuilder()
            .add("invoked", invoked)
            .add("index", index)
            .add("expectedElement", expectedElement)
            .add("expectedList", expectedList)
            .build();

        Object actualElement = test.get(index);

        assertEquals(expectedList, toList(test), context, r -> "List does not match expected!");
    }

    @ParameterizedTest
    @MethodSource("provideTestGet")
    public void testGet_complete(List<Object> invoked, int index, Object expectedElement, List<Object> expectedList) {
        MoveToFrontListRecursive<?> test = new MoveToFrontListRecursive<>(invoked.toArray());

        Context context = contextBuilder()
            .add("invoked", invoked)
            .add("index", index)
            .add("expectedElement", expectedElement)
            .add("expectedList", expectedList)
            .build();

        Object actualElement = test.get(index);
        assertEquals(expectedElement, actualElement, context, r -> "Element returned by get does not match expected!");

        assertEquals(expectedList, toList(test), context, r -> "List does not match expected!");
    }

    public static Stream<Arguments> provideTestGet() {
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

    @Test
    public void testVA() throws NoSuchMethodException {
        List<CtLoop> loops = WhiteBoxTestUtilsP.getCtElements(
            (List) BasicPackageLink.of("h11")
                .getTypes()
                .stream()
                .map(TypeLink::reflection)
                .map(it -> it)
                .toList(),
            CtLoop.class,
            BasicMethodLink.of(MoveToFrontListRecursive.class.getDeclaredMethod("get", int.class)).getCtElement()
        ).toList();
        assertEquals(0, loops.size(), emptyContext(), r -> "get() contains loops.");
    }


}
