package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class DCMPL extends Opcode {
    public DCMPL() {
        super(OpcodeType.STACK, Opcode.DCMPL, 1);
    }
}
