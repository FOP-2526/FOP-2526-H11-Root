package h11;

import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.SpoonUtils;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import spoon.reflect.declaration.CtType;

import java.lang.reflect.Method;

@TestForSubmission
public class GeneralVATest {

    public static void testMethod(Method methodToTest) {

        Class<?> declaringClass = methodToTest.getDeclaringClass();
        if (declaringClass.getDeclaringClass() != null) {
            declaringClass = declaringClass.getDeclaringClass();
        }

        CtType<?> ctClass = SpoonUtils.getType(declaringClass.getName());
        if (declaringClass != methodToTest.getDeclaringClass()) {
            ctClass = ctClass.getNestedType(methodToTest.getDeclaringClass().getSimpleName());
        }

        String methodSource = ctClass.getMethods()
            .stream()
            .filter((m) -> m.getSimpleName().equals(methodToTest.getName()))
            .findFirst()
            .orElseThrow()
            .toStringDebug();

        if (methodSource.lines().anyMatch(it -> it.matches(".*new (h11\\.)?ListItem(<.*>)?\\s*\\(.*"))) {
            Assertions2.fail(Assertions2.emptyContext(), r -> "Method " + methodToTest.getName() + " created a new ListItem.");
        }
    }

    @Test
    public void testVARandomRec() throws NoSuchMethodException {
        testMethod(RandomListRecursive.class.getDeclaredMethod("get", int.class));
    }

    @Test
    public void testVAMoveRec() throws NoSuchMethodException {
        testMethod(MoveToFrontListRecursive.class.getDeclaredMethod("get", int.class));
    }

    @Test
    public void testVAMoveIter() throws NoSuchMethodException {
        testMethod(MoveToFrontListIterative.class.getDeclaredMethod("get", int.class));
    }

    @Test
    public void testVAMoveItera() throws NoSuchMethodException {
        testMethod(MoveToFrontListIterator.class.getDeclaredMethod("get", int.class));
    }

    @Test
    public void testVARandIter() throws NoSuchMethodException {
        testMethod(RandomListIterative.class.getDeclaredMethod("get", int.class));
    }

    @Test
    public void testVARandItera() throws NoSuchMethodException {
        testMethod(RandomListIterator.class.getDeclaredMethod("get", int.class));
    }

    @Test
    public void testVARandRec() throws NoSuchMethodException {
        testMethod(RandomListRecursive.class.getDeclaredMethod("get", int.class));
    }

    @Test
    public void testVATransIter() throws NoSuchMethodException {
        testMethod(TransposeListIterative.class.getDeclaredMethod("get", int.class));
    }

    @Test
    public void testVATransItera() throws NoSuchMethodException {
        testMethod(TransposeListIterator.class.getDeclaredMethod("get", int.class));
    }

    @Test
    public void testVATransRec() throws NoSuchMethodException {
        testMethod(TransposeListRecursive.class.getDeclaredMethod("get", int.class));
    }

    @Test
    public void testVAIterHasPrev() throws NoSuchMethodException {
        testMethod(BidirectionalListIterator.class.getDeclaredMethod("hasPrevious"));
    }

    @Test
    public void testVAIterPrev() throws NoSuchMethodException {
        testMethod(BidirectionalListIterator.class.getDeclaredMethod("previous"));
    }

    @Test
    public void testVAIterHasNext() throws NoSuchMethodException {
        testMethod(BidirectionalListIterator.class.getDeclaredMethod("hasNext"));
    }

    @Test
    public void testVAIterNext() throws NoSuchMethodException {
        testMethod(BidirectionalListIterator.class.getDeclaredMethod("next"));
    }

    @Test
    public void testVAIterRemove() throws NoSuchMethodException {
        testMethod(BidirectionalListIterator.class.getDeclaredMethod("remove"));
    }
}
