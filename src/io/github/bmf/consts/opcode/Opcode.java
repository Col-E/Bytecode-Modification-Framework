package io.github.bmf.consts.opcode;

import io.github.bmf.util.IMeasurable;

public abstract class Opcode implements IMeasurable {
    private final int opcode;
    private final int length;

    public Opcode(int opcode, int length){
        this.opcode = opcode;
        this.length = length;
    }

    @Override
    public int getLength() {
        return length;
    }

    public int getOpcode() {
        return opcode;
    }
}
