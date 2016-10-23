package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;
import io.github.bmf.opcode.SingleValueOpcode;

public class BIPUSH extends SingleValueOpcode<Integer> {
    public BIPUSH(int value) {
        super(OpcodeType.STACK, Opcode.BIPUSH, 2, value);
    }
}
