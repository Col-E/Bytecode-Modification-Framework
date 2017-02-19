package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class ATHROW extends Opcode {
    public ATHROW() {
        super(OpcodeType.ARRAY, Opcode.ATHROW, 1);
    }
}
