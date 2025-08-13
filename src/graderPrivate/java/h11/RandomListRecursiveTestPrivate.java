package h11;

import h11.mocking.WhiteBoxTestUtilsP;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.reflections.BasicMethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.BasicPackageLink;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;
import spoon.reflect.code.CtLoop;

import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.emptyContext;

@TestForSubmission
public class RandomListRecursiveTestPrivate extends RandomListRecursiveTestPublic {

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
            BasicMethodLink.of(RandomListRecursive.class.getDeclaredMethod("get", int.class)).getCtElement()
        ).toList();
        assertEquals(0, loops.size(), emptyContext(), r -> "get() contains loops.");
    }

}
