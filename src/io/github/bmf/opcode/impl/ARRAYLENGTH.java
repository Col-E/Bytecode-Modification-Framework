package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class ARRAYLENGTH extends Opcode {
    public ARRAYLENGTH() {
        super(OpcodeType.ARRAY, Opcode.ARRAYLENGTH, 1);
    }
}
