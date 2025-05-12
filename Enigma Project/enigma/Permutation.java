package enigma;

import static enigma.EnigmaException.*;

/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *  @author mr
 */
class Permutation {
    /** String cycles. */
    private String _cycles;

    /** Set this Permutation to that specified by CYCLES, a string in the
     *  form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     *  is interpreted as a permutation in cycle notation.  Characters in the
     *  alphabet that are not included in any cycle map to themselves.
     *  Whitespace is ignored. */
    Permutation(String cycles, Alphabet alphabet) {
        _alphabet = alphabet;
        _cycles = cycles;
        boolean holder = false;
        int i = 0;
        while (i < _alphabet.size()) {
            int j = 0;
            while (j < _cycles.length()) {
                if (_alphabet.toChar(i) != _cycles.charAt(j)) {
                    j++;
                    continue;
                }
                holder = true;
                j++;
            }
            if (!holder) {
                addCycle(String.valueOf(_alphabet.toChar(i)));
            }
            holder = false;
            i++;
        }
    }

    /** Add the cycle c0->c1->...->cm->c0 to the permutation, where CYCLE is
     *  c0c1...cm. */
    private void addCycle(String cycle) {
        String addCycles;
        addCycles = "(" + cycle + ")";
        this._cycles = this._cycles + addCycles;
    }

    /** Return the value of P modulo the size of this permutation. */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /** Returns the size of the alphabet I permute. */
    int size() {
        return this._alphabet.size();
    }

    /** Return the result of applying this permutation to P modulo the
     *  alphabet size. */
    int permute(int p) {
        char charWrap = this._alphabet.toChar(wrap(p));
        char charD = '-';
        int i = 0;
        while (i < this._cycles.length()) {
            if (this._cycles.charAt(i) != charWrap
                    || this._cycles.charAt(i + 1) == ')') {
                if (this._cycles.charAt(i) != charWrap
                    || this._cycles.charAt(i + 1) != ')') {
                    i++;
                    continue;
                }
                int j = 0;
                while (j < this._cycles.length()) {
                    if (this._cycles.charAt(i - j) == '(') {
                        charD = this._cycles.charAt(i - j + 1);
                        break;
                    }
                    j++;
                }
            } else {
                charD = this._cycles.charAt(i + 1);
            }
            i++;
        }
        return this._alphabet.toInt(charD);
    }

    /** Return the result of applying the inverse of this permutation
     *  to  C modulo the alphabet size. */
    int invert(int c) {
        char charWrap = this._alphabet.toChar(wrap(c));
        char charD = '-';
        int i = 0;
        while (i < this._cycles.length()) {
            if (this._cycles.charAt(i) != charWrap
                    || this._cycles.charAt(i - 1) == '(') {
                if (this._cycles.charAt(i) != charWrap
                        || this._cycles.charAt(i - 1) != '(') {
                    i++;
                    continue;
                }
                int j = 0;
                while (j < this._cycles.length()) {
                    if (this._cycles.charAt(i + j) == ')') {
                        charD = this._cycles.charAt(i + j - 1);
                        break;
                    }
                    j++;
                }
            } else {
                charD = this._cycles.charAt(i - 1);
            }
            i++;
        }
        return this._alphabet.toInt(charD);
    }

    /** Return the result of applying this permutation to the index of P
     *  in ALPHABET, and converting the result to a character of ALPHABET. */
    char permute(char p) {
        return this._alphabet.toChar(
                permute(this._alphabet.toInt(p)));
    }

    /** Return the result of applying the inverse of this permutation to C. */
    char invert(char c) {
        return this._alphabet.toChar(
                invert(this._alphabet.toInt(c)));
    }

    /** Return the alphabet used to initialize this Permutation. */
    Alphabet alphabet() {
        return _alphabet;
    }

    /** Return true iff this permutation is a derangement (i.e., a
     *  permutation for which no value maps to itself). */
    boolean derangement() {
        int i = 0;
        while (i < this._cycles.length()) {
            if (this._cycles.charAt(i) != '('
                    || this._cycles.charAt(i + 2) != ')') {
                i++;
                continue;
            }
            return false;
        }
        return true;
    }

    /** Alphabet of this permutation. */
    private Alphabet _alphabet;

}
