package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.OpcodeType;
import io.github.bmf.opcode.SingleValueOpcode;

public class AbstractLOAD extends SingleValueOpcode<Integer> {
    public AbstractLOAD(int opcode, int index) {
        super(OpcodeType.VARIABLE, opcode, 2, index);
    }
}
