package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class FCMPL extends Opcode {
    public FCMPL() {
        super(OpcodeType.STACK, Opcode.FCMPL, 1);
    }
}
