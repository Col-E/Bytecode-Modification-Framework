package me.coley.bmf.attribute.method;

import java.util.List;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.util.Measurable;

/**
 * TODO refactor this to be more useful.
 *
 * @author Matt
 */
public class MethodCode implements Measurable {
    public final byte[] original;
    public List<Instruction> instructions;

    public MethodCode(byte[] original) {
        this.original = original;
    }

    @Override
    public int getLength() {
        // TODO: What opcode is the wrong length?
        /*
        if (opcodes != null) {
            return MeasurableUtils.getLength(opcodes);
        }*/
        return original.length;
    }
}
