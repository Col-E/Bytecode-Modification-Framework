package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.SingleValueOpcode;
import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class SIPUSH extends SingleValueOpcode<Integer> {
    public SIPUSH(int value) {
        super(OpcodeType.STACK, Opcode.SIPUSH, 3, value);
    }
}
