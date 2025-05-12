package enigma;

import java.util.Collection;

import static enigma.EnigmaException.*;

/** Class that represents a complete enigma machine.
 *  @author mr
 */
class Machine {
    /** Int for number of rotors. */
    private int _numRotors;
    /** Number of pawls. */
    private int _pawls;
    /** Object all rotors. */
    private Object[] _allRotors;
    /** Rotors. */
    private final Rotor[] _rotors;
    /** The plugboard. */
    private Permutation _plugboard;


    /** A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     *  and 0 <= PAWLS < NUMROTORS pawls.  ALLROTORS contains all the
     *  available rotors. */
    Machine(Alphabet alpha, int numRotors, int pawls,
            Collection<Rotor> allRotors) {
        _alphabet = alpha;
        this._numRotors = numRotors;
        this._pawls = pawls;
        this._allRotors = allRotors.toArray();
        this._rotors = new Rotor[this._numRotors];
    }

    /** Return the number of rotor slots I have. */
    int numRotors() {
        return this._numRotors;
    }

    /** Return the number pawls (and thus rotating rotors) I have. */
    int numPawls() {
        return this._pawls;
    }

    /** Set my rotor slots to the rotors named ROTORS from my set of
     *  available rotors (ROTORS[0] names the reflector).
     *  Initially, all rotors are set at their 0 setting. */
    void insertRotors(String[] rotors) {
        int rotorLen = rotors.length;
        for (int i = 0; i < rotorLen; i++) {
            for (Object allRotor : this._allRotors) {
                if (!(rotors[i].toString()).equals((
                        ((Rotor) allRotor).name()))) {
                    continue;
                }
                this._rotors[i] = (Rotor) allRotor;
            }
        }
    }

    /** Set my rotors according to SETTING, which must be a string of
     *  numRotors()-1 characters in my alphabet. The first letter refers
     *  to the leftmost rotor setting (not counting the reflector).  */
    void setRotors(String setting) {
        int i = 1;
        while (i < this._rotors.length) {
            this._rotors[i].set(setting.charAt(i - 1));
            i++;
        }
    }

    /** Set the plugboard to PLUGBOARD. */
    void setPlugboard(Permutation plugboard) {
        this._plugboard = plugboard;
    }

    /** Returns the result of converting the input character C (as an
     *  index in the range 0..alphabet size - 1), after first advancing
     *  the machine. */
    int convert(int c) {
        boolean[] boolRotor = new boolean[numRotors()];
        {
            int i = 0;
            while (i < numRotors()) {
                if (i == numRotors() - 1) {
                    boolRotor[i] = true;
                } else {
                    if (!this._rotors[i].rotates()) {
                        boolRotor[i] = false;
                    } else if (this._rotors[i + 1].atNotch()) {
                        boolRotor[i] = true;
                        boolRotor[i + 1] = true;
                    }
                }
                i++;
            }
        }
        {
            int i = 0;
            while (i < numRotors()) {
                if (!boolRotor[i]) {
                    i++;
                    continue;
                }
                this._rotors[i].advance();
                i++;
            }
        }
        int out = this._plugboard.permute(c);
        int i = this._rotors.length - 1;
        while (i >= 0) {
            out = this._rotors[i].convertForward(out);
            i--;
        }
        int j = 1;
        while (j < this._rotors.length) {
            out = this._rotors[j].convertBackward(out);
            j++;
        }
        out = this._plugboard.permute(out);
        return out;
    }

    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) {
        StringBuilder res = new StringBuilder();
        int i = 0;
        while (i < msg.length()) {
            char msgChar;
            msgChar = this._alphabet.toChar(
                    convert(this._alphabet.toInt(msg.charAt(i))));
            res.append(msgChar);
            i++;
        }
        return res.toString();
    }

    /** Common alphabet of my rotors. */
    private final Alphabet _alphabet;

}
