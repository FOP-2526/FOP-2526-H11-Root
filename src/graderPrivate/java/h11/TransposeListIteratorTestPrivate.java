package h11;

import h11.mocking.WhiteBoxTestUtilsP;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
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
import static org.mockito.Mockito.verify;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.emptyContext;

@TestForSubmission
public class TransposeListIteratorTestPrivate extends TransposeListIteratorTestPublic {

    @ParameterizedTest
    @MethodSource("provideTestVA")
    public void testVA(List<Object> invoked, int index, Object expectedElement, List<Object> expectedList)
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        var values = setupIterator(TransposeListIterator.class, invoked, 0);
        TransposeListIterator<Object> test = values.component1().component1();
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
