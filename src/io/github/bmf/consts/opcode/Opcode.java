package io.github.bmf.consts.opcode;

import io.github.bmf.util.IMeasurable;

public abstract class Opcode implements IMeasurable {
    public static final int WIDE = 196;

    private final OpcodeType opcode;
    private final int length;

    public Opcode(OpcodeType opcode, int length) {
        this.opcode = opcode;
        this.length = length;
    }

    @Override
    public int getLength() {
        return length;
    }

    public OpcodeType getOpcode() {
        return opcode;
    }

    public int getOpcodeValue() {
        return opcode.getValue();
    }
}
