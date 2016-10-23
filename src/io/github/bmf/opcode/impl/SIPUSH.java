package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;
import io.github.bmf.opcode.SingleValueOpcode;

public class SIPUSH extends SingleValueOpcode<Integer> {
    public SIPUSH(int value) {
        super(OpcodeType.STACK, Opcode.SIPUSH, 3, value);
    }
}
