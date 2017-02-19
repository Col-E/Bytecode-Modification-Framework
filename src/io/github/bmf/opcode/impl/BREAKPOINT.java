package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class BREAKPOINT extends Opcode {
    public BREAKPOINT() {
        super(OpcodeType.OTHER, Opcode.BREAKPOINT, 1);
    }
}
