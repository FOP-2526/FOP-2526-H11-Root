package h11;

import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions4;
import org.tudalgo.algoutils.tutor.general.reflections.BasicMethodLink;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.emptyContext;

@TestForSubmission
public class MoveToFrontListIterativeTestPrivate extends MoveToFrontListIterativeTestPublic {

    @Test
    public void testVA() throws NoSuchMethodException {
        Assertions4.assertIsNotRecursively(
            BasicMethodLink.of(MoveToFrontListIterative.class.getDeclaredMethod("get", int.class))
                .getCtElement(), emptyContext(), r -> "get() contains recursion"
        );
    }
}
