package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.OpcodeType;
import io.github.bmf.opcode.SingleValueOpcode;

public class AbstractSTORE extends SingleValueOpcode<Integer> {
    public AbstractSTORE(int opcode, int index) {
        super(OpcodeType.VARIABLE, opcode, 2, index);
    }
}
