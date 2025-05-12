package enigma;

import static enigma.EnigmaException.*;

/** An alphabet of encodable characters.  Provides a mapping from characters
 *  to and from indices into the alphabet.
 *  @author mr
 */
class Alphabet {
    /** Array of new Alphabet characters. */
    private char[] _newAlphabet;

    /** A new alphabet containing CHARS. The K-th character has index
     *  K (numbering from 0). No character may be duplicated. */
    Alphabet(String chars) {
        this._newAlphabet = new char[chars.length()];
        int i = 0;
        while (i < chars.length()) {
            this._newAlphabet[i] = chars.charAt(i);
            i++;
        }
    }

    /** A default alphabet of all upper-case characters. */
    Alphabet() {
        this("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }

    /** Returns the size of the alphabet. */
    int size() {
        return this._newAlphabet.length;
    }

    /** Returns true if CH is in this alphabet. */
    boolean contains(char ch) {
        for (char c : this._newAlphabet) {
            if (c != ch) {
                continue;
            }
            return true;
        }
        return false;
    }

    /** Returns character number INDEX in the alphabet, where
     *  0 <= INDEX < size(). */
    char toChar(int index) {
        return this._newAlphabet[index];
    }

    /** Returns the index of character CH which must be in
     *  the alphabet. This is the inverse of toChar(). */
    int toInt(char ch) {
        int i = 0;
        while (i < this._newAlphabet.length) {
            if (this._newAlphabet[i] != ch) {
                i++;
                continue;
            }
            return i;
        }
        return -1;
    }

}
