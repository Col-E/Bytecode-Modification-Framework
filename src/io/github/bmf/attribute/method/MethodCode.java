package io.github.bmf.attribute.method;

import java.util.ArrayList;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.util.Measurable;
import io.github.bmf.util.MeasurableUtils;

/**
 * TODO refactor this to be more useful.
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
        if (opcodes != null) {
            return MeasurableUtils.getLength(opcodes);
        }
        return original.length;
    }
}
