package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class RETURN extends Opcode {
    public RETURN() {
        super(OpcodeType.RETURN, Opcode.RETURN, 1);
    }
}
