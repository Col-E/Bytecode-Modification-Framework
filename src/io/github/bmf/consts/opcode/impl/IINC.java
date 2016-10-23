package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class IINC extends Opcode {
    public int index, increment;

    public IINC(int index, int increment) {
        super(OpcodeType.VARIABLE, Opcode.IINC, 3);
        this.index = index;
        this.increment = increment;
    }
}
