package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class MONITORENTER extends Opcode {
    public MONITORENTER() {
        super(OpcodeType.OTHER, Opcode.MONITORENTER, 1);
    }
}
