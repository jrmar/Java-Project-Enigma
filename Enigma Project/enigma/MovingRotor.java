package enigma;

import static enigma.EnigmaException.*;

/** Class that represents a rotating rotor in the enigma machine.
 *  @author mr
 */
class MovingRotor extends Rotor {
    /** String name. */
    private String _name;
    /** Perm perm. */
    private Permutation _perm;
    /** String notches. */
    private final String _notches;

    /** A rotor named NAME whose permutation in its default setting is
     *  PERM, and whose notches are at the positions indicated in NOTCHES.
     *  The Rotor is initally in its 0 setting (first character of its
     *  alphabet).
     */
    MovingRotor(String name, Permutation perm, String notches) {
        super(name, perm);
        this._name = name;
        this._perm = perm;
        this._notches = notches;
    }


    @Override
    void advance() {
        int set = setting() + 1;
        int ad = permutation().wrap(set);
        super.set(ad);
    }


    @Override
    boolean rotates() {
        return true;
    }

    @Override
    boolean atNotch() {
        int i = 0;
        while (i < this._notches.length()) {
            char not = this._notches.charAt(i);
            if (alphabet().toChar(setting()) != not) {
                i++;
                continue;
            }
            return true;
        }
        return false;
    }

}
