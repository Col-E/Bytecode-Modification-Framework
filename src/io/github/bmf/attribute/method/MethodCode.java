package io.github.bmf.attribute.method;

import java.util.ArrayList;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.util.Measurable;

/**
 * A temporary garbage class containing the raw data of what should be a list of
 * method opcodes. <br>
 * Delete this and replace it with something better. Or refactor it to be not
 * shit.
 *
 * @author Matt
 */
public class MethodCode implements Measurable {
    public final byte[] original;
    public ArrayList<Opcode> opcodes;

    public MethodCode(byte[] original) {
       this.original = original;
    }

    @Override
    public int getLength() {
        return original.length;
    }
}
