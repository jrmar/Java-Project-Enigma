package enigma;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import static enigma.TestUtils.*;

/** The suite of all JUnit tests for the Permutation class.
 *  @author mr
 */
public class PermutationTest {

    /** Testing time limit. */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    /* ***** TESTING UTILITIES ***** */

    private Permutation perm;
    private String alpha = UPPER_STRING;

    /** Check that perm has an alphabet whose size is that of
     *  FROMALPHA and TOALPHA and that maps each character of
     *  FROMALPHA to the corresponding character of FROMALPHA, and
     *  vice-versa. TESTID is used in error messages. */
    private void checkPerm(String testId,
                           String fromAlpha, String toAlpha) {
        int N = fromAlpha.length();
        assertEquals(testId + " (wrong length)", N, perm.size());
        for (int i = 0; i < N; i += 1) {
            char c = fromAlpha.charAt(i), e = toAlpha.charAt(i);
            assertEquals(msg(testId, "wrong translation of '%c'", c),
                         e, perm.permute(c));
            assertEquals(msg(testId, "wrong inverse of '%c'", e),
                         c, perm.invert(e));
            int ci = alpha.indexOf(c), ei = alpha.indexOf(e);
            assertEquals(msg(testId, "wrong translation of %d", ci),
                         ei, perm.permute(ci));
            assertEquals(msg(testId, "wrong inverse of %d", ei),
                         ci, perm.invert(ei));
        }
    }

    /* ***** TESTS ***** */

    @Test
    public void checkIdTransform() {
        perm = new Permutation("", UPPER);
        checkPerm("identity", UPPER_STRING, UPPER_STRING);
    }


    @Test
    public void checkPermuteTest() {
        perm = new Permutation("(ABC)(ED)", UPPER);
        assertEquals(1, perm.permute(0));
    }

    @Test
    public void testInvertChar() {
        Permutation p = new Permutation("(BACD)", UPPER);
        assertEquals('B', p.invert('A'));
        assertEquals('D', p.invert('B'));

        Permutation p2 = new Permutation("(AB)(CD)", UPPER);
        assertEquals('A', p2.invert('B'));
        assertEquals('C', p2.invert('D'));
    }

    @Test
    public void testSize() {
        Permutation p = new Permutation("(BACD)", new Alphabet("ABCD"));
        assertEquals(4, p.size());
        Permutation p2 = new Permutation("(BACD)", new Alphabet("ABCDEFGH"));
        assertEquals(8, p2.size());
    }
}
