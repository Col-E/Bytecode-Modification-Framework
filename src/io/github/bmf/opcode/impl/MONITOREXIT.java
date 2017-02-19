package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class MONITOREXIT extends Opcode {
    public MONITOREXIT() {
        super(OpcodeType.OTHER, Opcode.MONITOREXIT, 1);
    }
}
