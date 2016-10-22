package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.SingleValueOpcode;
import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class BIPUSH extends SingleValueOpcode<Integer> {
    public BIPUSH(int value) {
        super(OpcodeType.STACK, Opcode.BIPUSH, 2, value);
    }
}
