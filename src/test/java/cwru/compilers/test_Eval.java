package cwru.compilers;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

public class test_Eval {

    @Test
    public void test_int() throws Eval.EvalError {
        assertThat(Eval.evaluate(new Node(5)), is(5));
    }

    @Test
    public void test_plus() throws Eval.EvalError {
        assertThat(Eval.evaluate(
          (new Node("+")).addkid(new Node(2)).addkid(new Node(3))),
          is(5)
        );
    }

    @Test
    public void test_minus() throws Eval.EvalError {
        assertThat(Eval.evaluate(
          (new Node("-")).addkid(new Node(2)).addkid(new Node(3))),
          is(-1)
        );
    }

    @Test
    public void test_times() throws Eval.EvalError {
        assertThat(Eval.evaluate(
          (new Node("*")).addkid(new Node(2)).addkid(new Node(3))),
          is(6)
        );
    }

    @Test
    public void test_div() throws Eval.EvalError {
        assertThat(Eval.evaluate(
          (new Node("/")).addkid(new Node(15)).addkid(new Node(3))),
          is(5)
        );
    }

    @Test(expected = java.lang.ClassCastException.class)
    public void test_badint() throws Eval.EvalError {
        assertThat(Eval.evaluate(new Node("wizard")), is(5));
    }

    @Test(expected = java.lang.ClassCastException.class)
    public void test_badop() throws Eval.EvalError {
        assertThat(Eval.evaluate(
          (new Node(7)).addkid(new Node(5)).addkid(new Node(5))),
          is(5)
        );
    }

    @Test(expected = Eval.EvalError.class)
    public void test_not_enough() throws Eval.EvalError {
        assertThat(Eval.evaluate(
          (new Node("+")).addkid(new Node(5))),
          is(5)
        );
    }
}

