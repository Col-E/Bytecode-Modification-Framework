package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class IINC extends Opcode {
    public int index, increment;

    public IINC(int index, int increment) {
        super(OpcodeType.VARIABLE, Opcode.IINC, 3);
        this.index = index;
        this.increment = increment;
    }
}
